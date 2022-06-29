package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.agilston.commandline.CommandLineParser;
import edu.pdx.cs410J.agilston.commandline.arguments.CommandLineArgument;
import edu.pdx.cs410J.agilston.commandline.arguments.CommandLineArgument.ValueType;

import java.util.List;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {
    /**
     * Checks if a string is a valid phone number.
     * @param phoneNumber phone number string
     * @return <code>true</code> if valid phone number; <code>false</code> if not
     */
    @VisibleForTesting
    static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}");
    }

    /**
     * Checks if a string is a valid date and time.
     * @param dateTime date and time string
     * @return <code>true</code> if valid date time; <code>false</code> if not
     */
    static boolean isValidDateTime(String dateTime) {
        return dateTime.matches("(?:\\d{1,2}/){2}\\d{4} \\d{1,2}:\\d{1,2}");
    }

    /**
     * Validates all command line arguments have been given.
     * @param args command line arguments
     */
    private static void validateArguments(CommandLineParser parser, List<String> args) {
        String missingArgs = "";

        if(args.size() < 1) {
            missingArgs += "<customer> ";
        }

        if(args.size() < 2) {
            missingArgs += "<caller number> ";
        }

        if(args.size() < 3) {
            missingArgs += "<callee number> ";
        }

        if(args.size() < 4) {
            missingArgs += "<begin date> ";
        }

        if(args.size() < 5) {
            missingArgs += "<begin time> ";
        }

        if(args.size() < 6) {
            missingArgs += "<end date> ";
        }

        if(args.size() < 7) {
            missingArgs += "<end time>";
            parser.printUsage(System.out);
            throw new IllegalArgumentException("Missing command line arguments: " + missingArgs);
        }
    }

    public static void main(String[] args) {
        try {
            CommandLineParser parser = new CommandLineParser("phonebill-2022.0.0.jar");
            parser.setMaxLineLength(80);
            parser.setPrologue("This program creates a phone bill for a customer.");
            parser.setEpilogue("Date and time must be in m/d/yyyy h:mm format. Month, day, and hour may " +
                            "be one digit or two. Year must always be four digits.");

            parser.addArgument("--print", "Prints a description of the new phone call", "-p");
            parser.addArgument("--readme", "Prints a README for this project and exits", "-r");
            parser.addArgument("--help", "Prints usage information", "-h");
            parser.addArgument("customer", "Person whose phone bill we're modeling");
            parser.addArgument("caller_number", "Phone number of caller");
            parser.addArgument("callee_number", "Phone number of person who was called");
            parser.addArgument("begin_date", "Date call began (mm/dd/yyy)");
            parser.addArgument("begin_time", "Time call began (24-hour time)");
            parser.addArgument("end_date", "Date call ended (mm/dd/yyy)");
            parser.addArgument("end_time", "Time call ended (24-hour time)");

            parser.printUsage(System.out);
            parser.parse(args);

            System.err.println("Missing command line arguments");
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }

//        CommandLineParser parser = new CommandLineParser(args);
//        List<String> arguments = parser.getArguments();
//        String customer;
//        String callerNumber;
//        String calleeNumber;
//        String begin;
//        String end;
//
//        if(parser.hasFlag("help") || parser.hasFlag("h")) {
//            printUsage();
//            return;
//        }
//
//        if(parser.hasFlag("README")) {
//            try(InputStream readmeFile = Project1.class.getResourceAsStream("README.txt")) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(readmeFile));
//                StringBuilder readme = new StringBuilder();
//                String line;
//
//                while((line = reader.readLine()) != null) {
//                    readme.append(line);
//                }
//
//                System.out.println(readme);
//            }
//            catch(IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            return;
//        }
//
//        try {
//            validateArguments(arguments);
//
//            customer = arguments.get(0);
//            callerNumber = arguments.get(1);
//
//            if(!isValidPhoneNumber(callerNumber)) {
//                throw new IllegalArgumentException("Invalid argument: caller number must be in format ###-###-####");
//            }
//
//            calleeNumber = arguments.get(2);
//
//            if(!isValidPhoneNumber(calleeNumber)) {
//                throw new IllegalArgumentException("Invalid argument: callee number must be in format ###-###-####");
//            }
//
//            begin = arguments.get(3) + " " + arguments.get(4);
//
//            if(!isValidDateTime(begin)) {
//                throw new IllegalArgumentException("Invalid argument: begin time must be in format mm/dd/yyyy hh:mm or "
//                        + "m/d/yyyy h:mm");
//            }
//
//            end = arguments.get(5) + " " + arguments.get(6);
//
//            if(!isValidDateTime(end)) {
//                throw new IllegalArgumentException("Invalid argument: end time must be in format mm/dd/yyyy hh:mm or "
//                        + "m/d/yyyy h:mm");
//            }
//        }
//        catch(Exception e) {
//            System.err.println(e.getMessage());
//            return;
//        }
//
//        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, begin, end);
//        PhoneBill bill = new PhoneBill(customer);
//        bill.addPhoneCall(call);
//
//        if(parser.hasFlag("print")) {
//            System.out.println(call);
//        }
    }
}
