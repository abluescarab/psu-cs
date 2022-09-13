package edu.pdx.cs410J.agilston;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static edu.pdx.cs410J.agilston.PhoneCallTest.*;
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

    @Test
    void testPhoneBillFilterToTime() {
        PhoneBill bill = createPhoneBill();
        PhoneCall call1 = createPhoneCall();
        PhoneCall call2 = createPhoneCallWithBeginTime("12/31/2021 11:00 pm");
        PhoneCall call3 = createPhoneCallWithEndTime("01/01/2022 02:00 am");

        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        bill.addPhoneCall(call3);

        assertThat(bill.filter("01/01/2022 12:00 am", "01/01/2022 1:00 am").getPhoneCalls().size(), equalTo(2));
    }

    @Test
    void phoneBillEqualsItself() {
        PhoneBill bill = createPhoneBill();
        PhoneBill copy = bill;

        assertThat(bill.equals(copy), equalTo(true));
    }

    @Test
    void phoneBillNotEqualToDifferentClass() {
        PhoneBill bill =  createPhoneBill();
        String[] call = new String[0];

        assertThat(bill.equals(call), equalTo(false));
    }

    @Test
    void phoneBillNotEqualIfDifferentSizedLists() {
        PhoneBill bill1 = createPhoneBill();
        PhoneBill bill2 = createPhoneBill();

        bill2.addPhoneCall(createPhoneCall());
        assertThat(bill1.equals(bill2), equalTo(false));
    }

    @Test
    void phoneBillsNotEqualIfDifferentCalls() {
        PhoneBill bill1 = createPhoneBill();
        PhoneBill bill2 = createPhoneBill();

        bill1.addPhoneCall(createPhoneCall());
        bill2.addPhoneCall(createPhoneCallWithCallerNumber("444-444-4444"));
        assertThat(bill1.equals(bill2), equalTo(false));
    }
}
