package edu.pdx.cs410J.agilston;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class PhoneBillServletTest {
    private static StringWriter addBill(PhoneBillServlet servlet, String customer, PhoneCall call) throws IOException {
        PhoneBill bill = new PhoneBill(customer);

        bill.addPhoneCall(call);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(PhoneBillServlet.CUSTOMER_PARAMETER)).thenReturn(customer);
        when(request.getParameter(PhoneBillServlet.CALL_PARAMETER)).thenReturn(Project4.formatCall(call));

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter writer = setWriter(response);

        servlet.doPost(request, response);
        return writer;
    }

    private static StringWriter setWriter(HttpServletResponse response) throws IOException {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer, true);

        when(response.getWriter()).thenReturn(pw);
        return writer;
    }

    @Test
    void initiallyServletContainsNoDictionaryEntries() throws IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter pw = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        // Nothing is written to the response's PrintWriter
        verify(pw, never()).println(anyString());
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void addBillToDictionary() throws IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();
        String customer = "Customer";
        PhoneBill bill = new PhoneBill(customer);
        PhoneCall call = new PhoneCall("555-555-5555", "888-888-8888", "01/01/2022 12:00 PM", "01/01/2022 1:00 PM");

        bill.addPhoneCall(call);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(PhoneBillServlet.CUSTOMER_PARAMETER)).thenReturn(customer);
        when(request.getParameter(PhoneBillServlet.CALL_PARAMETER)).thenReturn(Project4.formatCall(call));

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer, true);

        when(response.getWriter()).thenReturn(pw);
        servlet.doPost(request, response);
        assertThat(writer.toString(), containsString(Messages.addedCustomerPhoneCall(customer, call)));

        ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(statusCode.capture());
        assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));
        assertThat(servlet.getBill(customer), equalTo(bill));
    }

    @Test
    void findCallsBetweenTimes() throws IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        addBill(servlet, "Customer 1", new PhoneCall("555-555-5555", "888-888-8888", "01/01/2022 01:00 PM",
                "01/01/2022 02:00 PM"));
        addBill(servlet, "Customer 2", new PhoneCall("888-888-8888", "111-111-1111",
                "01/01/2022 12:00 PM", "01/01/2022 01:00 PM"));

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(PhoneBillServlet.CUSTOMER_PARAMETER)).thenReturn("Customer 2");
        when(request.getParameter(PhoneBillServlet.BEGIN_PARAMETER)).thenReturn("01/01/2022 11:00 AM");
        when(request.getParameter(PhoneBillServlet.END_PARAMETER)).thenReturn("01/01/2022 02:00 PM");

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter writer = setWriter(response);

        servlet.doGet(request, response);
        assertThat(writer.toString(), containsString("Customer 2"));

        ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(statusCode.capture());
        assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));
    }
}
