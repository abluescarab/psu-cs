package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class used to store a phone call with the caller's and receiver's numbers and call length.
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {
    public static final String DATE_TIME_REGEX = "(\\d{1,2})/(\\d{1,2})/(\\d{4}) (\\d{1,2}):(\\d{1,2}) ([APap][Mm])";

    protected final String caller;
    protected final String callee;
    protected final ZonedDateTime beginTime;
    protected final ZonedDateTime endTime;
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
        this.formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a").withZone(ZoneId.systemDefault());
        this.caller = callerNumber;
        this.callee = calleeNumber;
        this.beginTime = formatDateTime(beginTime);
        this.endTime = formatDateTime(endTime);
    }

    /**
     * Formats a date time string, then parses it.
     *
     * @param dateTime date time string to format
     * @return parsed date time string as {@link ZonedDateTime}
     */
    private ZonedDateTime formatDateTime(String dateTime) {
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
            return ZonedDateTime.parse(dateTime, formatter);
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
     * Gets the begin time.
     */
    @Override
    public Date getBeginTime() {
        return Date.from(beginTime.toInstant());
    }

    /**
     * Gets the end time.
     */
    @Override
    public Date getEndTime() {
        return Date.from(endTime.toInstant());
    }

    /**
     * Gets the call's begin time in mm/dd/yyyy hh:mm format.
     */
    @Override
    public String getBeginTimeString() {
        // TODO: change to yy instead of yyyy
        return formatter.format(beginTime).replace("AM", "am").replace("PM", "pm");
    }

    /**
     * Gets the call's end time in mm/dd/yyyy hh:mm format.
     */
    @Override
    public String getEndTimeString() {
        // TODO: change to yy instead of yyyy
        return formatter.format(endTime).replace("AM", "am").replace("PM", "pm");
    }

    /**
     * Gets the duration of the call in minutes.
     */
    public long getDuration() {
        return ChronoUnit.MINUTES.between(beginTime, endTime);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(PhoneCall o) {
        int timeCompare = beginTime.compareTo(o.beginTime);

        if(timeCompare != 0) {
            return timeCompare;
        }

        return caller.compareTo(o.caller);
    }
}
