package edu.pdx.cs410J.agilston.phonebill;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A class that parses a phone bill from a reader.
 */
public class TextParser implements PhoneBillParser<PhoneBill> {
    private final Reader reader;

    public TextParser(Reader reader) {
        this.reader = reader;
    }

    /**
     * Parses a phone bill from a specified reader.
     *
     * @return parsed phone bill
     * @throws ParserException if customer is missing from file
     */
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
        catch(IndexOutOfBoundsException e) {
            throw new ParserException("Phone bill file cannot be parsed", e);
        }
        catch(IOException e) {
            throw new ParserException("Phone bill file cannot be read", e);
        }
    }
}
