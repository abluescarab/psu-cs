package edu.pdx.cs410J.agilston.commandline;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class used to parse command line arguments. Functionality based on Python's argparse library.
 */
public class CommandLineParser {
    protected String jarFilename;
    protected int maxLineLength;
    protected int indentSize;
    protected String prologue;
    protected String epilogue;

    // command line arguments
    protected Map<String, CommandLineArgument> positionalArguments;
    protected Map<String, CommandLineArgument> flags;
    protected List<String> givenArguments;

    /**
     * Creates a new command line parser.
     *
     * @param jarFilename name of the jar file with <code>main()</code>
     */
    public CommandLineParser(String jarFilename) {
        this.positionalArguments = new LinkedHashMap<>();
        this.flags = new HashMap<>();
        this.givenArguments = new ArrayList<>();
        this.jarFilename = jarFilename;
        this.maxLineLength = 0;
        this.indentSize = 2;
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
            newLineTokenizer = new StringTokenizer(text, System.lineSeparator());

            while(newLineTokenizer.hasMoreTokens()) {
                String block = newLineTokenizer.nextToken();
                spaceTokenizer = new StringTokenizer(block, " ");

                while(spaceTokenizer.hasMoreTokens()) {
                    String word = spaceTokenizer.nextToken();

                    if(length + word.length() >= columnToBreak) {
                        formatted.append(System.lineSeparator())
                                 .append(linePrefix);
                        length = linePrefix.length();
                    }

                    formatted.append(word)
                             .append(" ");
                    length += word.length() + 1;
                }

                length = linePrefix.length();

                if(newLineTokenizer.hasMoreTokens()) {
                    formatted.append(System.lineSeparator())
                             .append(linePrefix);
                }
            }
        }

        return formatted.toString();
    }

    /**
     * Adds a positional argument to the command line.
     *
     * @param name name to type
     * @param help help text displayed with usage information
     */
    public void addArgument(String name, String help) {
        addArgument(name, help, "", null, 1);
    }

    /**
     * Adds a positional argument to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param argumentList number of sub arguments accepted
     */
    public void addArgument(String name, String help, int argumentList) {
        addArgument(name, help, "", null, argumentList);
    }

    /**
     * Adds an optional positional argument to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the argument
     */
    public void addArgument(String name, String help, String defaultValue) {
        addArgument(name, help, defaultValue, null, 1);
    }

    /**
     * Adds an optional positional argument to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the argument
     * @param argumentList number of sub arguments accepted
     */
    public void addArgument(String name, String help, String defaultValue, int argumentList) {
        addArgument(name, help, defaultValue, null, argumentList);
    }

    /**
     * Adds a positional argument to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the argument
     * @param choices      possible values
     */
    public void addArgument(String name, String help, String defaultValue, String[] choices) {
        addArgument(name, help, defaultValue, choices, 1);
    }

    /**
     * Adds a flag (-f, --flag) to the command line.
     *
     * @param name    name to type
     * @param help    help text displayed with usage information
     * @param aliases alternative names to type
     */
    public void addFlag(String name, String help, String... aliases) {
        if(!name.startsWith("-")) {
            throw new IllegalArgumentException("Invalid argument: Flag name must start with \"-\"");
        }

        addArgument(name, help, "", null, 0, aliases);
    }

    /**
     * Adds a flag (-f, --flag) to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param argumentList number of sub arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     * @param aliases      alternative names to type
     */
    public void addFlag(String name, String help, int argumentList, String... aliases) {
        if(!name.startsWith("-")) {
            throw new IllegalArgumentException("Invalid argument: Flag name must start with \"-\"");
        }

        addArgument(name, help, "", null, argumentList, aliases);
    }

    /**
     * Adds a flag (-f, --flag) to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the argument
     * @param choices      possible values
     * @param aliases      alternative names to type
     */
    public void addFlag(String name, String help, String defaultValue, String[] choices, String... aliases) {
        if(!name.startsWith("-")) {
            throw new IllegalArgumentException("Invalid argument: Flag name must start with \"-\"");
        }

        addArgument(name, help, defaultValue, choices, 1, aliases);
    }

    /**
     * Add an argument to the command line interface.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the command
     * @param choices      possible values
     * @param argumentList number of sub arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     * @param aliases      alternative names to type
     */
    private void addArgument(String name, String help, String defaultValue, String[] choices, int argumentList,
                             String... aliases) {
        Map<String, CommandLineArgument> map = name.startsWith("-") ? flags : positionalArguments;
        map.put(name, new CommandLineArgument(name, help, defaultValue, choices, argumentList, aliases));
    }

    /**
     * Checks whether an argument has been provided on the command line.
     *
     * @param name name to check for
     * @return <code>true</code> if the argument has been given; <code>false</code> if not
     */
    public boolean hasArgument(String name) {
        if(givenArguments.contains(name)) {
            return true;
        }

        if(name.startsWith("-")) {
            // check if we are searching for an arg by its full name but the alias was given, or vice versa
            CommandLineArgument arg = getArgument(name);
            return arg != null && givenArguments.contains(arg.getName());
        }

        return false;
    }

    /**
     * Gets an argument.
     *
     * @param name name of argument to get
     */
    private CommandLineArgument getArgument(String name) {
        if(name.startsWith("-")) {
            if(flags.containsKey(name)) {
                return flags.get(name);
            }

            for(CommandLineArgument arg : flags.values()) {
                if(arg.hasAlias(name)) {
                    return arg;
                }
            }
        }

        return positionalArguments.get(name);
    }

    /**
     * Gets a value or default value of an argument. Automatically null checks and returns an empty string if the
     * argument does not exist.
     *
     * @param name name of argument to get
     * @return value or default value if not provided
     */
    public String getValueOrDefault(String name) {
        return getValueOrDefault(name, 0);
    }

    /**
     * Gets a value or default value of an argument. Automatically null checks and returns an empty string if the
     * argument does not exist. Index can be used to find a sub argument by its position within the argument list.
     *
     * @param name  name of argument to get
     * @param index value index to get
     * @return value or default value if not provided
     */
    public String getValueOrDefault(String name, int index) {
        CommandLineArgument arg = getArgument(name);
        return arg == null ? "" : arg.getValueOrDefault(index);
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
     * Gets the usage information for the parser.
     */
    public String getUsageInformation() {
        int currentUsageLength = 0;
        String appendName;
        StringBuilder usage = new StringBuilder();
        StringBuilder positional = new StringBuilder("positional arguments:" + System.lineSeparator());
        StringBuilder optional = new StringBuilder("optional arguments:" + System.lineSeparator());

        usage.append("usage: ");
        usage.append(jarFilename);
        currentUsageLength = usage.length();

        for(var arg : positionalArguments.values()) {
            currentUsageLength = splitUsage(usage, String.format(" %s", arg.getName()), currentUsageLength);
            positional.append(arg.getFormattedHelp(maxLineLength, indentSize))
                      .append(System.lineSeparator());
        }

        for(var arg : flags.values()) {
            currentUsageLength = splitUsage(usage, String.format(" [%s]", arg.getName()), currentUsageLength);
            optional.append(arg.getFormattedHelp(maxLineLength, indentSize))
                    .append(System.lineSeparator());
        }

        usage.append(System.lineSeparator());

        if(prologue != null && prologue.length() > 0) {
            usage.append(System.lineSeparator())
                 .append(formatString(prologue, maxLineLength))
                 .append(System.lineSeparator());
        }

        if(positionalArguments.size() > 0) {
            usage.append(System.lineSeparator())
                 .append(positional);
        }

        if(flags.size() > 0) {
            usage.append(System.lineSeparator())
                 .append(optional);
        }

        if(epilogue != null && epilogue.length() > 0) {
            usage.append(System.lineSeparator())
                 .append(formatString(epilogue, maxLineLength))
                 .append(System.lineSeparator());
        }

        return usage.toString();
    }

    /**
     * Splits the usage information line.
     *
     * @param builder     builder to append to
     * @param toAppend    string to append
     * @param usageLength current length of usage information
     * @return new length of usage information
     */
    private int splitUsage(StringBuilder builder, String toAppend, int usageLength) {
        int newLength = usageLength;
        int appendLength = toAppend.length();

        if((usageLength + appendLength) >= maxLineLength) {
            builder.append(System.lineSeparator())
                   .append("      ") // length of "usage: "
                   .append(toAppend);
            newLength = 0;
        }
        else {
            builder.append(toAppend);
            newLength += appendLength;
        }

        return newLength;
    }

    /**
     * Parses the given command line arguments.
     *
     * @param args command line arguments
     */
    public void parse(String[] args) {
        int index = 0;
        int position = 0;
        Pattern flag = Pattern.compile("((-+)([\\w-]+))(?:=(.*))?");
        Matcher matcher;

        while(index < args.length) {
            if((matcher = flag.matcher(args[index])).matches()) {
                String fullName = matcher.group(1);
                String hyphens = matcher.group(2);
                String option = matcher.group(4);

                if(Objects.equals(hyphens, "-") && getArgument(fullName) == null) {
                    for(String character : matcher.group(3).split("")) {
                        index = parseArg(args, index, hyphens + character, option);
                    }
                }
                else {
                    index = parseArg(args, index, fullName, option);
                }

                index++;
            }
            else {
                List<CommandLineArgument> positionalArgs = new ArrayList<>(positionalArguments.values());

                if(position >= positionalArgs.size()) {
                    throw new IllegalArgumentException("Invalid argument: Too many positional arguments given");
                }

                CommandLineArgument posArg = positionalArgs.get(position);
                int subarg = 0;

                givenArguments.add(posArg.getName());

                while(subarg < posArg.getArgumentList()) {
                    posArg.setValue(subarg++, args[index++]);
                }

                position++;
            }
        }
    }

    /**
     * Parses a single argument.
     *
     * @param args   list of arguments
     * @param index  index of current argument
     * @param name   name of current argument
     * @param option option given to current argument
     */
    private int parseArg(String[] args, int index, String name, String option) {
        CommandLineArgument arg = getArgument(name);

        if(arg == null) {
            throw new IllegalArgumentException(String.format("Invalid argument: %s", name));
        }

        int argumentList = arg.getArgumentList();
        int subarg = 0;

        if(argumentList > 0) {
            if(option == null) {
                while(subarg < argumentList && (index + 1 < args.length)) {
                    arg.setValue(subarg++, args[++index]);
                }
            }
            else {
                arg.setValue(option);
            }
        }

        givenArguments.add(arg.getName());
        return index;
    }
}
