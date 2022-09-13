package edu.pdx.cs410J.agilston.phonebill;

import android.text.TextUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.pdx.cs410J.AbstractPhoneBill;

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
     * Creates a new phone bill with a list of calls.
     *
     * @param customer customer name
     * @param calls    calls to add
     */
    public PhoneBill(String customer, List<PhoneCall> calls) {
        this.customer = customer;
        this.calls = calls;
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

    /**
     * Returns a new PhoneBill containing only the calls between two times with the specified caller/callee numbers.
     *
     * @param caller      caller number
     * @param callee      callee number
     * @param beginString time call began
     * @param endString   time call ended
     * @return new PhoneBill with calls that fall between begin and end
     */
    public PhoneBill filter(String caller, String callee, String beginString, String endString) {
        PhoneBill bill = new PhoneBill(customer);
        ZonedDateTime begin = null;
        ZonedDateTime end = null;

        // trim any whitespace to ensure isEmpty works
        caller = caller.trim();
        callee = callee.trim();
        beginString = beginString.trim();
        endString = endString.trim();

        if(!TextUtils.isEmpty(beginString.trim())) {
            begin = PhoneCall.formatDateTime(beginString);
        }

        if(!TextUtils.isEmpty(endString.trim())) {
            end = PhoneCall.formatDateTime(endString);
        }

        for(PhoneCall call : calls) {
            if(!TextUtils.isEmpty(caller) && !TextUtils.equals(caller, call.getCaller()) ||
                    !TextUtils.isEmpty(callee) && !TextUtils.equals(callee, call.getCallee())) {
                continue;
            }

            Date beginDate = call.getBeginTime();
            ZonedDateTime beginInstant = beginDate.toInstant().atZone(ZoneId.systemDefault());

            if((begin != null && begin.isAfter(beginInstant)) || (end != null && end.isBefore(beginInstant))) {
                continue;
            }

            bill.addPhoneCall(call);
        }

        return bill;
    }

    /**
     * Returns whether this {@link PhoneBill} is equivalent to the provided {@link Object}.
     *
     * @param obj object to compare
     * @return whether the objects match
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!obj.getClass().equals(PhoneBill.class)) {
            return false;
        }

        PhoneBill other = (PhoneBill)obj;

        if(!TextUtils.equals(other.customer, this.customer) || other.calls.size() != this.calls.size()) {
            return false;
        }

        for(int i = 0; i < calls.size(); i++) {
            if(!calls.get(i).equals(other.calls.get(i))) {
                return false;
            }
        }

        return true;
    }
}
