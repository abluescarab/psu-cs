package edu.pdx.cs410J.agilston.phonebill.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

import edu.pdx.cs410J.agilston.phonebill.R;

public class CallActivity extends AppCompatActivity {
    public static class Extras {
        public static final String ACTION = "ACTION";
        public static final String ACTION_ADD_CALL = "ADD_CALL";
        public static final String ACTION_SEARCH_CALLS = "SEARCH_CALLS";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.call_fab);
        String action = getIntent().getStringExtra(Extras.ACTION);

        if(Objects.equals(action, Extras.ACTION_ADD_CALL)) {
            setTitle(R.string.title_add_call);
            fab.setImageResource(R.drawable.ic_add_call);
        }
        else {
            setTitle(R.string.title_search_call);
            fab.setImageResource(R.drawable.ic_search);
        }

        // assign action to fab
        fab.setOnClickListener(view -> {
            // TODO: save/search call(s)
        });

        EditText callerNumber = findViewById(R.id.edit_caller_number);
        EditText calleeNumber = findViewById(R.id.edit_callee_number);
        EditText startDate = findViewById(R.id.edit_date_start);
        EditText startTime = findViewById(R.id.edit_time_start);
        EditText endDate = findViewById(R.id.edit_date_end);
        EditText endTime = findViewById(R.id.edit_time_end);
        ZonedDateTime dateTime = ZonedDateTime.now();

        // set the start date, end date, and end time to the current day and time
        startDate.setText(formatDate(dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getYear()));
        endDate.setText(formatDate(dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getYear()));
        endTime.setText(formatTime(dateTime.getHour(), dateTime.getMinute()));

        // create a date picker for the start date
        MaterialDatePicker<?> startDatePicker =
                MaterialDatePicker.Builder.datePicker()
                                          .setTitleText(getText(R.string.edit_start_date))
                                          .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                          .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                                          .build();

        startDate.setOnClickListener(view ->
                startDatePicker.show(getSupportFragmentManager(), "MATERIAL_START_DATE_PICKER"));
        startDatePicker.addOnPositiveButtonClickListener(selection ->
                startDate.setText(formatDate(startDatePicker.getHeaderText())));

        // create a time picker for the start time
        MaterialTimePicker startTimePicker =
                new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                        .setHour(dateTime.getHour())
                        .setMinute(dateTime.getMinute())
                        .build();

        startTime.setOnClickListener(view ->
                startTimePicker.show(getSupportFragmentManager(), "MATERIAL_START_TIME_PICKER"));
        startTimePicker.addOnPositiveButtonClickListener(selection ->
                startTime.setText(formatTime(startTimePicker.getHour(), startTimePicker.getMinute())));

        // create a date picker for the end date
        MaterialDatePicker<?> endDatePicker =
                MaterialDatePicker.Builder.datePicker()
                                          .setTitleText(getText(R.string.edit_end_date))
                                          .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                          .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                                          .build();

        endDate.setOnClickListener(view ->
                endDatePicker.show(getSupportFragmentManager(), "MATERIAL_END_DATE_PICKER"));
        endDatePicker.addOnPositiveButtonClickListener(selection ->
                endDate.setText(formatDate(endDatePicker.getHeaderText())));

        // create a time picker for the end time
        MaterialTimePicker endTimePicker =
                new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                        .setHour(dateTime.getHour())
                        .setMinute(dateTime.getMinute())
                        .build();

        endTime.setOnClickListener(view ->
                endTimePicker.show(getSupportFragmentManager(), "MATERIAL_END_TIME_PICKER"));
        endTimePicker.addOnPositiveButtonClickListener(selection ->
                endTime.setText(formatTime(endTimePicker.getHour(), endTimePicker.getMinute())));
    }

    private String formatDate(int month, int day, int year) {
        String date = String.format("%s/%s/%s", month, day, year);
        DateTimeFormatter from = getFormatter("M/d/yyyy");
        DateTimeFormatter to = getFormatter("MM/dd/yyyy");
        TemporalAccessor parsed = from.parse(date);
        return to.format(parsed);
    }

    private String formatDate(String date) {
        DateTimeFormatter from = getFormatter("MMM d, yyyy");
        DateTimeFormatter to = getFormatter("MM/dd/yyyy");
        TemporalAccessor parsed = from.parse(date);
        return to.format(parsed);
    }

    private String formatTime(int hour, int minute) {
        DateTimeFormatter from = getFormatter("H:mm");
        DateTimeFormatter to = getFormatter("hh:mm a");
        TemporalAccessor parsed = from.parse(String.format("%s:%s", hour, minute));
        return to.format(parsed);
    }

    private DateTimeFormatter getFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    }
}
