package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project1.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    void testInvalidCallerPhoneNumber() {
        MainMethodResult result = invokeMain(
                "Name",
                "(555) 555-5555",
                "888-888-8888",
                "01/01/2022",
                "00:00",
                "01/01/2022",
                "01:00");
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
                "00:00",
                "01/01/2022",
                "01:00");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Invalid argument: Callee number must be in format ###-###-####"));
    }

    @Test
    void testInvalidBeginDateTime() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/22",
                "00:00",
                "01/01/2022",
                "01:00");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Invalid argument: Begin time must be in format mm/dd/yyyy hh:mm or "
                        + "m/d/yyyy h:mm"));
    }

    @Test
    void testInvalidEndDateTime() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "00:00",
                "01/01/22",
                "01:00");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Invalid argument: End time must be in format mm/dd/yyyy hh:mm or "
                        + "m/d/yyyy h:mm"));
    }

    @Test
    void testUsagePrintingWithHelpArgument() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "00:00",
                "01/01/2022",
                "01:00",
                "--help");
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
                "00:00",
                "01/01/2022",
                "01:00",
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
                "00:00",
                "01/01/2022",
                "01:00",
                "-print");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Phone call from 555-555-5555 to 888-888-8888 from 01/01/2022 00:00 to " +
                        "01/01/2022 01:00"));
    }
}
