package edu.pdx.cs410J.agilston.phonebill.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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
    private EditText editCallerNumber;
    private EditText editCalleeNumber;
    private EditText editStartDate;
    private EditText editStartTime;
    private EditText editEndDate;
    private EditText editEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.call_fab);
        String action = getIntent().getStringExtra(Extras.ACTION);

        if(TextUtils.equals(action, Extras.ACTION_ADD_CALL)) {
            setTitle(R.string.title_add_call);
            fab.setImageResource(R.drawable.ic_add_call);
        }
        else {
            setTitle(R.string.title_search_call);
            fab.setImageResource(R.drawable.ic_search);
        }

        editCallerNumber = findViewById(R.id.edit_caller_number);
        editCalleeNumber = findViewById(R.id.edit_callee_number);
        editStartDate = findViewById(R.id.edit_date_start);
        editStartTime = findViewById(R.id.edit_time_start);
        editEndDate = findViewById(R.id.edit_date_end);
        editEndTime = findViewById(R.id.edit_time_end);
        ZonedDateTime dateTime = ZonedDateTime.now();

        // set the start date, end date, and end time to the current day and time
        editStartDate.setText(formatDate(dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getYear()));
        editEndDate.setText(formatDate(dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getYear()));
        editEndTime.setText(formatTime(dateTime.getHour(), dateTime.getMinute()));

        // create a date picker for the start date
        MaterialDatePicker<?> startDatePicker =
                MaterialDatePicker.Builder.datePicker()
                                          .setTitleText(R.string.hint_start_date)
                                          .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                          .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                                          .build();

        editStartDate.setOnClickListener(view -> {
            if(!startDatePicker.isAdded()) {
                startDatePicker.show(getSupportFragmentManager(), "MATERIAL_START_DATE_PICKER");
            }
        });
        startDatePicker.addOnPositiveButtonClickListener(selection ->
                editStartDate.setText(formatDate(startDatePicker.getHeaderText())));

        // create a time picker for the start time
        MaterialTimePicker startTimePicker =
                new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                        .setHour(dateTime.getHour())
                        .setMinute(dateTime.getMinute())
                        .build();

        editStartTime.setOnClickListener(view -> {
            if(!startTimePicker.isAdded()) {
                startTimePicker.show(getSupportFragmentManager(), "MATERIAL_START_TIME_PICKER");
            }
        });
        startTimePicker.addOnPositiveButtonClickListener(selection ->
                editStartTime.setText(formatTime(startTimePicker.getHour(), startTimePicker.getMinute())));

        // create a date picker for the end date
        MaterialDatePicker<?> endDatePicker =
                MaterialDatePicker.Builder.datePicker()
                                          .setTitleText(R.string.hint_end_date)
                                          .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                          .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                                          .build();

        editEndDate.setOnClickListener(view -> {
            if(!endDatePicker.isAdded()) {
                endDatePicker.show(getSupportFragmentManager(), "MATERIAL_END_DATE_PICKER");
            }
        });
        endDatePicker.addOnPositiveButtonClickListener(selection ->
                editEndDate.setText(formatDate(endDatePicker.getHeaderText())));

        // create a time picker for the end time
        MaterialTimePicker endTimePicker =
                new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                        .setHour(dateTime.getHour())
                        .setMinute(dateTime.getMinute())
                        .build();

        editEndTime.setOnClickListener(view -> {
            if(!endTimePicker.isAdded()) {
                endTimePicker.show(getSupportFragmentManager(), "MATERIAL_END_TIME_PICKER");
            }
        });
        endTimePicker.addOnPositiveButtonClickListener(selection ->
                editEndTime.setText(formatTime(endTimePicker.getHour(), endTimePicker.getMinute())));

        // assign action to fab
        fab.setOnClickListener(view -> {
            // TODO: save/search call(s)
            if(TextUtils.equals(action, Extras.ACTION_ADD_CALL)) {
                Intent intent = new Intent();
                intent.putExtra(Extras.ACTION_CUSTOMER, getIntent().getStringExtra(Extras.ACTION_CUSTOMER));
                intent.putExtra(Extras.RESULT_CALLER, editCallerNumber.getText().toString());
                intent.putExtra(Extras.RESULT_CALLEE, editCalleeNumber.getText().toString());
                intent.putExtra(Extras.RESULT_START, String.format("%s %s",
                        editStartDate.getText().toString(), editStartTime.getText().toString()));
                intent.putExtra(Extras.RESULT_END, String.format("%s %s",
                        editEndDate.getText().toString(), editEndTime.getText().toString()));

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
            else {

            }
        });
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
        DateTimeFormatter from = getFormatter("H:m");
        DateTimeFormatter to = getFormatter("hh:mm a");
        TemporalAccessor parsed = from.parse(String.format("%s:%s", hour, minute));
        return to.format(parsed);
    }

    private DateTimeFormatter getFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    }

    public static class Extras {
        public static final String ACTION = "ACTION";
        public static final String ACTION_ADD_CALL = "ADD_CALL";
        public static final String ACTION_SEARCH_CALLS = "SEARCH_CALLS";
        public static final String ACTION_CUSTOMER = "CUSTOMER";

        public static final String RESULT_CALLER = "CALLER";
        public static final String RESULT_CALLEE = "CALLEE";
        public static final String RESULT_START = "START";
        public static final String RESULT_END = "END";
    }
}
