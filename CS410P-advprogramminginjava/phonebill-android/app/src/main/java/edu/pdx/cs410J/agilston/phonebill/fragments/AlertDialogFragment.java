package edu.pdx.cs410J.agilston.phonebill.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;

import edu.pdx.cs410J.agilston.phonebill.R;

public class AlertDialogFragment extends DialogFragment {
    public static String TAG = "ALERT_DIALOG";

    private final int layout;
    private final int title;
    private final int positiveButton;
    private final int negativeButton;
    private final int hint;
    private final OnClickListener positiveAction;
    private final OnClickListener negativeAction;
    private EditText editText;
    private AlertDialog dialog;

    public AlertDialogFragment(@LayoutRes int layout, @StringRes int title, @StringRes int positiveButton,
                               @StringRes int negativeButton, @StringRes int hint, @StringRes int emptyError,
                               OnClickListener positiveAction, OnClickListener negativeAction) {
        this.layout = layout;
        this.title = title;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
        this.hint = hint;
        this.positiveAction = positiveAction;
        this.negativeAction = negativeAction;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(layout, null);

        editText = dialogView.findViewById(R.id.dialog_input);
        editText.setHint(hint);

        builder.setTitle(title);
        builder.setView(dialogView);
        builder.setNegativeButton(negativeButton,
                (dialogInterface, i) -> negativeAction.onClick(dialogInterface, editText));
        builder.setPositiveButton(positiveButton,
                (dialogInterface, i) -> {});

        dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> editText.requestFocus());
        editText.setOnFocusChangeListener((view, b) ->
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE));
        editText.addTextChangedListener(new DialogTextWatcher());
        dialog.show();

        Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positive.setEnabled(false);
        positive.setOnClickListener(view -> positiveAction.onClick(dialog, editText));

        return dialog;
    }

    public interface OnClickListener {
        void onClick(DialogInterface dialogInterface, EditText editText);
    }

    private class DialogTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(!TextUtils.isEmpty(charSequence));
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
