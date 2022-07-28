package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@TestMethodOrder(MethodName.class)
class PhoneBillRestClientIT {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    private PhoneBillRestClient newPhoneBillRestClient() {
        int port = Integer.parseInt(PORT);
        return new PhoneBillRestClient(HOSTNAME, port);
    }

    @Test
    void test0RemoveAllDictionaryEntries() throws IOException {
        PhoneBillRestClient client = newPhoneBillRestClient();
        client.removeAllPhoneBills();
    }

    @Test
    void test1EmptyServerContainsNoCalls() throws IOException, ParserException {
        PhoneBillRestClient client = newPhoneBillRestClient();
        Map<String, PhoneBill> bills = client.getAllPhoneBills();

        assertThat(bills.size(), equalTo(0));
    }
}
