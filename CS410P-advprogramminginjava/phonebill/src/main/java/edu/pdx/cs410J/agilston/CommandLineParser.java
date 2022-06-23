package edu.pdx.cs410J.agilston;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineParser {
    private List<String> flags;
    private Map<String, String> options;
    private List<String> arguments;

    public CommandLineParser(String[] args) {
        flags = new ArrayList<>();
        arguments = new ArrayList<>();
        options = new HashMap<>();
        parseArgs(args);
    }

    public boolean hasFlag(String flagName) {
        return flags.contains(flagName);
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    public boolean hasOption(String optionName) {
        return options.containsKey(optionName);
    }

    public String getOption(String optionName) {
        return options.get(optionName);
    }

    private void parseArgs(String[] args) {
        Pattern shortFlag = Pattern.compile("-(\\w+)");
        Pattern longFlag = Pattern.compile("--(\\w+)");
        Pattern option = Pattern.compile(longFlag + "=(.*)");
        Matcher matcher;

        for(String arg : args) {
            if((matcher = shortFlag.matcher(arg)).matches()) {
                flags.addAll(List.of(matcher.group(1).split("")));
            }
            else if((matcher = longFlag.matcher(arg)).matches()) {
                flags.add(matcher.group(1));
            }
            else if((matcher = option.matcher(arg)).matches()) {
                options.put(matcher.group(1), matcher.group(2));
            }
            else {
                arguments.add(arg);
            }
        }
    }
}
