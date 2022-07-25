package edu.pdx.cs410J.agilston;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.
 */
public class PhoneBillServlet extends HttpServlet {
    static final String CUSTOMER_PARAMETER = "customer";
    static final String CALL_PARAMETER = "call";
    static final String BEGIN_PARAMETER = "begin";
    static final String END_PARAMETER = "end";

    private final Map<String, PhoneBill> bills = new TreeMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the bill of the
     * customer specified in the "customer" HTTP parameter to the HTTP response.  If the
     * "customer" parameter is not specified, all bills in the dictionary
     * are written to the HTTP response.
     *
     * @param request  existing {@link HttpServletRequest}
     * @param response existing {@link HttpServletResponse}
     * @throws IOException if any write method fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String customer = getParameter(CUSTOMER_PARAMETER, request);

        if(customer == null) {
            writeAllBills(response);
            return;
        }

        String begin = getParameter(BEGIN_PARAMETER, request);
        String end = getParameter(END_PARAMETER, request);

        // ensure begin and end are always supplied together
        if(begin == null && end == null) {
            writeBill(customer, response);
        }
        else if((begin == null) == (end == null)) {
            writeCallsBetween(customer, begin, end, response);
        }
        else {
            missingRequiredParameter(response, begin == null ? BEGIN_PARAMETER : END_PARAMETER);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "customer" and "bill" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     *
     * @param request  existing {@link HttpServletRequest}
     * @param response existing {@link HttpServletResponse}
     * @throws IOException if {@link #missingRequiredParameter(HttpServletResponse, String)} or
     *                     {@link HttpServletResponse#getWriter()} fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String customer = getParameter(CUSTOMER_PARAMETER, request);

        if(customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }

        String callString = getParameter(CALL_PARAMETER, request);

        if(callString == null) {
            missingRequiredParameter(response, CALL_PARAMETER);
            return;
        }

        PhoneBill bill;

        if(bills.containsKey(customer)) {
            bill = bills.get(customer);
        }
        else {
            bill = new PhoneBill(customer);
        }

        PhoneCall call = Project4.parseCall(callString);
        bill.addPhoneCall(call);
        bills.put(customer, bill);

        PrintWriter pw = response.getWriter();

        pw.println(Messages.addedCustomerPhoneCall(customer, call) + " from " + callString);
        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     *
     * @param request  existing {@link HttpServletRequest}
     * @param response existing {@link HttpServletResponse}
     * @throws IOException if {@link HttpServletResponse#getWriter()} fails
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        this.bills.clear();

        PrintWriter pw = response.getWriter();

        pw.println(Messages.allBillsDeleted());
        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * <p>
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     *
     * @param response      existing {@link HttpServletResponse}
     * @param parameterName parameter name that is missing
     */
    private void missingRequiredParameter(HttpServletResponse response, String parameterName) throws IOException {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes all calls for a customer between the begin and end times.
     *
     * @param customer customer name
     * @param begin    begin time
     * @param end      end time
     * @param response existing HTTP response
     * @throws IOException if {@link HttpServletResponse#getWriter()} fails
     */
    private void writeCallsBetween(String customer, String begin, String end, HttpServletResponse response) throws IOException {
        PhoneBill bill = bills.get(customer);

        if(bill == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        PrintWriter writer = response.getWriter();
        Map<String, PhoneBill> calls = Map.of(customer, bill.filter(begin, end));
        TextDumper dumper = new TextDumper(writer);

        dumper.dump(calls);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes the bill for the given customer to the HTTP response.
     * <p>
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeBill(String customer, HttpServletResponse response) throws IOException {
        PhoneBill bill = bills.get(customer);

        if(bill == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        PrintWriter pw = response.getWriter();
        Map<String, PhoneBill> customerBill = Map.of(customer, bill);
        TextDumper dumper = new TextDumper(pw);

        dumper.dump(customerBill);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes all bills to the HTTP response.
     * <p>
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeAllBills(HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        TextDumper dumper = new TextDumper(pw);

        dumper.dump(bills);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     * <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        return (value == null || Objects.equals(value, "")) ? null : value;
    }
}
