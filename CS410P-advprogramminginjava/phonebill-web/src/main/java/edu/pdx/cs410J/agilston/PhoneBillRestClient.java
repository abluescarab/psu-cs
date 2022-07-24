package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient {
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final HttpRequestHelper http;

    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     *
     * @param hostName The name of the host
     * @param port     The port
     */
    public PhoneBillRestClient(String hostName, int port) {
        this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

    @VisibleForTesting
    PhoneBillRestClient(HttpRequestHelper http) {
        this.http = http;
    }

    /**
     * Gets all phone bills from the server.
     */
    public Map<String, PhoneBill> getAllPhoneBills() throws IOException, ParserException {
        Response response = http.get(Map.of());
        TextParser parser = new TextParser(new StringReader(response.getContent()));

        return parser.parse();
    }

    /**
     * Returns the phone bill for the given customer.
     */
    public PhoneBill getPhoneBill(String customer) throws IOException, ParserException {
        Response response = http.get(Map.of("customer", customer));

        throwExceptionIfNotOkayHttpStatus(response);

        String content = response.getContent();
        TextParser parser = new TextParser(new StringReader(content));

        return parser.parse().get(customer);
    }

    /**
     * Add a phone call to a customer's bill.
     *
     * @param customer customer name
     * @param call     phone call to add
     * @throws IOException if post fails
     */
    public void addPhoneCall(String customer, PhoneCall call) throws IOException {
        Response response = http.post(Map.of("customer", customer, "call", Project4.formatCall(call)));
        throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * Removes all phone bills.
     *
     * @throws IOException if delete fails
     */
    public void removeAllPhoneBills() throws IOException {
        Response response = http.delete(Map.of());
        throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * Throws an exception if an HTTP request fails.
     *
     * @param response response to check status of
     */
    private void throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getHttpStatusCode();

        if(code != HTTP_OK) {
            String message = response.getContent();
            throw new RestException(code, message);
        }
    }
}
