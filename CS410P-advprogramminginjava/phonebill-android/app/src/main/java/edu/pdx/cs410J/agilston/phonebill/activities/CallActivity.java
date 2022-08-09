package edu.pdx.cs410J.agilston.phonebill.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

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
    private static final String DATE_PATTERN = "MM/dd/yyyy";
    private static final String TIME_PATTERN = "hh:mm a";
    private EditText editCallerNumber;
    private EditText editCalleeNumber;
    private EditText editStartDate;
    private EditText editStartTime;
    private EditText editEndDate;
    private EditText editEndTime;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

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
            if(validate()) {
                if(TextUtils.equals(action, Extras.ACTION_ADD_CALL)) {
                    Intent intent = new Intent();
                    intent.putExtra(Extras.ACTION_CUSTOMER, getIntent().getStringExtra(Extras.ACTION_CUSTOMER));
                    intent.putExtra(Extras.RESULT_CALLER, editCallerNumber.getText().toString());
                    intent.putExtra(Extras.RESULT_CALLEE, editCalleeNumber.getText().toString());
                    intent.putExtra(Extras.RESULT_START_DATE, editStartDate.getText().toString());
                    intent.putExtra(Extras.RESULT_START_TIME, editStartTime.getText().toString());
                    intent.putExtra(Extras.RESULT_END_DATE, editEndDate.getText().toString());
                    intent.putExtra(Extras.RESULT_END_TIME, editEndTime.getText().toString());

                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                // TODO: search calls
                else {

                }
            }
        });
    }

    private String formatDate(int month, int day, int year) {
        String date = String.format("%s/%s/%s", month, day, year);
        DateTimeFormatter from = getFormatter("M/d/yyyy");
        DateTimeFormatter to = getFormatter(DATE_PATTERN);
        TemporalAccessor parsed = from.parse(date);
        return to.format(parsed);
    }

    private String formatDate(String date) {
        DateTimeFormatter from = getFormatter("MMM d, yyyy");
        DateTimeFormatter to = getFormatter(DATE_PATTERN);
        TemporalAccessor parsed = from.parse(date);
        return to.format(parsed);
    }

    private String formatTime(int hour, int minute) {
        DateTimeFormatter from = getFormatter("H:m");
        DateTimeFormatter to = getFormatter(TIME_PATTERN);
        TemporalAccessor parsed = from.parse(String.format("%s:%s", hour, minute));
        return to.format(parsed);
    }

    private DateTimeFormatter getFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    }

    private boolean validate() {
        boolean failed = isEmpty(editCalleeNumber);

        if(isEmpty(editCallerNumber)) {
            failed = true;
        }

        if(isEmpty(editStartDate)) {
            failed = true;
        }

        if(isEmpty(editStartTime)) {
            failed = true;
        }

        if(isEmpty(editEndDate)) {
            failed = true;
        }

        if(isEmpty(editEndTime)) {
            failed = true;
        }

        if(failed) {
            Toast.makeText(this, R.string.error_missing_data, Toast.LENGTH_LONG).show();
            return false;
        }

        if(isWrongLength(editCallerNumber)) {
            failed = true;
        }

        if(isWrongLength(editCalleeNumber)) {
            failed = true;
        }

        if(failed) {
            Toast.makeText(this, R.string.error_invalid_phone_number, Toast.LENGTH_LONG).show();
            return false;
        }

        DateTimeFormatter formatter = getFormatter(String.format("%s %s", DATE_PATTERN, TIME_PATTERN));
        ZonedDateTime startZdt = ZonedDateTime.parse(String.format("%s %s", editStartDate.getText(),
                editStartTime.getText()), formatter);
        ZonedDateTime endZdt = ZonedDateTime.parse(String.format("%s %s", editEndDate.getText(),
                editEndTime.getText()), formatter);

        if(startZdt.isAfter(endZdt)) {
            editStartDate.setError(getText(R.string.error_start_after_end));
            editStartTime.setError(getText(R.string.error_start_after_end));
            editEndDate.setError(getText(R.string.error_start_after_end));
            editEndTime.setError(getText(R.string.error_start_after_end));
            Toast.makeText(this, R.string.error_start_after_end, Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            editStartDate.setError(null);
            editStartTime.setError(null);
            editEndDate.setError(null);
            editEndTime.setError(null);
        }

        return true;
    }

    private boolean isEmpty(EditText editText) {
        boolean isEmpty = TextUtils.isEmpty(editText.getText());
        editText.setError(isEmpty ? getText(R.string.required) : null);
        return isEmpty;
    }

    private boolean isWrongLength(EditText editText) {
        boolean wrongLength = editText.length() != getResources().getInteger(R.integer.phone_number_input_length);
        editText.setError(wrongLength ? getText(R.string.error_invalid_phone_number) : null);
        return wrongLength;
    }

    public static class Extras {
        public static final String ACTION = "ACTION";
        public static final String ACTION_ADD_CALL = "ADD_CALL";
        public static final String ACTION_SEARCH_CALLS = "SEARCH_CALLS";
        public static final String ACTION_CUSTOMER = "CUSTOMER";

        public static final String RESULT_CALLER = "CALLER";
        public static final String RESULT_CALLEE = "CALLEE";
        public static final String RESULT_START_DATE = "START_DATE";
        public static final String RESULT_START_TIME = "START_TIME";
        public static final String RESULT_END_DATE = "END_DATE";
        public static final String RESULT_END_TIME = "END_TIME";
    }
}
