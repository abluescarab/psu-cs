package edu.pdx.cs410J.agilston.commandline;

import java.util.Collections;
import java.util.List;

/**
 * A class that represents a command line argument.
 */
class CommandLineArgument {
    private final String name;
    private final String help;
    private final String defaultValue;
    private final List<String> choices;
    private final List<String> aliases;
    private final boolean acceptsOption;
    private final int argumentListSize;
    private final String[] values;

    /**
     * Creates a new command line argument.
     *
     * @param name             name to type
     * @param help             help displayed with usage information
     * @param acceptsOption    whether the argument accepts an option (-f=opt, --flag=opt)
     * @param defaultValue     default value for command
     * @param choices          possible values
     * @param argumentListSize number of sub arguments accepted
     * @param aliases          alternative names to type
     */
    public CommandLineArgument(String name, String help, boolean acceptsOption, String defaultValue, String[] choices,
                               int argumentListSize, String... aliases) {
        this.name = name;
        this.help = help;
        this.acceptsOption = acceptsOption;
        this.defaultValue = defaultValue;
        this.choices = choices == null ? Collections.emptyList() : List.of(choices);
        this.argumentListSize = argumentListSize;
        this.values = new String[this.argumentListSize];
        this.aliases = aliases == null ? Collections.emptyList() : List.of(aliases);
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
     * Sets a value of the command.
     *
     * @param value value to set to
     */
    public void setValue(String value) {
        setValue(0, value);
    }

    /**
     * Sets a value of the command.
     *
     * @param index value index to set
     * @param value value to set to
     */
    public void setValue(int index, String value) {
        if(choices.size() > 0) {
            this.values[index] = choices.contains(value) ? value : defaultValue;
        }
        else {
            this.values[index] = value;
        }
    }

    /**
     * Gets the default value of the argument if a value is not provided.
     */
    public final String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Gets an unmodifiable list of aliases.
     */
    public final List<String> getAliases() {
        return aliases;
    }

    /**
     * Gets a value if it was provided or the default value otherwise.
     */
    public final String getValueOrDefault() {
        return getValueOrDefault(0);
    }

    /**
     * Gets a value if it was provided or the default value otherwise.
     *
     * @param index value index to get
     */
    public final String getValueOrDefault(int index) {
        if(values[index] == null) {
            return defaultValue;
        }

        return values[index];
    }

    /**
     * Gets the number of sub arguments accepted.
     */
    public final int getArgumentListSize() {
        return argumentListSize;
    }

    /**
     * Gets whether the argument accepts an option (-f=opt, --flag=opt).
     */
    public final boolean acceptsOption() {
        return acceptsOption;
    }

    /**
     * Gets a formatted help string for the argument.
     *
     * @param lineLength maximum length of each printed line
     * @param indentSize size of indentation before line
     */
    public String getFormattedHelp(int lineLength, int indentSize) {
        int maxNameLength = 24;
        StringBuilder names = getFormattedName();
        StringBuilder builder = new StringBuilder(
                String.format("%" + indentSize + "s%-" + (maxNameLength - indentSize) + "s", "", names));

        if(names.length() >= (maxNameLength - indentSize)) {
            builder.append(System.lineSeparator())
                   .append(CommandLineParser.formatString(help, lineLength,
                           String.format("%" + maxNameLength + "s", ""), true));
        }
        else {
            builder.append(CommandLineParser.formatString(help, lineLength,
                    String.format("%" + builder.length() + "s", ""), false));
        }

        return builder.toString();
    }

    /**
     * Gets the formatted name of the argument, included aliases.
     */
    protected StringBuilder getFormattedName() {
        StringBuilder builder = new StringBuilder(name);

        if(aliases.size() > 0) {
            builder.append(", ")
                   .append(String.join(", ", aliases));
        }

        return builder;
    }
}
