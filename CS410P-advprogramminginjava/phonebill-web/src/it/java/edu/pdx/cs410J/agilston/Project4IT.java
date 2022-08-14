package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    /**
     * Invokes the main method of {@link Project4} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project4.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardOut(), containsString("phonebill-2022.0.0.jar"));
    }

    @Test
    void testInvalidCallerPhoneNumber() {
        MainMethodResult result = invokeMain(
                "Name",
                "(555) 555-5555",
                "888-888-8888",
                "01/01/2022",
                "12:00",
                "am",
                "01/01/2022",
                "01:00",
                "am");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Invalid argument: Caller number must be in format ###-###-####"));
    }

    @Test
    void testInvalidCalleePhoneNumber() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "(888) 888-8888",
                "01/01/2022",
                "12:00",
                "am",
                "01/01/2022",
                "01:00",
                "am");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Invalid argument: Callee number must be in format ###-###-####"));
    }

    @Test
    void testInvalidBeginDateTime() {
        String[] begin = { "01/01/22", "12:00", "am" };
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                begin[0],
                begin[1],
                begin[2],
                "01/01/2022",
                "01:00",
                "am");
        assertThat(result.getTextWrittenToStandardError(),
                containsString(String.format("Invalid argument: Begin time %s %s %s must be in format "
                        + "mm/dd/yyyy hh:mm am/pm or m/d/yyyy h:mm AM/PM", begin[0], begin[1], begin[2])));
    }

    @Test
    void testInvalidEndDateTime() {
        String[] end = { "01/01/22", "01:00", "am" };
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "12:00",
                "am",
                end[0],
                end[1],
                end[2]);
        assertThat(result.getTextWrittenToStandardError(),
                containsString(String.format("Invalid argument: End time %s %s %s must be in format "
                        + "mm/dd/yyyy hh:mm am/pm or m/d/yyyy h:mm AM/PM", end[0], end[1], end[2])));
    }

    @Test
    void testUsagePrintingWithHelpArgument() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "12:00",
                "am",
                "01/01/2022",
                "01:00",
                "am",
                "-help");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("usage: phonebill-2022.0.0.jar"));
    }

    @Test
    void testReadmePrintingWithArgument() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "12:00",
                "am",
                "01/01/2022",
                "01:00",
                "am",
                "-README");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Class:   CS410P - Advanced Programming in Java"));
    }

    @Test
    void testPrintingWithArgument() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "12:00",
                "am",
                "01/01/2022",
                "01:00",
                "am",
                "-host",
                "localhost",
                "-port",
                "8080",
                "-print");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("phone call from 555-555-5555 to 888-888-8888 from 01/01/22 12:00 am to " +
                        "01/01/22 01:00 am"));
    }

    @Test
    void endTimeBeforeBeginTimeFails() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "08:00",
                "am",
                "01/01/2022",
                "07:00",
                "am");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Invalid argument: End time must be at or after begin time"));
    }

    @Test
    void testSearchArgumentWithoutTimes() {
        MainMethodResult result = invokeMain(
                "Name",
                "-search"
        );
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Invalid argument: -search requires customer, begin, and end to be provided"));
    }

    @Test
    void testAllArgumentsExceptHost() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "08:00",
                "am",
                "01/01/2022",
                "09:00",
                "am",
                "-port",
                "8080");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Host must be specified"));
    }

    @Test
    void testAllArgumentsExceptPort() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "08:00",
                "am",
                "01/01/2022",
                "09:00",
                "am",
                "-host",
                "localhost");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Port must be specified"));
    }

    @Test
    void testPortNotANumber() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "08:00",
                "am",
                "01/01/2022",
                "09:00",
                "am",
                "-host",
                "localhost",
                "-port",
                "nan");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Port must be a number"));
    }

    @Test
    void testRemoveAllMappings() throws IOException {
        PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
        client.removeAllPhoneBills();
    }

    // The following tests are from the Project4 grading email
    @Test
    void test0AddCall() {
        MainMethodResult result = invokeMain(
                ("-host localhost -port 8080 -print Project4 123-456-7890 237-425-2345 01/08/2022 8:00 am 01/08/2022 "
                        + "8:02 am").split(" "));

        assertThat(result.getTextWrittenToStandardOut(), containsString("Added phone call from 123-456-7890 "
                + "to 237-425-2345 from 01/08/22 08:00 am to 01/08/22 08:02 am to Project4"));
    }

    @Test
    void test1QueryEmptyPhoneBill() {
        MainMethodResult result = invokeMain(
                "-search -host localhost -port 8080 Project4 01/06/2022 5:00 pm 01/06/2022 7:00 pm".split(" "));

        assertThat(result.getTextWrittenToStandardOut(), containsString("There are no phone calls associated "
                + "with Project4."));
    }

    @Test
    void test2AddSecondPhoneCall() {
        MainMethodResult result = invokeMain(
                "-host localhost -port 8080 Project4 123-456-7890 234-235-2354 01/10/2022 10:00 am 01/10/2022 10:14 am"
                        .split(" "));

        assertThat(result.getTextWrittenToStandardOut(), containsString("Added phone call from 123-456-7890 "
                + "to 234-235-2354 from 01/10/22 10:00 am to 01/10/22 10:14 am to Project4"));
    }

    @Test
    void test3AddThirdPhoneCall() {
        MainMethodResult result = invokeMain(
                "-host localhost -port 8080 Project4 123-456-7890 124-142-6246 01/12/2022 12:00 pm 01/12/2022 12:15 pm"
                        .split(" "));

        assertThat(result.getTextWrittenToStandardOut(), containsString("Added phone call from 123-456-7890 "
                + "to 124-142-6246 from 01/12/22 12:00 pm to 01/12/22 12:15 pm to Project4"));
    }

    @Test
    void test4QueryPhoneCall() {
        MainMethodResult result = invokeMain(
                "-search -host localhost -port 8080 Project4 01/09/2022 1:00 pm 01/20/2022 1:23 pm".split(" "));

        assertThat(result.getTextWrittenToStandardOut(), containsString("234-235-2354"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("124-142-6246"));
    }

    @Test
    void test5AddFourthPhoneCall() {
        MainMethodResult result = invokeMain(
                "-host localhost -port 8080 Project4 123-456-7890 124-142-6246 01/31/2022 12:00 pm 01/31/2022 12:15 pm"
                        .split(" "));

        assertThat(result.getTextWrittenToStandardOut(), containsString("Added phone call from 123-456-7890 "
                + "to 124-142-6246 from 01/31/22 12:00 pm to 01/31/22 12:15 pm to Project4"));
    }

    @Test
    void test6QueryPhoneBill() {
        MainMethodResult result = invokeMain(
                "-search -host localhost -port 8080 Project4 01/30/2022 12:00 pm 02/01/2022 12:15 pm".split(" "));

        assertThat(result.getTextWrittenToStandardOut(), containsString(""));
    }

    @Test
    void test7AddSecondPhoneBill() {
        MainMethodResult result = invokeMain(
                ("-host localhost -port 8080 -print Project4a 131-313-1313 234-345-5467 01/13/2022 8:00 am 01/13/2022 "
                        + "8:02 am").split(" "));

        assertThat(result.getTextWrittenToStandardOut(), containsString("Added phone call from 131-313-1313 "
                + "to 234-345-5467 from 01/13/22 08:00 am to 01/13/22 08:02 am to Project4a"));
    }

    @Test
    void test8AddPhoneCallToSecondBill() {
        MainMethodResult result = invokeMain(
                ("-host localhost -port 8080 -print Project4a 131-313-1313 123-456-7890 02/13/2022 9:00 am 02/13/2022 "
                        + "10:02 am").split(" "));

        assertThat(result.getTextWrittenToStandardOut(), containsString("Added phone call from 131-313-1313 "
                + "to 123-456-7890 from 02/13/22 09:00 am to 02/13/22 10:02 am to Project4a"));
    }

    @Test
    void test9QuerySecondPhoneBill() {
        MainMethodResult result = invokeMain(
                "-search -host localhost -port 8080 Project4a 02/01/2022 12:00 am 02/15/2022 12:00 am".split(" "));

        assertThat(result.getTextWrittenToStandardOut(), containsString("123-456-7890"));
    }
}
