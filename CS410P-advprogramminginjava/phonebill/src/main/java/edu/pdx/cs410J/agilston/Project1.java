package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;

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

    private static void printUsage() {
        System.out.println("usage: java -jar target/phonebill-2022.0.0.jar [options] <args>");
        System.out.println("  args are (in this order):");
        System.out.println("    customer        Person whose phone bill we're modeling");
        System.out.println("    callerNumber    Phone number of caller");
        System.out.println("    calleeNumber    Phone number of person who was called");
        System.out.println("    begin           Date and time call began (24-hour time)");
        System.out.println("    end             Date and time call ended (24-hour time)");
        System.out.println("  options are (options may appear in any order):");
        System.out.println("    --print         Prints a description of the new phone call");
        System.out.println("    --README        Prints a README for this project and exits");
        System.out.println("  Date and time should be in the format: mm/dd/yyyy hh:mm");
    }

    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser(args);

        if(parser.hasFlag("help") || parser.hasFlag("h")) {
            printUsage();
            return;
        }

//        PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
//        System.err.println("Missing command line arguments");
//        for (String arg : args) {
//            System.out.println(arg);
//        }
    }
}
