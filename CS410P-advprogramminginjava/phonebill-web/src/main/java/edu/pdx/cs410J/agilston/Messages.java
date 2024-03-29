package edu.pdx.cs410J.agilston;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages {
    /**
     * Gets a message that the HTTP request is missing a required parameter.
     *
     * @param parameterName missing parameter name
     */
    public static String missingRequiredParameter(String parameterName) {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * Gets a message that the server has created a new customer bill.
     *
     * @param customer customer name
     * @param bill     new phone bill
     */
    public static String createdCustomerBill(String customer, String bill) {
        return String.format("Created %s bill as %s", customer, bill);
    }

    /**
     * Gets a message that the server has added a customer phone call.
     *
     * @param customer customer name
     * @param call     new phone call
     */
    public static String addedCustomerPhoneCall(String customer, PhoneCall call) {
        return String.format("Added %s to %s", call.toString().toLowerCase(), customer);
    }

    /**
     * Gets a message that the server has deleted all bills.
     */
    public static String allBillsDeleted() {
        return "All bills have been deleted";
    }
}
