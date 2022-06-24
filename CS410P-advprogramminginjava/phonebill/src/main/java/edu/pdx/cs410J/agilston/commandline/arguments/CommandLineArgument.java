package edu.pdx.cs410J.agilston.commandline.arguments;

import edu.pdx.cs410J.agilston.commandline.CommandLineParser;

import java.util.List;
import java.util.Objects;

/**
 * Creates a new command line argument.
 */
public class CommandLineArgument {
    protected String name;
    protected String help;
    protected String value;
    protected String defaultValue;
    protected List<String> choices;
    protected List<String> aliases;

    /**
     * Creates a new command line argument.
     * @param name name to type
     */
    public CommandLineArgument(String name) {
        this(name, "", null);
    }

    /**
     * Creates a new command line argument.
     * @param name name to type
     * @param help help displayed with <code>--help</code>
     */
    public CommandLineArgument(String name, String help) {
        this(name, help, null);
    }

    /**
     * Creates a new command line argument.
     * @param name name to type
     * @param help help displayed with <code>--help</code>
     */
    public CommandLineArgument(String name, String help, String... aliases) {
        this.name = name.replace(" ", "_");
        this.help = help;
        this.choices = List.of(choices);
    }

    /**
     * Gets the name of the command.
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets the help text for the command.
     */
    public final String getHelp() {
        return help;
    }

    /**
     * Gets the current value of the argument.
     */
    public final String getValue() {
        return value;
    }

    /**
     * Gets the default value of the argument if a value is not provided.
     */
    public final String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Gets the value if it was provided or the default value otherwise.
     */
    public final String getValueOrDefault() {
        if(Objects.equals(value, "") && !Objects.equals(defaultValue, "")) {
            return defaultValue;
        }

        return value;
    }

    /** Gets a formatted help string for the argument.
     * @param lineLength maximum length of each printed line
     * @param indentSize size of indentation before line
     */
    public String getFormattedHelp(int lineLength, int indentSize) {
        int maxNameLength = 24;
        StringBuilder names = getFormattedName();
        StringBuilder builder = new StringBuilder(
                String.format("%" + indentSize + "s%-" + (maxNameLength - indentSize) + "s", "", names));

        if(names.length() >= (maxNameLength - indentSize)) {
            builder.append("\n")
                   .append(CommandLineParser.formatString(help, lineLength,
                           String.format("%" + maxNameLength + "s", ""), true));
        }
        else {
            builder.append(CommandLineParser.formatString(help, lineLength,
                    String.format("%" + builder.length() + "s", ""), false));
        }

        return builder.toString();
    }

    protected StringBuilder getFormattedName() {
        return new StringBuilder(name);
    }
}
