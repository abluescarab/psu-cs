package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneBillRestClientTest {
//    @Test
//    void getAllDictionaryEntriesPerformsHttpGetWithNoParameters() throws ParserException, IOException {
//        Map<String, PhoneBill> dictionary = Map.of("One", new PhoneBill("One"), "Two",
//                new PhoneBill("Two"));
//
//        HttpRequestHelper http = mock(HttpRequestHelper.class);
//        when(http.get(eq(Map.of()))).thenReturn(dictionaryAsText(dictionary));
//
//        PhoneBillRestClient client = new PhoneBillRestClient(http);
//
//        assertThat(client.getAllPhoneBills(), equalTo(dictionary));
//    }

    private HttpRequestHelper.Response dictionaryAsText(Map<String, PhoneBill> dictionary) {
        StringWriter writer = new StringWriter();
        new TextDumper(writer).dump(dictionary);

        return new HttpRequestHelper.Response(writer.toString());
    }
}
