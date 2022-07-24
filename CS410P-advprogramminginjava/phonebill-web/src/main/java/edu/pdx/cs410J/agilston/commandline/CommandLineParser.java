package edu.pdx.cs410J.agilston.commandline;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class used to parse command line arguments. Functionality based on Python's argparse library.
 */
public class CommandLineParser {
    private final String jarFilename;
    private final Map<String, CommandLineArgument> positionalArguments;
    private final Map<String, CommandLineArgument> flags;
    private final List<String> givenArguments;
    private int maxLineLength;
    private int indentSize;
    private String prologue;
    private String epilogue;

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
        return formatString(text, columnToBreak, "", "");
    }

    /**
     * Formats a string for the command line interface.
     *
     * @param text          text to format
     * @param columnToBreak column number to add line breaks at
     * @param linePrefix    prefix string before each new line
     * @return formatted string
     */
    public static String formatString(String text, int columnToBreak, String linePrefix) {
        return formatString(text, columnToBreak, linePrefix, linePrefix);
    }

    /**
     * Formats a string for the command line interface.
     *
     * @param text            text to format
     * @param columnToBreak   column number to add line breaks at
     * @param firstLinePrefix prefix string on the first line
     * @param linePrefix      prefix string before each new line
     * @return formatted string
     */
    public static String formatString(String text, int columnToBreak, String firstLinePrefix, String linePrefix) {
        if(text == null || text.length() == 0) {
            return "";
        }

        StringBuilder formatted = new StringBuilder();
        String[] lines = text.split(System.lineSeparator());
        int length = 0;

        if(!Objects.equals(firstLinePrefix, "")) {
            length = firstLinePrefix.length();
            formatted.append(firstLinePrefix);
        }

        for(int i = 0; i < lines.length; i++) {
            String block = lines[i];

            if(columnToBreak <= 0 || length + block.length() <= columnToBreak) {
                formatted.append(block);
            }
            else {
                for(String word : block.split(" ")) {
                    if(length + word.length() + 1 >= columnToBreak) {
                        formatted.append(System.lineSeparator())
                                 .append(linePrefix);
                        length = linePrefix.length();
                    }

                    int wordIndex = block.indexOf(word);

                    formatted.append(block, 0, wordIndex)
                             .append(word)
                             .append(" ");

                    int newLength = wordIndex + word.length() + 1;

                    if(newLength <= block.length()) {
                        block = block.substring(newLength);
                    }
                    else {
                        block = "";
                    }

                    length += newLength;
                }

                length = linePrefix.length();
            }

            if(i < lines.length - 1) {
                formatted.append(System.lineSeparator())
                         .append(linePrefix);
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
        addArgument(name, help, "", null, null);
    }

    /**
     * Adds a positional argument to the command line.
     *
     * @param name      name to type
     * @param help      help text displayed with usage information
     * @param arguments sub-arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     */
    public void addArgument(String name, String help, String[] arguments) {
        addArgument(name, help, "", null, arguments);
    }

    /**
     * Adds an optional positional argument to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the argument
     */
    public void addArgument(String name, String help, String defaultValue) {
        addArgument(name, help, defaultValue, null, null);
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
        addArgument(name, help, defaultValue, choices, null);
    }

    /**
     * Adds an optional positional argument to the command line.
     *
     * @param name      name to type
     * @param help      help text displayed with usage information
     * @param arguments sub-arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     */
    public void addListArgument(String name, String help, String[] arguments) {
        addArgument(name, help, null, null, arguments);
    }

    /**
     * Adds an optional positional argument to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the argument
     * @param arguments    sub-arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     */
    public void addListArgument(String name, String help, String defaultValue, String[] arguments) {
        addArgument(name, help, defaultValue, null, arguments);
    }

    /**
     * Adds an optional positional argument to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the argument
     * @param choices      possible values
     * @param arguments    sub-arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     */
    public void addListArgument(String name, String help, String defaultValue, String[] choices,
                                String[] arguments) {
        addArgument(name, help, defaultValue, choices, arguments);
    }

    /**
     * Adds a flag (-f, --flag) to the command line.
     *
     * @param name      name to type
     * @param help      help text displayed with usage information
     * @param arguments sub-arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     * @param aliases   alternative names to type
     */
    public void addFlag(String name, String help, String[] arguments, String... aliases) {
        if(!name.startsWith("-")) {
            throw new IllegalArgumentException("Invalid argument: Flag name must start with \"-\"");
        }

        addArgument(name, help, "", null, arguments, aliases);
    }

    /**
     * Adds a flag (-f, --flag) to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the argument
     * @param arguments    sub-arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     * @param aliases      alternative names to type
     */
    public void addFlag(String name, String help, String defaultValue, String[] arguments, String... aliases) {
        if(!name.startsWith("-")) {
            throw new IllegalArgumentException("Invalid argument: Flag name must start with \"-\"");
        }

        addArgument(name, help, defaultValue, null, arguments, aliases);
    }

    /**
     * Adds a flag (-f, --flag) to the command line.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the argument
     * @param choices      possible values
     * @param arguments    sub-arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     * @param aliases      alternative names to type
     */
    public void addFlag(String name, String help, String defaultValue, String[] choices, String[] arguments,
                        String... aliases) {
        if(!name.startsWith("-")) {
            throw new IllegalArgumentException("Invalid argument: Flag name must start with \"-\"");
        }

        addArgument(name, help, defaultValue, choices, arguments, aliases);
    }

    /**
     * Add an argument to the command line interface.
     *
     * @param name         name to type
     * @param help         help text displayed with usage information
     * @param defaultValue default value of the command
     * @param choices      possible values
     * @param arguments    sub-arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     * @param aliases      alternative names to type
     */
    private void addArgument(String name, String help, String defaultValue, String[] choices, String[] arguments,
                             String... aliases) {
        Map<String, CommandLineArgument> map = name.startsWith("-") ? flags : positionalArguments;
        map.put(name, new CommandLineArgument(name, help, defaultValue, choices, arguments, aliases));
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
        return arg == null ? null : arg.getValueOrDefault(index);
    }

    /**
     * Gets all the values or default value of an argument, separated by the specified delimiter.
     *
     * @param name      name of argument to get
     * @param delimiter delimiter to separate arguments by
     * @return all values or default value if not provided
     */
    public String getAllValuesOrDefault(String name, String delimiter) {
        CommandLineArgument arg = getArgument(name);
        return arg == null ? null : arg.getAllValuesOrDefault(delimiter);
    }

    /**
     * Gets the maximum line length printed to the command line.
     */
    public int getMaxLineLength() {
        return maxLineLength;
    }

    /**
     * Sets the maximum line length printed to the command line.
     *
     * @param maxLineLength maximum line length in columns
     */
    public void setMaxLineLength(int maxLineLength) {
        this.maxLineLength = Math.abs(maxLineLength);
    }

    /**
     * Gets the indent size to display before new lines.
     */
    public int getIndentSize() {
        return indentSize;
    }

    /**
     * Sets the indent size to display before new lines on the command line (where applicable).
     *
     * @param indentSize indent size in columns
     */
    public void setIndentSize(int indentSize) {
        this.indentSize = Math.abs(indentSize);
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
        StringBuilder usage = new StringBuilder();
        StringBuilder positional = new StringBuilder("positional arguments:" + System.lineSeparator());
        StringBuilder optional = new StringBuilder("optional arguments:" + System.lineSeparator());

        usage.append("usage: ")
             .append(jarFilename);

        for(var arg : positionalArguments.values()) {
            usage.append(String.format(" %s", arg.getName()));
            positional.append(arg.getFormattedHelp(maxLineLength, indentSize))
                      .append(System.lineSeparator());
        }

        for(var arg : flags.values()) {
            String flagInfo;

            if(arg.hasArguments()) {
                var args = arg.getArguments();
                flagInfo = String.format(" [%s=%s]", arg.getName(), String.join(", ", args));
            }
            else {
                flagInfo = String.format(" [%s]", arg.getName());
            }

            usage.append(flagInfo);
            optional.append(arg.getFormattedHelp(maxLineLength, indentSize))
                    .append(System.lineSeparator());
        }

        usage = new StringBuilder(formatString(usage.toString(), maxLineLength, "",
                String.format("%" + "usage: ".length() + "s", "")));
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
     * Parses the given command line arguments.
     *
     * @param args command line arguments
     */
    public void parse(String[] args) {
        int index = 0;
        int position = 0;
        Pattern flag = Pattern.compile("((-+)(\\w+))(?:=(.*))?");
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

                do {
                    posArg.setValue(subarg++, args[index++]);
                } while(subarg < posArg.getArguments().size());

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

        int argumentList = arg.getArguments().size();
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
