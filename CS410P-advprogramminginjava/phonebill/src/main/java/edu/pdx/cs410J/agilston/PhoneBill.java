package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;

/**
 * A class that maintains a phone bill with all its relevant phone calls.
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private final String customer;

    /**
     * Creates a new phone bill.
     * @param customer customer name
     */
  public PhoneBill(String customer) {
    this.customer = customer;
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
     * @param call phone call to add
     */
  @Override
  public void addPhoneCall(PhoneCall call) {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

    /**
     * Gets all phone calls in the bill.
     */
  @Override
  public Collection<PhoneCall> getPhoneCalls() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }
}
