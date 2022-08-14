package edu.pdx.cs410J.agilston;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

public class TextDumper {
    private final Writer writer;

    public TextDumper(Writer writer) {
        this.writer = writer;
    }

    public void dump(Map<String, PhoneBill> dictionary) {
        try(PrintWriter pw = new PrintWriter(this.writer)) {
            for(Map.Entry<String, PhoneBill> bill : dictionary.entrySet()) {
                String customer = bill.getKey();
                Collection<PhoneCall> calls = bill.getValue().getPhoneCalls();

                if(calls.size() == 0) {
                    pw.println(String.format("%s : %s", customer, bill.getValue().toString()));
                }

                for(PhoneCall call : calls) {
                    String beginTime = PhoneCall.DATE_TIME_FORMATTER.format(call.getBeginTime().toInstant());
                    String endTime = PhoneCall.DATE_TIME_FORMATTER.format(call.getEndTime().toInstant());

                    pw.println(String.format("%s : %s,%s,%s,%s", customer, call.getCaller(), call.getCallee(),
                            beginTime, endTime));
                }
            }

            pw.flush();
        }
    }
}
