package edu.pdx.cs410J.agilston.commandline;

import java.util.ArrayList;
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
    private final List<String> arguments;
    private final String[] values;

    /**
     * Creates a new command line argument.
     *
     * @param name         name to type
     * @param help         help displayed with usage information
     * @param defaultValue default value for command
     * @param choices      possible values
     * @param arguments    sub-arguments accepted in the form -f=opt, --flag=opt, -f opt, --flag opt
     * @param aliases      alternative names to type
     */
    public CommandLineArgument(String name, String help, String defaultValue, String[] choices, String[] arguments,
                               String... aliases) {
        this.name = name;
        this.help = help;
        this.defaultValue = defaultValue;
        this.choices = choices == null ? Collections.emptyList() : List.of(choices);
        this.arguments = arguments == null ? new ArrayList<>() : new ArrayList<>(List.of(arguments));
        this.values = new String[arguments == null ? 1 : arguments.length];
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
     * Checks whether the command has a specific alias.
     *
     * @param alias alias to check for
     * @return true if the alias exists; false otherwise
     */
    public final boolean hasAlias(String alias) {
        return aliases.contains(alias);
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
     * Gets a value if it was provided or the default value otherwise.
     *
     * @param argumentName argument name to retrieve
     */
    public final String getValueOrDefault(String argumentName) {
        int index = arguments.indexOf(argumentName);
        return getValueOrDefault(index);
    }

    /**
     * Gets all values separated by a delimiter or the default value otherwise.
     *
     * @param delimiter delimiter to separate values by
     * @return delimiter-separated values or default value
     */
    public final String getAllValuesOrDefault(String delimiter) {
        if(values[0] == null) {
            return defaultValue;
        }

        return String.join(delimiter, values);
    }

    /**
     * Checks whether the argument has any sub-arguments.
     *
     * @return whether the argument has sub-arguments
     */
    public final boolean hasArguments() {
        return arguments.size() > 0;
    }

    /**
     * Gets sub argument names.
     */
    public final List<String> getArguments() {
        return Collections.unmodifiableList(arguments);
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
                String.format("%-" + (maxNameLength - indentSize) + "s", names));

        if((names.length() + indentSize) >= maxNameLength) {
            builder.append(System.lineSeparator());
        }

        builder.append(help);

        builder = new StringBuilder(CommandLineParser.formatString(
                builder.toString(),
                lineLength,
                String.format("%" + indentSize + "s", ""),
                String.format("%" + maxNameLength + "s", "")));

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

        if(hasArguments()) {
            builder.append(" ")
                   .append(String.join(" ", arguments));
        }

        return builder;
    }
}
