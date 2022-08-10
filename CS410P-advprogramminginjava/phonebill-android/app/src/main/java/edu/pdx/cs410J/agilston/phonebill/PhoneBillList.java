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

    /**
     * Adds a new customer.
     *
     * @param context  calling context
     * @param customer customer name
     */
    public static void addCustomer(Context context, String customer) {
        PhoneBill bill = new PhoneBill(customer);
        bills.put(customer, bill);

        if(customerAdapter != null) {
            customerAdapter.addCustomer(customer);
        }

        FileUtils.save(context, bill);
    }

    /**
     * Adds a list of bills.
     *
     * @param context   calling context
     * @param bills     bills to add
     * @param saveAdded whether to save all bills to the file system
     */
    public static void addBills(Context context, Map<String, PhoneBill> bills, boolean saveAdded) {
        PhoneBillList.bills.putAll(bills);

        if(customerAdapter != null) {
            customerAdapter.addCustomers(bills.keySet().toArray(new String[0]));
        }

        if(saveAdded) {
            FileUtils.save(context, bills.values());
        }
    }

    /**
     * Adds a new call.
     *
     * @param context  calling context
     * @param customer customer name
     * @param call     call to add
     */
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

    /**
     * Gets a bill by customer name.
     *
     * @param customer customer name
     */
    public static PhoneBill getBill(String customer) {
        return bills.get(customer);
    }

    /**
     * Gets a list of customers.
     */
    public static List<String> getCustomers() {
        return Arrays.asList(bills.keySet().toArray(new String[0]));
    }

    /**
     * Gets the app's {@link RecyclerView} customer adapter.
     */
    public static CustomerAdapter getCustomerAdapter() {
        return customerAdapter;
    }

    /**
     * Sets the app's {@link RecyclerView} customer adapter.
     *
     * @param customerAdapter adapter to set to
     */
    public static void setCustomerAdapter(CustomerAdapter customerAdapter) {
        PhoneBillList.customerAdapter = customerAdapter;
    }

    /**
     * Gets the app's {@link RecyclerView} call adapter.
     */
    public static CallAdapter getCallAdapter() {
        return callAdapter;
    }

    /**
     * Sets the app's {@link RecyclerView} call adapter.
     *
     * @param callAdapter adapter to set to
     */
    public static void setCallAdapter(CallAdapter callAdapter) {
        PhoneBillList.callAdapter = callAdapter;
    }

    /**
     * Gets the number of calls in a specified bill.
     *
     * @param customer customer name
     */
    public static int getCallCount(String customer) {
        if(bills.containsKey(customer)) {
            PhoneBill bill = bills.get(customer);

            if(bill != null) {
                return bill.getPhoneCalls().size();
            }
        }

        return 0;
    }

    /**
     * Checks whether the app has a customer.
     *
     * @param customer customer name
     * @return whether the customer exists
     */
    public static boolean hasCustomer(String customer) {
        return bills.containsKey(customer);
    }
}
