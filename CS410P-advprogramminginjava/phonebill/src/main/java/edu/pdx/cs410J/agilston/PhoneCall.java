package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneCall extends AbstractPhoneCall {
    protected String caller;
    protected String callee;
    protected LocalDateTime beginTime;
    protected LocalDateTime endTime;
    private DateTimeFormatter formatter;

    public PhoneCall(String callerNumber, String calleeNumber, String beginTime, String endTime) {
        formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

        this.caller = callerNumber;
        this.callee = calleeNumber;
        this.beginTime = formatDateTime(beginTime);
        this.endTime = formatDateTime(endTime);
    }

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

    @Override
    public String getCaller() {
        return caller;
    }

    @Override
    public String getCallee() {
        return callee;
    }

    @Override
    public String getBeginTimeString() {
        return formatter.format(beginTime);
    }

    @Override
    public String getEndTimeString() {
        return formatter.format(endTime);
    }
}
