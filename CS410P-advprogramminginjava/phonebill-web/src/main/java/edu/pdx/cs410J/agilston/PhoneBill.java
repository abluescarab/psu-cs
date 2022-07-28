package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

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

    /**
     * Returns a new PhoneBill containing only the calls between two times.
     *
     * @param beginString time call began
     * @param endString   time call ended
     * @return new PhoneBill with calls that fall between begin and end
     */
    public PhoneBill filter(String beginString, String endString) {
        PhoneBill bill = new PhoneBill(customer);
        ZonedDateTime begin = PhoneCall.formatDateTime(beginString);
        ZonedDateTime end = PhoneCall.formatDateTime(endString);

        for(PhoneCall call : calls) {
            Date beginDate = call.getBeginTime();
            ZonedDateTime beginInstant = beginDate.toInstant().atZone(ZoneId.systemDefault());

            if(begin.isAfter(beginInstant) || end.isBefore(beginInstant)) {
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

        if(!Objects.equals(other.customer, this.customer) || other.calls.size() != this.calls.size()) {
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
