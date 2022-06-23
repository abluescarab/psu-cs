package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;

import java.util.List;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {
    @VisibleForTesting
    static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}");
    }

    static boolean isValidDateTime(String dateTime) {
        return dateTime.matches("(?:\\d{1,2}/){2}\\d{4} \\d{1,2}:\\d{1,2}");
    }

    private static void validateArguments(List<String> args) {
        String missingArgs = "";

        if(args.size() < 1)
            missingArgs += "<customer> ";

        if(args.size() < 2)
            missingArgs += "<caller number> ";

        if(args.size() < 3)
            missingArgs += "<callee number> ";

        if(args.size() < 4)
            missingArgs += "<begin date> ";

        if(args.size() < 5)
            missingArgs += "<begin time> ";

        if(args.size() < 6)
            missingArgs += "<end date> ";

        if(args.size() < 7) {
            missingArgs += "<end time>";
            printUsage();
            throw new IllegalArgumentException("Missing command line arguments: " + missingArgs);
        }
    }

    private static void printUsage() {
        System.out.println("usage: java -jar target/phonebill-2022.0.0.jar [options] <args>");
        System.out.println("  args are (in this order):");
        System.out.println("    customer        Person whose phone bill we're modeling");
        System.out.println("    callerNumber    Phone number of caller");
        System.out.println("    calleeNumber    Phone number of person who was called");
        System.out.println("    begin date      Date call began (mm/dd/yyy)");
        System.out.println("    begin time      Time call began (24-hour time)");
        System.out.println("    end date        Date call ended (mm/dd/yyy)");
        System.out.println("    end time        Time call ended (24-hour time)");
        System.out.println("  options are (options may appear in any order):");
        System.out.println("    --print         Prints a description of the new phone call");
        System.out.println("    --README        Prints a README for this project and exits");
        System.out.println("  Date and time should be in the format: mm/dd/yyyy hh:mm or m/d/yyyy h:mm");
    }

    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser(args);
        List<String> arguments = parser.getArguments();
        String customer = "";
        String callerNumber = "";
        String calleeNumber = "";
        String begin = "";
        String end = "";

        if(parser.hasFlag("help") || parser.hasFlag("h")) {
            printUsage();
            return;
        }

        if(parser.hasFlag("README")) {
            // TODO: print README and exit
            return;
        }

        try {
            validateArguments(arguments);

            customer = arguments.get(0);
            callerNumber = arguments.get(1);

            if(!isValidPhoneNumber(callerNumber)) {
                throw new IllegalArgumentException("Invalid argument: caller number must be in format ###-###-####");
            }

            calleeNumber = arguments.get(2);

            if(!isValidPhoneNumber(calleeNumber)) {
                throw new IllegalArgumentException("Invalid argument: callee number must be in format ###-###-####");
            }

            begin = arguments.get(3) + " " + arguments.get(4);

            if(!isValidDateTime(begin)) {
                throw new IllegalArgumentException("Invalid argument: begin time must be in format mm/dd/yyyy hh:mm or m/d/yyyy h:mm");
            }

            end = arguments.get(5) + " " + arguments.get(6);

            if(!isValidDateTime(end)) {
                throw new IllegalArgumentException("Invalid argument: end time must be in format mm/dd/yyyy hh:mm or m/d/yyyy h:mm");
            }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, begin, end);

        if(parser.hasFlag("print")) {
            // TODO: print description of new phone call
        }
    }
}
