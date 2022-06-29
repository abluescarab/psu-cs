package edu.pdx.cs410J.agilston.commandline;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Creates a new command line argument.
 */
public class CommandLineArgument {
    private final String name;
    private final String help;
    private final String defaultValue;
    private final List<String> choices;
    private final List<String> aliases;
    private String value;

    /**
     * Creates a new command line argument.
     *
     * @param name    name to type
     * @param help    help displayed with <code>--help</code>
     * @param aliases alternative names to type
     */
    public CommandLineArgument(String name, String help, String... aliases) {
        this(name, help, "", null, aliases);
    }

    /**
     * Creates a new command line argument.
     *
     * @param name         name to type
     * @param help         help displayed with <code>--help</code>
     * @param defaultValue default value for command
     * @param choices      possible values
     * @param aliases      alternative names to type
     */
    public CommandLineArgument(String name, String help, String defaultValue, String[] choices, String... aliases) {
        this.name = name;
        this.help = help;
        this.defaultValue = defaultValue;
        this.choices = choices == null ? Collections.emptyList() : List.of(choices);
        this.aliases = aliases == null ? Collections.emptyList() : List.of(aliases);
        this.value = "";
    }

    /**
     * Sets the value of the command.
     *
     * @param value value to set to
     */
    public void setValue(String value) {
        if(choices.size() > 0) {
            this.value = choices.contains(value) ? value : defaultValue;
        }
        else {
            this.value = value;
        }
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
     * Gets an unmodifiable list of aliases.
     */
    public final List<String> getAliases() {
        return aliases;
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
