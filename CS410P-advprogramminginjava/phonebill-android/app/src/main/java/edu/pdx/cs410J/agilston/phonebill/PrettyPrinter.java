package edu.pdx.cs410J.agilston.phonebill;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.PrintWriter;
import java.io.Writer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Dump all phone bills.
     *
     * @param bills bills to dump
     */
    public void dump(Map<String, PhoneBill> bills) {
        for(PhoneBill bill : bills.values()) {
            dump(bill);
        }
    }

    @Override
    public void dump(PhoneBill bill) {
        try(PrintWriter pw = new PrintWriter(this.writer)) {
            StringBuilder line;
            Duration duration;
            long hours;
            long minutes;

            if(bill == null) {
                pw.println("No calls found.");
            }
            else {
                List<PhoneCall> calls = new ArrayList<>(bill.getPhoneCalls());

                if(calls.size() == 0) {
                    pw.println(String.format("There are no phone calls associated with %s.", bill.getCustomer()));
                }
                else {
                    Collections.sort(calls);

                    pw.println("Phone bill for " + bill.getCustomer() + ":" + System.lineSeparator());
                    pw.println("CALLER       | CALLEE       | BEGIN TIME        | END TIME          | DURATION");

                    for(PhoneCall call : calls) {
                        duration = Duration.ofMinutes(call.getDuration());
                        hours = duration.toHours();
                        minutes = duration.toMinutesPart();
                        line = new StringBuilder(call.getCaller());

                        line.append(" | ")
                            .append(call.getCallee())
                            .append(" | ")
                            .append(call.getBeginTimeString())
                            .append(" | ")
                            .append(call.getEndTimeString())
                            .append(" | ");

                        if(hours > 0) {
                            line.append(hours)
                                .append(" ")
                                .append(hours == 1 ? "hour" : "hours")
                                .append(" ");
                        }

                        line.append(minutes)
                            .append(" ")
                            .append(minutes == 1 ? "minute" : "minutes");

                        pw.println(line);
                    }
                }
            }

            pw.flush();
        }
    }
}
