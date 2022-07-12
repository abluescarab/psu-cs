package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class used to store a phone call with the caller's and receiver's numbers and call length.
 */
public class PhoneCall extends AbstractPhoneCall {
    public static final String DATE_TIME_REGEX = "(\\d{1,2})/(\\d{1,2})/(\\d{4}) (\\d{1,2}):(\\d{1,2}) ([APap][Mm])";

    protected final String caller;
    protected final String callee;
    protected final LocalDateTime beginTime;
    protected final LocalDateTime endTime;
    private final DateTimeFormatter formatter;

    /**
     * Creates a new phone call.
     *
     * @param callerNumber caller's number (###-###-####)
     * @param calleeNumber receiver's number (###-###-####)
     * @param beginTime    date and time the call began (mm/dd/yyyy hh:mm)
     * @param endTime      date and time the call ended (mm/dd/yyyy hh:mm)
     */
    public PhoneCall(String callerNumber, String calleeNumber, String beginTime, String endTime) {
        this.formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        this.caller = callerNumber;
        this.callee = calleeNumber;
        this.beginTime = formatDateTime(beginTime);
        this.endTime = formatDateTime(endTime);
    }

    /**
     * Formats a date time string, then parses it.
     *
     * @param dateTime date time string to format
     * @return parsed date time string as {@link LocalDateTime}
     */
    private LocalDateTime formatDateTime(String dateTime) {
        Pattern pattern = Pattern.compile(DATE_TIME_REGEX);
        Matcher matcher = pattern.matcher(dateTime);

        if(matcher.matches()) {
            String month = matcher.group(1);
            String day = matcher.group(2);
            String year = matcher.group(3);
            String hour = matcher.group(4);
            String minute = matcher.group(5);
            String ampm = matcher.group(6).toUpperCase();

            if(month.length() == 1)
                month = "0" + month;

            if(day.length() == 1)
                day = "0" + day;

            if(hour.length() == 1)
                hour = "0" + hour;

            dateTime = String.format("%s/%s/%s %s:%s %s", month, day, year, hour, minute, ampm);
        }

        try {
            return LocalDateTime.parse(dateTime, formatter);
        }
        catch(DateTimeParseException e) {
            throw new IllegalArgumentException(String.format("Invalid argument: %s must be in format mm/dd/yyyy hh:mm "
                    + "am/pm or m/d/yyyy h:mm AM/PM", dateTime));
        }
    }

    /**
     * Gets the caller's number.
     */
    @Override
    public String getCaller() {
        return caller;
    }

    /**
     * Gets the callee's number.
     */
    @Override
    public String getCallee() {
        return callee;
    }

    /**
     * Gets the call's begin time in mm/dd/yyyy hh:mm format.
     */
    @Override
    public String getBeginTimeString() {
        return formatter.format(beginTime).replace("AM", "am").replace("PM", "pm");
    }

    /**
     * Gets the call's end time in mm/dd/yyyy hh:mm format.
     */
    @Override
    public String getEndTimeString() {
        return formatter.format(endTime).replace("AM", "am").replace("PM", "pm");
    }
}
