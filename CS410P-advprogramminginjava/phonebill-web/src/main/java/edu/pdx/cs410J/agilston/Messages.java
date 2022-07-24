package edu.pdx.cs410J.agilston;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages {
    public static String missingRequiredParameter(String parameterName) {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String createdCustomerBill(String customer, String bill) {
        return String.format("Created %s bill as %s", customer, bill);
    }

    public static String addedCustomerPhoneCall(String customer, PhoneCall call) {
        return String.format("Added %s to %s", call, customer);
    }

    public static String allBillsDeleted() {
        return "All bills have been deleted";
    }
}
