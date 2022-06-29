package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class that maintains a phone bill with all its relevant phone calls.
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private final String customer;
    private final List<PhoneCall> calls;

    /**
     * Creates a new phone bill.
     *
     * @param customer customer name
     */
    public PhoneBill(String customer) {
        this.customer = customer;
        this.calls = new ArrayList<>();
    }

    /**
     * Gets customer's name.
     */
    @Override
    public String getCustomer() {
        return this.customer;
    }

    /**
     * Adds a phone call to the bill.
     *
     * @param call phone call to add
     */
    @Override
    public void addPhoneCall(PhoneCall call) {
        calls.add(call);
    }

    /**
     * Gets all phone calls in the bill.
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return calls;
    }
}
