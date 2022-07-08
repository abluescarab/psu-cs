package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class TextParser implements PhoneBillParser<PhoneBill> {
    private final Reader reader;

    public TextParser(Reader reader) {
        this.reader = reader;
    }

    @Override
    public PhoneBill parse() throws ParserException {
        try(BufferedReader br = new BufferedReader(this.reader)) {
            String customer = br.readLine();
            PhoneBill bill;

            if(customer == null) {
                throw new ParserException("Missing customer");
            }

            bill = new PhoneBill(customer);

            while(br.ready()) {
                String line = br.readLine();
                String[] call = line.split(",");

                bill.addPhoneCall(new PhoneCall(call[0], call[1], call[2], call[3]));
            }

            return bill;
        }
        catch(IOException e) {
            throw new ParserException("While parsing phone bill text", e);
        }
    }
}
