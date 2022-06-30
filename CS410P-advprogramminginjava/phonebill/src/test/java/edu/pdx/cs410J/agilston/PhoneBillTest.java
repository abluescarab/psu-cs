package edu.pdx.cs410J.agilston;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static edu.pdx.cs410J.agilston.PhoneCallTest.createPhoneCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit tests for the {@link PhoneCall} class.
 * <p>
 * You'll need to update these unit tests as you build out your program.
 */
public class PhoneBillTest {
    private PhoneBill createPhoneBill() {
        return new PhoneBill("Name");
    }

    @Test
    void phoneCallIsInBill() {
        PhoneBill bill = createPhoneBill();
        PhoneCall call = createPhoneCall();

        bill.addPhoneCall(call);
        assertThat(bill.getPhoneCalls().stream().filter(c -> Objects.equals(c, call)).count(), equalTo(1L));
    }
}
