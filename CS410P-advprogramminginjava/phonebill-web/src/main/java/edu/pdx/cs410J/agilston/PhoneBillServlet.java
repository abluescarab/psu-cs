package edu.pdx.cs410J.agilston;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet {
    static final String CUSTOMER_PARAMETER = "customer";
    static final String BILL_PARAMETER = "bill";

    private final Map<String, PhoneBill> bills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the bill of the
     * customer specified in the "customer" HTTP parameter to the HTTP response.  If the
     * "customer" parameter is not specified, all bills in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String customer = getParameter(CUSTOMER_PARAMETER, request);

        if(customer != null) {
            writeBill(customer, response);
        }
        else {
            writeAllBills(response);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "customer" and "bill" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String customer = getParameter(CUSTOMER_PARAMETER, request);

        if(customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }

        String billString = getParameter(BILL_PARAMETER, request);

        if(billString == null) {
            missingRequiredParameter(response, BILL_PARAMETER);
            return;
        }

        PhoneBill bill;

        if(bills.containsKey(customer)) {
            bill = bills.get(customer);
        }
        else {

            bill = new PhoneBill(customer);
        }

        String[] callInfo = billString.split(Project4.BILL_DELIMITER);

        if(callInfo.length == 4) {
            bill.addPhoneCall(new PhoneCall(callInfo[0], callInfo[1], callInfo[2], callInfo[3]));
        }

        this.bills.put(customer, bill);

        PrintWriter pw = response.getWriter();

        pw.println(Messages.createdCustomerBill(customer, billString));
        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
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
     */
    private void missingRequiredParameter(HttpServletResponse response, String parameterName)
            throws IOException {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the bill for the given customer to the HTTP response.
     * <p>
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeBill(String customer, HttpServletResponse response) throws IOException {
        PhoneBill bill = this.bills.get(customer);

        if(bill == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        else {
            PrintWriter pw = response.getWriter();
            Map<String, PhoneBill> customerBill = Map.of(customer, bill);
            TextDumper dumper = new TextDumper(pw);

            dumper.dump(customerBill);
            response.setStatus(HttpServletResponse.SC_OK);
        }
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

        if(value == null || "".equals(value)) {
            return null;
        }
        else {
            return value;
        }
    }

    /**
     * Gets the bill for a specified customer.
     *
     * @param customer customer name
     */
    @VisibleForTesting
    PhoneBill getBill(String customer) {
        return this.bills.get(customer);
    }
}
