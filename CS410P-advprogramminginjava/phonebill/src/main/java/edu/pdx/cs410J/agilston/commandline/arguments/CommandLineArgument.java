package edu.pdx.cs410J.agilston.commandline.arguments;

import edu.pdx.cs410J.agilston.commandline.CommandLineParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Creates a new command line argument.
 */
public class CommandLineArgument {
    public enum ValueType {
        VALUE,
        CHOICE,
        LIST
    }

    protected String name;
    protected String help;
    protected String value;
    protected String defaultValue;
    protected ValueType valueType;
    protected int listSize;
    protected List<String> choices;
    protected List<String> aliases;

    /**
     * Creates a new command line argument.
     *
     * @param name name to type
     */
    public CommandLineArgument(String name) {
        this(name, "", ValueType.VALUE);
    }

    /**
     * Creates a new command line argument.
     *
     * @param name      name to type
     * @param valueType type of value to store in the argument
     * @param aliases alternative names to type
     */
    public CommandLineArgument(String name, ValueType valueType, String... aliases) {
        this(name, "", valueType, aliases);
    }

    /**
     * Creates a new command line argument.
     *
     * @param name    name to type
     * @param help    help displayed with <code>--help</code>
     * @param aliases alternative names to type
     */
    public CommandLineArgument(String name, String help, String... aliases) {
        this(name, help, ValueType.VALUE, aliases);
    }

    /**
     * Creates a new command line argument.
     *
     * @param name      name to type
     * @param help      help displayed with <code>--help</code>
     * @param valueType type of value to store in the argument
     * @param aliases   alternative names to type
     */
    public CommandLineArgument(String name, String help, ValueType valueType, String... aliases) {
        this.name = name.replace(" ", "_");
        this.help = help;
        this.valueType = valueType;
        this.choices = null;
        this.listSize = 0;
        this.aliases = aliases == null ? Collections.emptyList() : List.of(aliases);
    }

    /**
     * Sets the value of the command.
     *
     * @param value value to set to
     */
    public void setValue(String value) {
        switch(valueType) {
            case VALUE:
                this.value = value;
                break;
            case CHOICE:
                if(choices.size() > 0 && choices.contains(value)) {
                    this.value = value;
                }
                else {
                    this.value = this.defaultValue;
                }
                break;
            default:
                this.value = this.defaultValue;
                break;
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

    public final ValueType getValueType() {
        return valueType;
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

    protected StringBuilder getFormattedName() {
        StringBuilder builder = new StringBuilder(name);

        if(aliases.size() > 0) {
            builder.append(", ")
                    .append(String.join(", ", aliases));
        }

        return builder;
    }
}
