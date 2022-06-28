package edu.pdx.cs410J.agilston.commandline;

import edu.pdx.cs410J.agilston.commandline.arguments.CommandLineArgument;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class used to parse command line arguments. Functionality based on Python's argparse library.
 */
public class CommandLineParser {
    // parser options
    protected String jarFilename;
    protected int maxLineLength;
    protected int indentSize;
    protected String prologue;
    protected String epilogue;

    // command line arguments
    protected Map<String, CommandLineArgument> arguments;
    protected List<String> givenArguments;

    /**
     * Creates a new command line parser.
     *
     * @param jarFilename name of the jar file with <code>main()</code>
     */
    public CommandLineParser(String jarFilename) {
        arguments = new LinkedHashMap<>();
        this.jarFilename = jarFilename;
        this.maxLineLength = 0;
        this.indentSize = 2;
    }

    /**
     * Add an argument to the command line interface.
     *
     * @param name name to type
     */
    public void addArgument(String name) {
        arguments.put(name, new CommandLineArgument(name));
    }

    /**
     * Add an argument to the command line interface.
     *
     * @param name      name to type
     * @param valueType type of value to store in the argument
     * @param aliases   alternative names to type
     */
    public void addArgument(String name, CommandLineArgument.ValueType valueType, String... aliases) {
        arguments.put(name, new CommandLineArgument(name, valueType, aliases));
    }

    /**
     * Add an argument to the command line interface.
     *
     * @param name    name to type
     * @param help    help text displayed with <code>--help</code>
     * @param aliases alternative names to type
     */
    public void addArgument(String name, String help, String... aliases) {
        arguments.put(name, new CommandLineArgument(name, help, aliases));
    }

    /**
     * Add an argument to the command line interface.
     *
     * @param name      name to type
     * @param help      help text displayed with <code>--help</code>
     * @param valueType type of value to store in the argument
     * @param aliases   alternative names to type
     */
    public void addArgument(String name, String help, CommandLineArgument.ValueType valueType, String... aliases) {
        arguments.put(name, new CommandLineArgument(name, help, valueType, aliases));
    }

    /**
     * Removes a positional or option argument from the command line interface.
     *
     * @param name name to remove
     * @return <code>true</code> if argument was removed successfully; <code>false</code> if not
     */
    public boolean removeArgument(String name) {
        return arguments.remove(name) != null;
    }

    /**
     * Checks whether an argument has been provided on the command line.
     *
     * @param name name to check for
     * @return <code>true</code> if the argument has been given; <code>false</code> if not
     */
    public boolean hasArgument(String name) {
        return givenArguments.contains(name);
    }

    /**
     * Gets an argument given on the command line.
     *
     * @param name name of argument to get
     * @return argument from the command line
     */
    public CommandLineArgument getArgument(String name) {
        if(!givenArguments.contains(name)) {
            return null;
        }

        if(name.startsWith("-") && !arguments.containsKey(name)) {
            for(CommandLineArgument arg : arguments.values()) {
                try {
                    if(arg.getAliases().contains(name)) {
                        return arg;
                    }
                }
                catch(ClassCastException ignored) {
                }
            }
        }

        return arguments.get(name);
    }

    /**
     * Sets the maximum line length printed to the command line.
     *
     * @param maxLineLength maximum line length in columns
     */
    public void setMaxLineLength(int maxLineLength) {
        this.maxLineLength = maxLineLength;
    }

    /**
     * Sets the indent size to display before new lines on the command line (where applicable).
     *
     * @param indentSize indent size in columns
     */
    public void setIndentSize(int indentSize) {
        this.indentSize = indentSize;
    }

    /**
     * Sets the prologue displayed after the usage information and before the positional arguments.
     *
     * @param prologue prologue text
     */
    public void setPrologue(String prologue) {
        this.prologue = prologue;
    }

    /**
     * Sets the epilogue displayed after the optional arguments.
     *
     * @param epilogue epilogue text
     */
    public void setEpilogue(String epilogue) {
        this.epilogue = epilogue;
    }

    /**
     * Prints the usage information for the parser.
     *
     * @param stream stream to print to
     */
    public void printUsage(PrintStream stream) {
//            optional arguments:
//              -h, --help            show this help message and exit
//              --generate-reports REPORT_COUNT SERVICE_COUNT, -gr REPORT_COUNT SERVICE_COUNT
//                                    generate REPORT_COUNT member reports, each with SERVICE_COUNT services on load
//              --generate-users USER_TYPE USER_COUNT, -gu USER_TYPE USER_COUNT
//                                    generate users. USER_TYPE must be one of: members, providers, managers.

        StringBuilder usage = new StringBuilder();
        StringBuilder positional = new StringBuilder("positional arguments:\n");
        StringBuilder optional = new StringBuilder("optional arguments:\n");
        boolean hasPositional = false;
        boolean hasOptional = false;

        usage.append("usage: ");
        usage.append(jarFilename);

        for(var arg : arguments.values()) {
            if(arg.getName().startsWith("-")) {
                usage.append(String.format(" [%s]", arg.getName()));
                optional.append(arg.getFormattedHelp(maxLineLength, indentSize))
                        .append("\n");
                hasOptional = true;
            }
            else {
                usage.append(String.format(" %s", arg.getName()));
                positional.append(arg.getFormattedHelp(maxLineLength, indentSize))
                          .append("\n");
                hasPositional = true;
            }
        }

        usage.append("\n");

        if(prologue != null && prologue.length() > 0) {
            usage.append("\n")
                 .append(formatString(prologue, maxLineLength))
                 .append("\n");
        }

        if(hasPositional) {
            usage.append("\n")
                 .append(positional);
        }

        if(hasOptional) {
            usage.append("\n")
                 .append(optional);
        }

        if(epilogue != null && epilogue.length() > 0) {
            usage.append("\n")
                 .append(formatString(epilogue, maxLineLength))
                 .append("\n");
        }

        stream.println(usage);
    }

    /**
     * Parses the given command line arguments.
     *
     * @param args command line arguments
     */
    private void parseArgs(String[] args) {
        int i = 0;
        Pattern shortFlag = Pattern.compile("-(\\w+)");
        Pattern longFlag = Pattern.compile("--(\\w+)");
        Pattern shortOption = Pattern.compile(shortFlag + "=(.*)");
        Pattern longOption = Pattern.compile(longFlag + "=(.*)");
        Matcher matcher;
        CommandLineArgument arg;
        String name;

        while(i < args.length) {
            // short flag:   -f
            // short option: -f opt
            if((matcher = shortFlag.matcher(args[i])).matches()) {
                for(String group : matcher.group(1).split("")) {
                    givenArguments.add(group.toLowerCase());

                    if((arg = getArgument("-" + group)) != null) {
                        switch(arg.getValueType()) {
                            // TODO
                        }
                    }
                }
            }
            // long flag:   --flag
            // long option: --flag opt
            else if((matcher = longFlag.matcher(args[i])).matches()) {
                givenArguments.add(matcher.group(1).toLowerCase());
            }
            // short option: -f=opt
            else if((matcher = shortOption.matcher(args[i])).matches()) {

            }
            // long option: --flag=opt
            else if((matcher = longOption.matcher(args[i])).matches()) {
//                givenArguments.add(matcher.group(1).toLowerCase(), matcher.group(2));
            }
            // positional argument
            else {
                givenArguments.add(args[i]);
            }
        }

//        for(String arg : args) {
//            if((matcher = shortFlag.matcher(arg)).matches()) {
//                for(String group : matcher.group(1).split("")) {
//                    givenFlags.add(group.toLowerCase());
//                }
//            }
//            else if((matcher = longFlag.matcher(arg)).matches()) {
//                givenFlags.add(matcher.group(1).toLowerCase());
//            }
//            else if((matcher = option.matcher(arg)).matches()) {
//                givenOptions.put(matcher.group(1).toLowerCase(), matcher.group(2));
//            }
//            else {
//                arguments.add(arg);
//            }
//        }
    }

    /**
     * Formats a string for the command line interface.
     *
     * @param text          text to format
     * @param columnToBreak column number to add line breaks at
     * @return formatted string
     */
    public static String formatString(String text, int columnToBreak) {
        return formatString(text, columnToBreak, "", false);
    }

    /**
     * Formats a string for the command line interface.
     *
     * @param text              text to format
     * @param columnToBreak     column number to add line breaks at
     * @param linePrefix        prefix string before each new line
     * @param prefixOnFirstLine whether to print the prefix on the first line
     * @return formatted string
     */
    public static String formatString(String text, int columnToBreak, String linePrefix, boolean prefixOnFirstLine) {
        if(text == null || text.length() == 0) {
            return "";
        }

        StringBuilder formatted = new StringBuilder();
        StringTokenizer newLineTokenizer;
        StringTokenizer spaceTokenizer;
        int length = linePrefix.length();

        if(prefixOnFirstLine) {
            formatted.append(linePrefix);
        }

        if(columnToBreak <= 0 || text.length() <= columnToBreak) {
            formatted.append(text);
        }
        else {
            newLineTokenizer = new StringTokenizer(text, "\n");

            while(newLineTokenizer.hasMoreTokens()) {
                String block = newLineTokenizer.nextToken();
                spaceTokenizer = new StringTokenizer(block, " ");

                while(spaceTokenizer.hasMoreTokens()) {
                    String word = spaceTokenizer.nextToken();

                    if(length + word.length() >= columnToBreak) {
                        formatted.append("\n");
                        formatted.append(linePrefix);
                        length = linePrefix.length();
                    }

                    formatted.append(word);
                    formatted.append(" ");
                    length += word.length() + 1;
                }

                length = linePrefix.length();

                if(newLineTokenizer.hasMoreTokens()) {
                    formatted.append("\n\n");
                    formatted.append(linePrefix);
                }
            }
        }

        return formatted.toString();
    }
}
