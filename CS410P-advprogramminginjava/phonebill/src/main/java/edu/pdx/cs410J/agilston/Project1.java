package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.agilston.commandline.CommandLineParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {
    /**
     * Checks if a string is a valid phone number.
     *
     * @param phoneNumber phone number string
     * @return <code>true</code> if valid phone number; <code>false</code> if not
     */
    @VisibleForTesting
    static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}");
    }

    /**
     * Checks if a string is a valid date and time.
     *
     * @param dateTime date and time string
     * @return <code>true</code> if valid date time; <code>false</code> if not
     */
    static boolean isValidDateTime(String dateTime) {
        return dateTime.matches("(?:\\d{1,2}/){2}\\d{4} \\d{1,2}:\\d{1,2}");
    }

    /**
     * Validates all command line arguments have been given.
     *
     * @param args command line arguments
     */
    static void validateArguments(CommandLineParser parser, String[] args) {
        List<String> missingArgs = List.of(
                "customer",
                "caller_number",
                "callee_number",
                "begin_date",
                "begin_time",
                "end_date",
                "end_time"
        );

        if(args.length < missingArgs.size()) {
            throw new IllegalArgumentException("Missing command line arguments: "
                    + String.join(", ", missingArgs.subList(args.length, missingArgs.size())));
        }

        parser.parse(args);

        if(!isValidPhoneNumber(parser.getValueOrDefault("caller_number"))) {
            throw new IllegalArgumentException("Invalid argument: Caller number must be in format ###-###-####");
        }

        if(!isValidPhoneNumber(parser.getValueOrDefault("callee_number"))) {
            throw new IllegalArgumentException("Invalid argument: Callee number must be in format ###-###-####");
        }

        if(!isValidDateTime(String.format("%s %s", parser.getValueOrDefault("begin_date"),
                parser.getValueOrDefault("begin_time")))) {
            throw new IllegalArgumentException("Invalid argument: Begin time must be in format mm/dd/yyyy hh:mm or "
                    + "m/d/yyyy h:mm");
        }

        if(!isValidDateTime(String.format("%s %s", parser.getValueOrDefault("end_date"),
                parser.getValueOrDefault("end_time")))) {
            throw new IllegalArgumentException("Invalid argument: End time must be in format mm/dd/yyyy hh:mm or "
                    + "m/d/yyyy h:mm");
        }
    }

    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser("phonebill-2022.0.0.jar");

        try {
            parser.setMaxLineLength(80);
            parser.setPrologue("This program creates a phone bill for a customer.");
            parser.setEpilogue("Date and time must be in m/d/yyyy h:mm format. Month, day, and hour may " +
                    "be one digit or two. Year must always be four digits.");

            parser.addFlag("-print", "Prints a description of the new phone call", false, "-p");
            parser.addFlag("-README", "Prints a README for this project and exits", false, "-r");
            parser.addFlag("--help", "Prints usage information", false, "-h");
            parser.addArgument("customer", "Person whose phone bill we're modeling");
            parser.addArgument("caller_number", "Phone number of caller");
            parser.addArgument("callee_number", "Phone number of person who was called");
            parser.addArgument("begin_date", "Date call began (mm/dd/yyy)");
            parser.addArgument("begin_time", "Time call began (24-hour time)");
            parser.addArgument("end_date", "Date call ended (mm/dd/yyy)");
            parser.addArgument("end_time", "Time call ended (24-hour time)");

            validateArguments(parser, args);

            if(parser.hasArgument("--help")) {
                System.out.print(parser.getUsageInformation());
                return;
            }

            if(parser.hasArgument("-README")) {
                try(InputStream readmeFile = Project1.class.getResourceAsStream("README.txt")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(readmeFile));
                    StringBuilder readme = new StringBuilder();
                    String line;

                    while((line = reader.readLine()) != null) {
                        readme.append(line);
                    }

                    System.out.println(readme);
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }

                return;
            }

            PhoneCall call = new PhoneCall(
                    parser.getValueOrDefault("caller_number"),
                    parser.getValueOrDefault("callee_number"),
                    parser.getValueOrDefault("begin_date") + " " + parser.getValueOrDefault("begin_time"),
                    parser.getValueOrDefault("end_date") + " " + parser.getValueOrDefault("end_time"));
            PhoneBill bill = new PhoneBill(parser.getValueOrDefault("customer"));
            bill.addPhoneCall(call);

            if(parser.hasArgument("-print")) {
                System.out.println(call);
            }
        }
        catch(Exception e) {
            System.out.print(parser.getUsageInformation());
            System.err.println(e.getMessage());
        }
    }
}
