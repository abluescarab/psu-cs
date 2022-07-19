package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {

    @Test
    void validTextFileCanBeParsed() throws ParserException {
        InputStream resource = getClass().getResourceAsStream("valid-phonebill.txt");
        assertThat(resource, notNullValue());

        TextParser parser = new TextParser(new InputStreamReader(resource));
        PhoneBill bill = parser.parse();
        assertThat(bill.getCustomer(), equalTo("Test Phone Bill"));
    }

    @Test
    void invalidTextFileThrowsParserException() {
        InputStream resource = getClass().getResourceAsStream("empty-phonebill.txt");
        assertThat(resource, notNullValue());

        TextParser parser = new TextParser(new InputStreamReader(resource));
        assertThrows(ParserException.class, parser::parse);
    }

    @Test
    void garbageTextFileThrowsParserException(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "garbage-file.txt");

        try(PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("oanfoaubgob");
            writer.println("0gtunr08h");
            writer.println("aipogbn085h");
            writer.println("aoubg95782");
            writer.flush();
        }

        TextParser parser = new TextParser(new FileReader(file));
        assertThrows(ParserException.class, parser::parse);
    }
}
