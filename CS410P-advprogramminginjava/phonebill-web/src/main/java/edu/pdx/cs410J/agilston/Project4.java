package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.agilston.commandline.CommandLineParser;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {
    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        CommandLineParser parser = createParser();

        try {
//            String hostName = null;
//            String portString = null;
//            String word = null;
//            String definition = null;
//
//            for(String arg : args) {
//                if(hostName == null) {
//                    hostName = arg;
//
//                }
//                else if(portString == null) {
//                    portString = arg;
//
//                }
//                else if(word == null) {
//                    word = arg;
//
//                }
//                else if(definition == null) {
//                    definition = arg;
//
//                }
//                else {
//                    usage("Extraneous command line argument: " + arg);
//                }
//            }
//
//            if(hostName == null) {
//                usage(MISSING_ARGS);
//
//            }
//            else if(portString == null) {
//                usage("Missing port");
//            }
//
//            int port;
//            try {
//                port = Integer.parseInt(portString);
//
//            }
//            catch(NumberFormatException ex) {
//                usage("Port \"" + portString + "\" must be an integer");
//                return;
//            }
//            PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);
//
//            String message;
//            try {
//                if(word == null) {
//                    // Print all word/definition pairs
//                    Map<String, String> dictionary = client.getAllDictionaryEntries();
//                    StringWriter sw = new StringWriter();
//                    PrettyPrinter pretty = new PrettyPrinter(sw);
//                    pretty.dump(dictionary);
//                    message = sw.toString();
//
//                }
//                else if(definition == null) {
//                    // Print all dictionary entries
//                    message = PrettyPrinter.formatDictionaryEntry(word, client.getDefinition(word));
//
//                }
//                else {
//                    // Post the word/definition pair
//                    client.addDictionaryEntry(word, definition);
//                    message = Messages.definedWordAs(word, definition);
//                }
//
//            }
//            catch(IOException | ParserException ex) {
//                error("While contacting server: " + ex);
//                return;
//            }
//
//            System.out.println(message);
            PhoneBillRestClient client = new PhoneBillRestClient(parser.getValueOrDefault("-host"),
                    Integer.parseInt(parser.getValueOrDefault("-port")));
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
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

        parser.addFlag("-print", "Prints a description of the new phone call", "-p");
        parser.addFlag("-README", "Prints a README for this project and exits", "-r");
        parser.addFlag("-help", "Prints usage information", "-h");
        parser.addFlag("-host", "Host computer on which the server runs");
        parser.addFlag("-port", "Port on which the server is listening");
        parser.addFlag("-search", "Phone calls which should be searched for");
        parser.addArgument("customer", "Person whose phone bill we're modeling");
        parser.addArgument("caller_number", "Phone number of caller");
        parser.addArgument("callee_number", "Phone number of person who was called");
        parser.addListArgument("begin", "Date and time call began",
                new String[] { "date", "time", "am_pm" });
        parser.addListArgument("end", "Date and time call ended",
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

        String beginTime = String.format("%s %s %s",
                parser.getValueOrDefault("begin_date", 0),
                parser.getValueOrDefault("begin_date", 1),
                parser.getValueOrDefault("begin_date", 2));

        if(!isValidDateTime(beginTime)) {
            throw new IllegalArgumentException("Invalid argument: Begin time must be in format mm/dd/yyyy hh:mm am/pm or "
                    + "m/d/yyyy h:mm AM/PM");
        }

        String endTime = String.format("%s %s %s",
                parser.getValueOrDefault("end_date", 0),
                parser.getValueOrDefault("end_date", 1),
                parser.getValueOrDefault("end_date", 2));

        if(!isValidDateTime(endTime)) {
            throw new IllegalArgumentException("Invalid argument: End time must be in format mm/dd/yyyy hh:mm am/pm or "
                    + "m/d/yyyy h:mm AM/PM");
        }

        if(ChronoUnit.MILLIS.between(PhoneCall.formatDateTime(beginTime), PhoneCall.formatDateTime(endTime)) < 0) {
            throw new IllegalArgumentException("Invalid argument: End time must be at or after begin time");
        }

        try {
            Integer.parseInt(parser.getValueOrDefault("-port"));
        }
        catch(NumberFormatException e) {
            throw new IllegalArgumentException("Port must be a number", e);
        }

        return true;
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     *
     * @param code     The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode(int code, HttpRequestHelper.Response response) {
        if(response.getHttpStatusCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                    response.getHttpStatusCode(), response.getContent()));
        }
    }

    /**
     * Prints an error message to <code>System.err</code>.
     *
     * @param message
     */
    private static void error(String message) {
        PrintStream err = System.err;
        err.println("** " + message);
    }
}
