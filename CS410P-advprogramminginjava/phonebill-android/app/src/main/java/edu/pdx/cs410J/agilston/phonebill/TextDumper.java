package edu.pdx.cs410J.agilston;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class TextDumper {
    private final Writer writer;

    public TextDumper(Writer writer) {
        this.writer = writer;
    }

    public void dump(Map<String, PhoneBill> dictionary) {
        try(PrintWriter pw = new PrintWriter(this.writer)) {
            for(Map.Entry<String, PhoneBill> bill : dictionary.entrySet()) {
                pw.println(String.format("%s : %s", bill.getKey(), bill.getValue()));
            }

            pw.flush();
        }
    }
}
