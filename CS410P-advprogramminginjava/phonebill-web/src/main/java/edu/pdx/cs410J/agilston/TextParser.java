package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {
    private final Reader reader;

    public TextParser(Reader reader) {
        this.reader = reader;
    }

    public Map<String, PhoneBill> parse() throws ParserException {
        Pattern pattern = Pattern.compile("(.*?) : (.*)");
        Map<String, PhoneBill> bills = new TreeMap<>();

        try(BufferedReader br = new BufferedReader(this.reader)) {
            for(String line = br.readLine(); line != null; line = br.readLine()) {
                Matcher matcher = pattern.matcher(line);

                if(!matcher.find()) {
                    throw new ParserException(String.format("Unexpected text: \"%s\"", line));
                }

                String customer = matcher.group(1);
                String callString = matcher.group(2);
                PhoneBill bill;

                if(bills.containsKey(customer)) {
                    bill = bills.get(customer);
                }
                else {
                    bill = new PhoneBill(customer);
                    bills.put(customer, bill);
                }

                PhoneCall call = parseCall(callString);

                if(call != null) {
                    bill.addPhoneCall(call);
                }
            }
        }
        catch(IOException e) {
            throw new ParserException("While parsing dictionary", e);
        }

        return bills;
    }

    private PhoneCall parseCall(String callString) {
        String[] call = callString.split(",");

        if(call.length == 4) {
            return new PhoneCall(call[0], call[1], call[2], call[3]);
        }
        else {
            return null;
        }
    }
}
