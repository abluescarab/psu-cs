package edu.pdx.cs410J.agilston.phonebill;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PhoneBillList {
    private static final Map<String, PhoneBill> bills = new TreeMap<>();
    private static String selectedCustomer;

    public static void addBill(String customer) {
        bills.put(customer, new PhoneBill(customer));
    }

    public static void addBill(String customer, PhoneBill bill) {
        bills.put(customer, bill);
    }

    public static void addCall(String customer, PhoneCall call) {
        PhoneBill bill;

        if(bills.containsKey(customer)) {
            bill = bills.get(customer);
        }
        else {
            bill = new PhoneBill(customer);
        }

        bill.addPhoneCall(call);
    }

    public static PhoneBill getBill(String customer) {
        return bills.get(customer);
    }

    public static PhoneBill getSelectedBill() {
        return bills.get(selectedCustomer);
    }

    public static Collection<PhoneBill> getAllBills() {
        return bills.values();
    }

    public static List<String> getCustomers() {
        return Arrays.asList(bills.keySet().toArray(new String[0]));
    }

    public static String getSelectedCustomer() {
        return selectedCustomer;
    }

    public static void setSelectedCustomer(String selectedCustomer) {
        PhoneBillList.selectedCustomer = selectedCustomer;
    }
}
