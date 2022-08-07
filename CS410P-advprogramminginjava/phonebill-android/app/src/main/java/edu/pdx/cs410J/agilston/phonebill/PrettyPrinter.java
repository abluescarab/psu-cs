package edu.pdx.cs410J.agilston.phonebill;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.pdx.cs410J.PhoneBillDumper;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void dump(PhoneBill bill) {
        try(PrintWriter pw = new PrintWriter(this.writer)) {
            StringBuilder line;
            List<PhoneCall> calls = new ArrayList<>(bill.getPhoneCalls());
            Collections.sort(calls);

            pw.println("Phone bill for " + bill.getCustomer() + ":" + System.lineSeparator());
            pw.println("CALLER       | CALLEE       | BEGIN TIME        | END TIME          | DURATION");

            for(PhoneCall call : calls) {
                line = new StringBuilder(call.getCaller());

                line.append(" | ")
                    .append(call.getCallee())
                    .append(" | ")
                    .append(call.getBeginTimeString())
                    .append(" | ")
                    .append(call.getEndTimeString())
                    .append(" | ")
                    .append(call.getDurationString());

                pw.println(line);
            }

            pw.flush();
        }
    }
}
