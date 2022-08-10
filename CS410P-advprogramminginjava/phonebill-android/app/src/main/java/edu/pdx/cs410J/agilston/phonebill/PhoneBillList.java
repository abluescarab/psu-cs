package edu.pdx.cs410J.agilston.phonebill;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import edu.pdx.cs410J.agilston.phonebill.adapters.CallAdapter;
import edu.pdx.cs410J.agilston.phonebill.adapters.CustomerAdapter;

public class PhoneBillList {
    private static final SortedMap<String, PhoneBill> bills = new TreeMap<>();
    private static CustomerAdapter customerAdapter;
    private static CallAdapter callAdapter;

    public static void addCustomer(Context context, String customer) {
        PhoneBill bill = new PhoneBill(customer);
        bills.put(customer, bill);

        if(customerAdapter != null) {
            customerAdapter.addCustomer(customer);
        }

        FileUtils.save(context, bill);
    }

    public static void addBills(Context context, Map<String, PhoneBill> bills, boolean saveAdded) {
        PhoneBillList.bills.putAll(bills);

        if(customerAdapter != null) {
            customerAdapter.addCustomers(bills.keySet().toArray(new String[0]));
        }

        if(saveAdded) {
            FileUtils.save(context, bills.values());
        }
    }

    public static void addCall(Context context, String customer, PhoneCall call) {
        PhoneBill bill = bills.containsKey(customer) ? bills.get(customer) : new PhoneBill(customer);

        if(bill != null) {
            bill.addPhoneCall(call);

            if(callAdapter != null) {
                callAdapter.addCall(call);
            }

            FileUtils.save(context, bill);
        }
    }

    public static PhoneBill getBill(String customer) {
        return bills.get(customer);
    }

    public static List<String> getCustomers() {
        return Arrays.asList(bills.keySet().toArray(new String[0]));
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

    public static int getCallCount(String customer) {
        if(bills.containsKey(customer)) {
            PhoneBill bill = bills.get(customer);

            if(bill != null) {
                return bill.getPhoneCalls().size();
            }
        }

        return 0;
    }

    public static boolean hasCustomer(String customer) {
        return bills.containsKey(customer);
    }
}
