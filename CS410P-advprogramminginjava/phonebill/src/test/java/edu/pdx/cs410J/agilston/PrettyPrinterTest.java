package edu.pdx.cs410J.agilston;

import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * Unit tests for the {@link PrettyPrinter} class.
 */
public class PrettyPrinterTest {
    @Test
    void customerNameDumpedCorrectly() {
        String customer = "Customer Name";
        PhoneBill bill = new PhoneBill(customer);
        StringWriter writer = new StringWriter();
        PrettyPrinter printer = new PrettyPrinter(writer);

        printer.dump(bill);
        assertThat(writer.toString(), containsString(customer));
    }

    @Test
    void durationCalculatesCorrectly() {
        PhoneBill bill = new PhoneBill("Name");
        bill.addPhoneCall(new PhoneCall("555-555-5555", "888-888-8888", "01/01/2022 08:00 AM", "01/01/2022 09:10 AM"));
        StringWriter writer = new StringWriter();
        PrettyPrinter printer = new PrettyPrinter(writer);

        printer.dump(bill);
        assertThat(writer.toString(), containsString("1 hour 10 minutes"));
    }

    @Test
    void durationWithMultipleHoursAndSingleMinuteDumpsCorrectly() {
        PhoneBill bill = new PhoneBill("Name");
        bill.addPhoneCall(new PhoneCall("555-555-5555", "888-888-8888", "01/01/2022 08:00 AM", "01/01/2022 10:01 AM"));
        StringWriter writer = new StringWriter();
        PrettyPrinter printer = new PrettyPrinter(writer);

        printer.dump(bill);
        assertThat(writer.toString(), containsString("2 hours 1 minute"));
    }

    @Test
    void callDurationUnderOneMinuteDumpsCorrectly() {
        PhoneBill bill = new PhoneBill("Name");
        bill.addPhoneCall(new PhoneCall("555-555-5555", "888-888-8888", "01/01/2022 08:00 AM", "01/01/2022 08:00 AM"));
        StringWriter writer = new StringWriter();
        PrettyPrinter printer = new PrettyPrinter(writer);

        printer.dump(bill);
        assertThat(writer.toString(), containsString("0 minutes"));
    }
}
