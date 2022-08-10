package edu.pdx.cs410J.agilston.phonebill.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

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

    /**
     * Checks whether an {@link EditText} is empty and sets its error message.
     *
     * @param editText     EditText to check
     * @param errorMessage error message to set to
     * @return whether the EditText is empty
     */
    private static boolean isEmpty(EditText editText, @StringRes int errorMessage) {
        boolean isEmpty = TextUtils.isEmpty(editText.getText());
        editText.setError(isEmpty ? editText.getContext().getText(errorMessage) : null);
        return isEmpty;
    }

    /**
     * Checks whether any one of a list of {@link EditText}s is empty and sets their error messages.
     *
     * @param errorMessage error message to set to
     * @param editTexts    EditTexts to check
     * @return whether any EditText is empty
     */
    private static boolean oneEmpty(@StringRes int errorMessage, EditText... editTexts) {
        boolean oneEmpty = false;

        for(EditText editText : editTexts) {
            if(isEmpty(editText, errorMessage)) {
                oneEmpty = true;
            }
        }

        return oneEmpty;
    }

    /**
     * Checks whether any {@link EditText} in a list has text.
     *
     * @param editTexts EditTexts to check
     * @return whether any EditText has text
     */
    private static boolean anyText(EditText... editTexts) {
        for(EditText editText : editTexts) {
            if(!TextUtils.isEmpty(editText.getText())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the {@link EditText} contains a valid phone number.
     *
     * @param editText EditText to check
     * @return whether the EditText has a valid phone number
     */
    private static boolean isValidPhoneNumber(EditText editText) {
        String text = editText.getText().toString();
        Resources resources = editText.getResources();
        int phoneNumberLength = resources.getInteger(R.integer.phone_number_input_length);
        Pattern pattern = Pattern.compile(String.format(Locale.getDefault(), "\\d{%d}", phoneNumberLength));
        boolean valid = pattern.matcher(editText.getText().toString()).matches();

        editText.setError(valid ? null : resources.getText(R.string.err_invalid_phone_number));
        return valid;
    }

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

        // set the start date, end date, and end time to the current day and time if adding call
        if(TextUtils.equals(action, Extras.ACTION_ADD_CALL)) {
            editStartDate.setText(formatDate(dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getYear()));
            editEndDate.setText(formatDate(dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getYear()));
            editEndTime.setText(formatTime(dateTime.getHour(), dateTime.getMinute()));
        }

        // add text changed listener that clears errors
        editStartDate.addTextChangedListener(new DateTimeTextWatcher(editStartDate));
        editStartTime.addTextChangedListener(new DateTimeTextWatcher(editStartTime));
        editEndDate.addTextChangedListener(new DateTimeTextWatcher(editEndDate));
        editEndTime.addTextChangedListener(new DateTimeTextWatcher(editEndTime));

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
            if(validate(action)) {
                Intent intent = new Intent();

                intent.putExtra(Extras.ACTION, action);
                intent.putExtra(Extras.RESULT_CUSTOMER, getIntent().getStringExtra(Extras.RESULT_CUSTOMER));
                intent.putExtra(Extras.RESULT_CALLER, editCallerNumber.getText().toString());
                intent.putExtra(Extras.RESULT_CALLEE, editCalleeNumber.getText().toString());
                intent.putExtra(Extras.RESULT_START_DATE, editStartDate.getText().toString());
                intent.putExtra(Extras.RESULT_START_TIME, editStartTime.getText().toString());
                intent.putExtra(Extras.RESULT_END_DATE, editEndDate.getText().toString());
                intent.putExtra(Extras.RESULT_END_TIME, editEndTime.getText().toString());

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * Formats a date for {@link EditText}s in the activity.
     *
     * @return formatted date string
     */
    private String formatDate(int month, int day, int year) {
        String date = String.format("%s/%s/%s", month, day, year);
        DateTimeFormatter from = getFormatter("M/d/yyyy");
        DateTimeFormatter to = getFormatter(DATE_PATTERN);
        TemporalAccessor parsed = from.parse(date);
        return to.format(parsed);
    }

    /**
     * Formats a date for {@link EditText}s in the activity.
     *
     * @param date date string from a {@link MaterialDatePicker} and {@link MaterialTimePicker}
     * @return formatted date string
     */
    private String formatDate(String date) {
        DateTimeFormatter from = getFormatter("MMM d, yyyy");
        DateTimeFormatter to = getFormatter(DATE_PATTERN);
        TemporalAccessor parsed = from.parse(date);
        return to.format(parsed);
    }

    /**
     * Formats a time for {@link EditText}s in the activity.
     *
     * @return formatted time string
     */
    private String formatTime(int hour, int minute) {
        DateTimeFormatter from = getFormatter("H:m");
        DateTimeFormatter to = getFormatter(TIME_PATTERN);
        TemporalAccessor parsed = from.parse(String.format("%s:%s", hour, minute));
        return to.format(parsed);
    }

    /**
     * Gets a {@link DateTimeFormatter} with the specified pattern and the system default timezone.
     *
     * @param pattern pattern to create with
     */
    private DateTimeFormatter getFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    }

    /**
     * Validates the activity's data before submitting.
     *
     * @param action action passed in the {@link Intent}
     * @return whether the data is valid
     */
    private boolean validate(String action) {
        EditText[] editTexts = {
                editCallerNumber,
                editCalleeNumber,
                editStartDate,
                editStartTime,
                editEndDate,
                editEndTime
        };
        EditText[] editTextDates = {
                editStartDate,
                editStartTime,
                editEndDate,
                editEndTime
        };

        boolean anyDate = anyText(editTextDates);

        if(anyDate && oneEmpty(R.string.err_all_date_fields_required, editTextDates)) {
            Toast.makeText(this, R.string.err_all_date_fields_required, Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.equals(action, Extras.ACTION_ADD_CALL)) {
            boolean valid = true;

            for(EditText editText : editTexts) {
                if(isEmpty(editText, R.string.required)) {
                    valid = false;
                }
            }

            if(!valid) {
                Toast.makeText(this, R.string.err_missing_data, Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if((editCallerNumber.length() > 0 && !isValidPhoneNumber(editCallerNumber))
                || (editCalleeNumber.length() > 0 && !isValidPhoneNumber(editCalleeNumber))) {
            return false;
        }

        if(anyDate) {
            DateTimeFormatter formatter = getFormatter(String.format("%s %s", DATE_PATTERN, TIME_PATTERN));
            ZonedDateTime startZdt = ZonedDateTime.parse(String.format("%s %s", editStartDate.getText(),
                    editStartTime.getText()), formatter);
            ZonedDateTime endZdt = ZonedDateTime.parse(String.format("%s %s", editEndDate.getText(),
                    editEndTime.getText()), formatter);

            if(startZdt.isAfter(endZdt)) {
                editStartTime.setError(getText(R.string.err_start_after_end));
                editEndTime.setError(getText(R.string.err_start_after_end));
                Toast.makeText(this, R.string.err_start_after_end, Toast.LENGTH_LONG).show();
                return false;
            }
            else {
                editStartTime.setError(null);
                editEndTime.setError(null);
            }
        }

        return true;
    }

    public static class Extras {
        public static final String ACTION = "ACTION";
        public static final String ACTION_ADD_CALL = "ADD_CALL";
        public static final String ACTION_SEARCH_CALLS = "SEARCH_CALLS";

        public static final String RESULT_CUSTOMER = "CUSTOMER";
        public static final String RESULT_CALLER = "CALLER";
        public static final String RESULT_CALLEE = "CALLEE";
        public static final String RESULT_START_DATE = "START_DATE";
        public static final String RESULT_START_TIME = "START_TIME";
        public static final String RESULT_END_DATE = "END_DATE";
        public static final String RESULT_END_TIME = "END_TIME";
    }

    private static class DateTimeTextWatcher implements TextWatcher {
        private final EditText editText;

        DateTimeTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            editText.setError(null);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
