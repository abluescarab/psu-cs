package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.agilston.commandline.CommandLineParser;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {
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
     * @return whether arguments successfully validated
     */
    static boolean validateArguments(CommandLineParser parser, String[] args) {
        List<String> missingArgs = List.of(
                "customer",
                "caller_number",
                "callee_number",
                "begin_date",
                "begin_time",
                "end_date",
                "end_time"
        );

        parser.parse(args);

        if(parser.hasArgument("-help")) {
            System.out.print(parser.getUsageInformation());
            return false;
        }

        if(parser.hasArgument("-README")) {
            try(InputStream readmeFile = Project2.class.getResourceAsStream("README.txt")) {
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

            return false;
        }

        if(args.length < missingArgs.size()) {
            throw new IllegalArgumentException("Missing command line arguments: "
                    + String.join(", ", missingArgs.subList(args.length, missingArgs.size())));
        }

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

        return true;
    }

    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser("phonebill-2022.0.0.jar");
        String filename;
        PhoneBill bill = null;
        PhoneCall call;
        TextParser textParser;
        TextDumper textDumper;
        String customer;

        try {
            parser.setMaxLineLength(80);
            parser.setPrologue("This program creates a phone bill for a customer.");
            parser.setEpilogue("Date and time must be in m/d/yyyy h:mm format. Month, day, and hour may " +
                    "be one digit or two. Year must always be four digits.");

            parser.addFlag("-print", "Prints a description of the new phone call", false, "-p");
            parser.addFlag("-README", "Prints a README for this project and exits", false, "-r");
            parser.addFlag("-help", "Prints usage information", false, "-h");
            parser.addFlag("-textFile", "Where to read/write the phone bill", true, "-t");
            parser.addArgument("customer", "Person whose phone bill we're modeling");
            parser.addArgument("caller_number", "Phone number of caller");
            parser.addArgument("callee_number", "Phone number of person who was called");
            parser.addArgument("begin_date", "Date call began (mm/dd/yyy)");
            parser.addArgument("begin_time", "Time call began (24-hour time)");
            parser.addArgument("end_date", "Date call ended (mm/dd/yyy)");
            parser.addArgument("end_time", "Time call ended (24-hour time)");

            if(!validateArguments(parser, args)) {
                return;
            }

            customer = parser.getValueOrDefault("customer");

            // TODO: throw error if customer in text file does not match given
            if(!Objects.equals(filename = parser.getValueOrDefault("-textFile"), "")) {
                try {
                    textParser = new TextParser(new FileReader(filename));
                    bill = textParser.parse();

                    if(!Objects.equals(customer, bill.getCustomer())) {
                        throw new IllegalArgumentException("Customer name from file does not match customer argument");
                    }
                }
                catch(FileNotFoundException ignored) {
                }
            }

            if(bill == null) {
                bill = new PhoneBill(customer);
            }

            call = new PhoneCall(
                    parser.getValueOrDefault("caller_number"),
                    parser.getValueOrDefault("callee_number"),
                    parser.getValueOrDefault("begin_date") + " " + parser.getValueOrDefault("begin_time"),
                    parser.getValueOrDefault("end_date") + " " + parser.getValueOrDefault("end_time"));

            bill.addPhoneCall(call);

            if(!Objects.equals(filename, "")) {
                try {
                    textDumper = new TextDumper(new FileWriter(filename));
                    textDumper.dump(bill);
                }
                catch(IOException e) {
                    throw new IOException("Could not save file: invalid file name");
                }
            }

            if(parser.hasArgument("-print")) {
                System.out.println(call);
            }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            System.out.println("To view usage information, run with -help.");
        }
    }
}
