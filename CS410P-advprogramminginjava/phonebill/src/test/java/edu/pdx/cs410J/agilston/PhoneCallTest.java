package edu.pdx.cs410J.agilston;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link PhoneCall} class.
 * <p>
 * You'll need to update these unit tests as you build out your program.
 */
public class PhoneCallTest {
    static PhoneCall createPhoneCall() {
        return new PhoneCall("555-555-5555", "888-888-8888", "01/01/2022 12:00 am", "01/01/2022 01:00 am");
    }

    private static PhoneCall createPhoneCallWithCallerNumber(String callerNumber) {
        return new PhoneCall(callerNumber, "888-888-8888", "01/01/2022 12:00 am", "01/01/2022 01:00 am");
    }

    private static PhoneCall createPhoneCallWithCalleeNumber(String calleeNumber) {
        return new PhoneCall("555-555-5555", calleeNumber, "01/01/2022 12:00 am", "01/01/2022 01:00 am");
    }

    private static PhoneCall createPhoneCallWithBeginTime(String beginTime) {
        return new PhoneCall("555-555-5555", "888-888-8888", beginTime, "01/01/2022 01:00 am");
    }

    private static PhoneCall createPhoneCallWithEndTime(String endTime) {
        return new PhoneCall("555-555-5555", "888-888-8888", "01/01/2022 12:00 am", endTime);
    }

    @Test
    void canAcceptAnyNonWhitespaceCallerNumber() {
        assertThat(createPhoneCallWithCallerNumber("1").getCaller(), equalTo("1"));
    }

    @Test
    void canAcceptAnyNonWhitespaceCalleeNumber() {
        assertThat(createPhoneCallWithCalleeNumber("1").getCallee(), equalTo("1"));
    }

    @Test
    void invalidBeginTimeFails() {
        String time = "13/01/2022 00:00";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> createPhoneCallWithBeginTime(time));
    }

    @Test
    void invalidEndTimeFails() {
        String time = "11/35/2022 12:00";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> createPhoneCallWithEndTime(time));
    }

    @Test
    void parsesFullBeginTimeStringCorrectly() {
        assertThat(createPhoneCall().getBeginTimeString(), equalTo("01/01/2022 12:00 am"));
    }

    @Test
    void parsesFullEndTimeStringCorrectly() {
        assertThat(createPhoneCall().getEndTimeString(), equalTo("01/01/2022 01:00 am"));
    }

    @Test
    void parsesShortBeginTimeStringCorrectly() {
        assertThat(createPhoneCallWithBeginTime("1/1/2022 1:00 am").getBeginTimeString(), equalTo("01/01/2022 01:00 am"));
    }

    @Test
    void parsesShortEndTimeStringCorrectly() {
        assertThat(createPhoneCallWithEndTime("1/1/2022 1:00 am").getEndTimeString(), equalTo("01/01/2022 01:00 am"));
    }
}
