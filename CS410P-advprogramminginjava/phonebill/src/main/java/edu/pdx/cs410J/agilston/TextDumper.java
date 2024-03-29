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

    /**
     * Dumps a phone bill to a specified writer.
     * @param bill bill to dump
     */
    @Override
    public void dump(PhoneBill bill) {
        try(PrintWriter pw = new PrintWriter(this.writer)) {
            String beginTime;
            String endTime;

            pw.println(bill.getCustomer());

            for(PhoneCall call : bill.getPhoneCalls()) {
                beginTime = PhoneCall.DATE_TIME_FORMATTER.format(call.getBeginTime().toInstant());
                endTime = PhoneCall.DATE_TIME_FORMATTER.format(call.getEndTime().toInstant());

                pw.println(String.format("%s,%s,%s,%s", call.getCaller(), call.getCallee(), beginTime, endTime));
            }

            pw.flush();
        }
    }
}
