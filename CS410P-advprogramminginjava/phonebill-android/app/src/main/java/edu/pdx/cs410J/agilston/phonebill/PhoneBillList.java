package edu.pdx.cs410J.agilston.phonebill;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class PhoneBillList {
    private static final SortedMap<String, PhoneBill> bills = new TreeMap<>();

    public static int addBill(String customer) {
        bills.put(customer, new PhoneBill(customer));
        return bills.headMap(customer).size();
    }

    public static int addBill(PhoneBill bill) {
        bills.put(bill.getCustomer(), bill);
        return bills.headMap(bill.getCustomer()).size();
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

    public static Collection<PhoneBill> getAllBills() {
        return bills.values();
    }

    public static List<String> getCustomers() {
        return Arrays.asList(bills.keySet().toArray(new String[0]));
    }

    public static int indexOf(String customer) {
        return bills.headMap(customer).size();
    }
}
