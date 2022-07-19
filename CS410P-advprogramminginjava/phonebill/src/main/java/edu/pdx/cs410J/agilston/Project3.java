package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.agilston.commandline.CommandLineParser;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {
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
     * Validates all command line arguments have been given.
     *
     * @param parser parser to validate with
     * @param args   command line arguments
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
        String beginTime;
        String endTime;

        parser.parse(args);

        if(parser.hasArgument("-help")) {
            System.out.print(parser.getUsageInformation());
            return false;
        }

        if(parser.hasArgument("-README")) {
            try(InputStream readmeFile = Project3.class.getResourceAsStream("README.txt")) {
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

        beginTime = String.format("%s %s %s",
                parser.getValueOrDefault("begin_date", 0),
                parser.getValueOrDefault("begin_date", 1),
                parser.getValueOrDefault("begin_date", 2));

        if(!isValidDateTime(beginTime)) {
            throw new IllegalArgumentException("Invalid argument: Begin time must be in format mm/dd/yyyy hh:mm am/pm or "
                    + "m/d/yyyy h:mm AM/PM");
        }

        endTime = String.format("%s %s %s",
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

        return true;
    }

    static boolean canWriteFile(String filename) throws IOException, SecurityException {
        File file = new File(filename);
        File parent;

        if((parent = file.getParentFile()) != null) {
            parent.mkdirs();
        }

        return file.createNewFile() || file.canWrite();
    }

    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser("phonebill-2022.0.0.jar");
        String filename;
        String prettyFilename;
        PhoneBill bill = null;
        PhoneCall call;
        TextParser textParser;
        TextDumper textDumper;
        PrettyPrinter prettyPrinter;
        String customer;

        try {
            parser.setMaxLineLength(80);
            parser.setPrologue("This program creates a phone bill for a customer.");
            parser.setEpilogue("Date and time must be in m/d/yyyy h:mm am/pm format. Month, day, and hour may " +
                    "be one digit or two. Year must always be four digits.");

            parser.addFlag("-print", "Prints a description of the new phone call", "-p");
            parser.addFlag("-README", "Prints a README for this project and exits", "-r");
            parser.addFlag("-help", "Prints usage information", "-h");
            parser.addFlag("-textFile", "Where to read/write the phone bill",
                    new String[] { "file_name" }, "-t");
            parser.addFlag("-pretty", "Pretty print the phone bill to a text file or standard out (file -)",
                    new String[] { "file_name" }, "-y");
            parser.addArgument("customer", "Person whose phone bill we're modeling");
            parser.addArgument("caller_number", "Phone number of caller");
            parser.addArgument("callee_number", "Phone number of person who was called");
            parser.addListArgument("begin_date", "Date call began (mm/dd/yyyy hh:mm am/pm)",
                    new String[] { "date", "time", "am_pm" });
            parser.addListArgument("end_date", "Date call ended (mm/dd/yyyy hh:mm am/pm)",
                    new String[] { "date", "time", "am_pm" });

            if(!validateArguments(parser, args)) {
                return;
            }

            customer = parser.getValueOrDefault("customer");

            if(!Objects.equals(filename = parser.getValueOrDefault("-textFile"), "")) {
                try {
                    textParser = new TextParser(new FileReader(filename));
                    bill = textParser.parse();

                    if(!Objects.equals(customer, bill.getCustomer())) {
                        throw new IllegalArgumentException("Invalid file: Customer names do not match");
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
                    String.format("%s %s %s",
                            parser.getValueOrDefault("begin_date", 0),
                            parser.getValueOrDefault("begin_date", 1),
                            parser.getValueOrDefault("begin_date", 2)),
                    String.format("%s %s %s",
                            parser.getValueOrDefault("end_date", 0),
                            parser.getValueOrDefault("end_date", 1),
                            parser.getValueOrDefault("end_date", 2)));

            bill.addPhoneCall(call);

            if(!Objects.equals(filename, "") && canWriteFile(filename)) {
                textDumper = new TextDumper(new FileWriter(filename));
                textDumper.dump(bill);
            }

            if(parser.hasArgument("-pretty")) {
                prettyFilename = parser.getValueOrDefault("-pretty");

                if(Objects.equals(prettyFilename, "-")) {
                    prettyPrinter = new PrettyPrinter(new PrintWriter(System.out));
                }
                else {
                    prettyPrinter = new PrettyPrinter(new FileWriter(prettyFilename));
                }

                prettyPrinter.dump(bill);
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
