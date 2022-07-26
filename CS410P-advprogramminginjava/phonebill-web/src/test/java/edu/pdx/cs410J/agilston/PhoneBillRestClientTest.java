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
    @Test
    void getAllBillsPerformsHttpGetWithNoParameters() throws ParserException, IOException {
        Map<String, PhoneBill> bills = Map.of("Customer 1", new PhoneBill("Customer 1"), "Customer 2",
                new PhoneBill("Customer 2"));
        HttpRequestHelper http = mock(HttpRequestHelper.class);

        when(http.get(eq(Map.of()))).thenReturn(dictionaryAsText(bills));

        PhoneBillRestClient client = new PhoneBillRestClient(http);
        Map<String, PhoneBill> clientBills = client.getAllPhoneBills();

        assertThat(clientBills, equalTo(bills));
    }

    private HttpRequestHelper.Response dictionaryAsText(Map<String, PhoneBill> dictionary) {
        StringWriter writer = new StringWriter();
        new TextDumper(writer).dump(dictionary);

        return new HttpRequestHelper.Response(writer.toString());
    }
}
