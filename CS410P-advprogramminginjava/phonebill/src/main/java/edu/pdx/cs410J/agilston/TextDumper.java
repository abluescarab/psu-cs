package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * A class that dumps a phone bill to a writer.
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {
    private final Writer writer;

    public TextDumper(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void dump(PhoneBill bill) {
        try(PrintWriter pw = new PrintWriter(this.writer)) {
            pw.println(bill.getCustomer());

            for(PhoneCall call : bill.getPhoneCalls()) {
                pw.println(String.format("%s,%s,%s,%s", call.getCaller(), call.getCallee(), call.getBeginTimeString(),
                        call.getEndTimeString()));
            }

            pw.flush();
        }
    }
}
