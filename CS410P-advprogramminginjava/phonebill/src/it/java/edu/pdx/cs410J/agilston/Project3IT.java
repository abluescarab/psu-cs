package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
class Project3IT extends InvokeMainTestCase {
    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project3.class, args);
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
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/22",
                "12:00",
                "am",
                "01/01/2022",
                "01:00",
                "am");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Invalid argument: Begin time must be in format mm/dd/yyyy hh:mm am/pm or "
                        + "m/d/yyyy h:mm AM/PM"));
    }

    @Test
    void testInvalidEndDateTime() {
        MainMethodResult result = invokeMain(
                "Name",
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "12:00",
                "am",
                "01/01/22",
                "01:00",
                "am");
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Invalid argument: End time must be in format mm/dd/yyyy hh:mm am/pm or "
                        + "m/d/yyyy h:mm AM/PM"));
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
                "-print");
        assertThat(result.getTextWrittenToStandardOut(),
                containsString("Phone call from 555-555-5555 to 888-888-8888 from 01/01/22 12:00 am to " +
                        "01/01/22 01:00 am"));
    }

    @Test
    void testFileWithMatchingCustomerNameParses(@TempDir File tempDir) throws IOException {
        String customer = "Name";
        String fileName = "bill.txt";
        PhoneBill bill = new PhoneBill(customer);
        File file = new File(tempDir, fileName);
        TextDumper textDumper = new TextDumper(new FileWriter(file));

        textDumper.dump(bill);

        MainMethodResult result = invokeMain(
                customer,
                "555-555-5555",
                "888-888-8888",
                "01/01/2022",
                "12:00",
                "am",
                "01/01/2022",
                "01:00",
                "am",
                "-textFile",
                file.getCanonicalPath());

        assertThat(result.getTextWrittenToStandardError(), not(containsString("Invalid file")));
    }

    @Test
    void testFileWithoutMatchingCustomerNameFails(@TempDir File tempDir) throws IOException {
        String fileName = "bill.txt";
        PhoneBill bill = new PhoneBill("Customer Name");
        File file = new File(tempDir, fileName);
        TextDumper textDumper = new TextDumper(new FileWriter(file));

        textDumper.dump(bill);

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
                "-textFile",
                file.getCanonicalPath());

        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid file"));
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
    void prettyPrinterPrintsCorrectlyToStandardOut() {
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
                "-pretty",
                "-");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone bill for Name:"));
    }
}
