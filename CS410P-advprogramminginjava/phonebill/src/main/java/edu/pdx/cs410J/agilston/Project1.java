package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;

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
    private static void validateArguments(List<String> args) {
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
            printUsage();
            throw new IllegalArgumentException("Missing command line arguments: " + missingArgs);
        }
    }

    /**
     * Prints command line usage information.
     */
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
        String customer;
        String callerNumber;
        String calleeNumber;
        String begin;
        String end;

        if(parser.hasFlag("help") || parser.hasFlag("h")) {
            printUsage();
            return;
        }

        if(parser.hasFlag("README")) {
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
                throw new IllegalArgumentException("Invalid argument: begin time must be in format mm/dd/yyyy hh:mm or "
                        + "m/d/yyyy h:mm");
            }

            end = arguments.get(5) + " " + arguments.get(6);

            if(!isValidDateTime(end)) {
                throw new IllegalArgumentException("Invalid argument: end time must be in format mm/dd/yyyy hh:mm or "
                        + "m/d/yyyy h:mm");
            }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, begin, end);
        PhoneBill bill = new PhoneBill(customer);
        bill.addPhoneCall(call);

        if(parser.hasFlag("print")) {
            System.out.println(call);
        }
    }
}
