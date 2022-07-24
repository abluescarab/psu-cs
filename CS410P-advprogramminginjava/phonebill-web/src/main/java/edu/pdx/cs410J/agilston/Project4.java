package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.agilston.commandline.CommandLineParser;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {
    public static final String MISSING_ARGS = "Missing command line arguments";
    public static final String BILL_DELIMITER = ",";
    public static final String BILL_FORMAT = "%s" + BILL_DELIMITER + "%s" + BILL_DELIMITER + "%s" + BILL_DELIMITER
            + "%s";

    public static void main(String... args) {
        CommandLineParser parser = createParser();

        try {
            validateArguments(parser, args);

            PhoneBillRestClient client = new PhoneBillRestClient(parser.getValueOrDefault("-host"),
                    Integer.parseInt(parser.getValueOrDefault("-port")));
            String customer = parser.getValueOrDefault("customer");
            String message;

            try {
                if(customer == null) {
                    // Print all bills
                    Map<String, PhoneBill> dictionary = client.getAllPhoneBills();
                    StringWriter writer = new StringWriter();
                    PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
                    prettyPrinter.dump(dictionary);
                    message = writer.toString();
                }
                else {
                    // Add new call to bill
                    PhoneCall call = new PhoneCall(
                            parser.getValueOrDefault("caller_number"),
                            parser.getValueOrDefault("callee_number"),
                            parser.getAllValuesOrDefault("begin_date", " "),
                            parser.getAllValuesOrDefault("end_date", " "));
                    client.addPhoneCall(customer, call);
                    message = Messages.addedCustomerPhoneCall(customer, call);
                }
            }
            catch(IOException | ParserException e) {
                throw new RuntimeException("While contacting server: " + e, e);
            }

            System.out.println(message);
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
            System.out.println("To view usage information, run with -help.");
        }
    }

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
        return dateTime.matches(PhoneCall.DATE_TIME_REGEX);
    }

    /**
     * Creates a new command line parser.
     *
     * @return parser instance
     */
    private static CommandLineParser createParser() {
        CommandLineParser parser = new CommandLineParser("phonebill-2022.0.0.jar");

        parser.setMaxLineLength(80);
        parser.setPrologue("This program creates a phone bill via a RESTful web interface.");
        parser.setEpilogue("Date and time must be in m/d/yyyy h:mm am/pm format. Month, day, and hour may " +
                "be one digit or two. Year must always be four digits.");

        parser.addFlag("-print", "Prints a description of the new phone call", null, "-p");
        parser.addFlag("-README", "Prints a README for this project and exits", null, "-r");
        parser.addFlag("-help", "Prints usage information", null, "-h");
        parser.addFlag("-host", "Host computer on which the server runs", new String[] { "host_name" });
        parser.addFlag("-port", "Port on which the server is listening", new String[] { "port_number" });
        parser.addFlag("-search", "Phone calls which should be searched for", null);
        parser.addArgument("customer", "Person whose phone bill we're modeling");
        parser.addArgument("caller_number", "Phone number of caller");
        parser.addArgument("callee_number", "Phone number of person who was called");
        parser.addListArgument("begin_date", "Date and time call began",
                new String[] { "date", "time", "am_pm" });
        parser.addListArgument("end_date", "Date and time call ended",
                new String[] { "date", "time", "am_pm" });

        return parser;
    }

    /**
     * Validates all command line arguments have been given.
     *
     * @param parser parser to validate with
     * @param args   command line arguments
     * @return whether arguments successfully validated
     */
    private static boolean validateArguments(CommandLineParser parser, String[] args) {
        List<String> missingArgs = List.of(
                "customer",
                "caller_number",
                "callee_number",
                "begin_date",
                "begin_time",
                "begin_ampm",
                "end_date",
                "end_time",
                "end_ampm"
        );

        parser.parse(args);

        if(parser.hasArgument("-help")) {
            System.out.println(parser.getUsageInformation());
            return false;
        }

        if(parser.hasArgument("-README")) {
            try(InputStream readmeFile = Project4.class.getResourceAsStream("README.txt")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(readmeFile));
                StringBuilder readme = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null) {
                    readme.append(line)
                          .append(System.lineSeparator());
                }

                System.out.println(CommandLineParser.formatString(readme.toString(), parser.getMaxLineLength()));
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }

            return false;
        }

        if(!parser.hasArgument("-search") && args.length < missingArgs.size()) {
            throw new IllegalArgumentException(
                    String.format("%s: %s",
                            MISSING_ARGS,
                            String.join(", ", missingArgs.subList(args.length, missingArgs.size()))));
        }

        if(!isValidPhoneNumber(parser.getValueOrDefault("caller_number"))) {
            throw new IllegalArgumentException("Invalid argument: Caller number must be in format ###-###-####");
        }

        if(!isValidPhoneNumber(parser.getValueOrDefault("callee_number"))) {
            throw new IllegalArgumentException("Invalid argument: Callee number must be in format ###-###-####");
        }

        String beginTime = parser.getAllValuesOrDefault("begin_date", " ");

        if(!isValidDateTime(beginTime)) {
            throw new IllegalArgumentException(String.format("Invalid argument: Begin time %s must be in format "
                    + "mm/dd/yyyy hh:mm am/pm or m/d/yyyy h:mm AM/PM", beginTime));
        }

        String endTime = parser.getAllValuesOrDefault("end_date", " ");

        if(!isValidDateTime(endTime)) {
            throw new IllegalArgumentException(String.format("Invalid argument: End time %s must be in format "
                    + "mm/dd/yyyy hh:mm am/pm or m/d/yyyy h:mm AM/PM", endTime));
        }

        if(ChronoUnit.MILLIS.between(PhoneCall.formatDateTime(beginTime), PhoneCall.formatDateTime(endTime)) < 0) {
            throw new IllegalArgumentException("Invalid argument: End time must be at or after begin time");
        }

        if(!parser.hasArgument("-host")) {
            throw new IllegalArgumentException("Host must be specified");
        }

        if(!parser.hasArgument("-port")) {
            throw new IllegalArgumentException("Port must be specified");
        }

        try {
            Integer.parseInt(parser.getValueOrDefault("-port"));
        }
        catch(NumberFormatException e) {
            throw new IllegalArgumentException("Port must be a number", e);
        }

        return true;
    }
}
