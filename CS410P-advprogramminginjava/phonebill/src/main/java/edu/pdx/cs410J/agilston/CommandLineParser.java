package edu.pdx.cs410J.agilston;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class used to parse command line arguments.
 */
public class CommandLineParser {
    private final List<String> flags;
    private final Map<String, String> options;
    private final List<String> arguments;

    /**
     * Creates a new CommandLineParser.
     * @param args arguments to parse
     */
    public CommandLineParser(String[] args) {
        flags = new ArrayList<>();
        arguments = new ArrayList<>();
        options = new HashMap<>();
        parseArgs(args);
    }

    /**
     * Checks whether the command line arguments had a certain flag.
     * @param flagName flag name (e.g., --flag, -f)
     * @return whether the flag was given
     */
    public boolean hasFlag(String flagName) {
        return flags.contains(flagName);
    }

    /**
     * Gets all positional command line arguments.
     */
    public List<String> getArguments() {
        return this.arguments;
    }

    /**
     * Checks whether a command line option with its own arguments was given.
     * @param optionName option name (e.g., <code>--option=</code>, <code>-o=</code>)
     * @return whether the option was given
     */
    public boolean hasOption(String optionName) {
        return options.containsKey(optionName);
    }

    /**
     * Gets the value of a command line option.
     * @param optionName option name (e.g., <code>--option=</code>, <code>-o=</code>)
     * @return value of the option (after the <code>=</code>)
     */
    public String getOption(String optionName) {
        return options.get(optionName);
    }

    /**
     * Parses the given command line arguments. Only supports options in the form <code>--option=value</code> or
     * <code>-o=value</code>.
     * @param args command line arguments
     */
    private void parseArgs(String[] args) {
        Pattern shortFlag = Pattern.compile("-(\\w+)");
        Pattern longFlag = Pattern.compile("--(\\w+)");
        Pattern option = Pattern.compile(longFlag + "=(.*)");
        Matcher matcher;

        for(String arg : args) {
            if((matcher = shortFlag.matcher(arg)).matches()) {
                for(String group : matcher.group(1).split("")) {
                    flags.add(group.toLowerCase());
                }
            }
            else if((matcher = longFlag.matcher(arg)).matches()) {
                flags.add(matcher.group(1).toLowerCase());
            }
            else if((matcher = option.matcher(arg)).matches()) {
                options.put(matcher.group(1).toLowerCase(), matcher.group(2));
            }
            else {
                arguments.add(arg);
            }
        }
    }
}
