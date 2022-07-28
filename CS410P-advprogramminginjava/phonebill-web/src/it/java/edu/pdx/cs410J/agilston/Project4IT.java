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
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
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
}
