package edu.pdx.cs410J.agilston.phonebill;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import edu.pdx.cs410J.agilston.phonebill.adapters.CallAdapter;
import edu.pdx.cs410J.agilston.phonebill.adapters.CustomerAdapter;

public class PhoneBillList {
    private static final SortedMap<String, PhoneBill> bills = new TreeMap<>();
    private static CustomerAdapter customerAdapter;
    private static CallAdapter callAdapter;

    public static void addBill(String customer) {
        bills.put(customer, new PhoneBill(customer));

        if(customerAdapter != null) {
            customerAdapter.addCustomer(customer);
        }
    }

    public static void addBill(PhoneBill bill) {
        bills.put(bill.getCustomer(), bill);

        if(customerAdapter != null) {
            customerAdapter.addCustomer(bill.getCustomer());
        }
    }

    public static void addCall(String customer, PhoneCall call) {
        PhoneBill bill = bills.containsKey(customer) ? bills.get(customer) : new PhoneBill(customer);
        bill.addPhoneCall(call);

        if(callAdapter != null) {
            callAdapter.addCall(call);
        }
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

    public static CustomerAdapter getCustomerAdapter() {
        return customerAdapter;
    }

    public static void setCustomerAdapter(CustomerAdapter customerAdapter) {
        PhoneBillList.customerAdapter = customerAdapter;
    }

    public static CallAdapter getCallAdapter() {
        return callAdapter;
    }

    public static void setCallAdapter(CallAdapter callAdapter) {
        PhoneBillList.callAdapter = callAdapter;
    }

    public static int getCustomerCount() {
        return bills.keySet().size();
    }

    public static int getCallCount(String customer) {
        if(bills.containsKey(customer)) {
            PhoneBill bill = bills.get(customer);

            if(bill != null) {
                return bill.getPhoneCalls().size();
            }
        }

        return 0;
    }
}
