package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class used to store a phone call with the caller's and receiver's numbers and call length.
 */
public class PhoneCall extends AbstractPhoneCall {
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
        this.formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
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
        Pattern pattern = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4}) (\\d{1,2}):(\\d{1,2})");
        Matcher matcher = pattern.matcher(dateTime);

        if(matcher.matches()) {
            String month = matcher.group(1);
            String day = matcher.group(2);
            String year = matcher.group(3);
            String hour = matcher.group(4);
            String minute = matcher.group(5);

            if(month.length() == 1)
                month = "0" + month;

            if(day.length() == 1)
                day = "0" + day;

            if(hour.length() == 1)
                hour = "0" + hour;

            if(minute.length() == 1)
                minute = "0" + minute;

            dateTime = String.format("%s/%s/%s %s:%s", month, day, year, hour, minute);
        }

        return LocalDateTime.parse(dateTime, formatter);
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
        return formatter.format(beginTime);
    }

    /**
     * Gets the call's end time in mm/dd/yyyy hh:mm format.
     */
    @Override
    public String getEndTimeString() {
        return formatter.format(endTime);
    }
}
