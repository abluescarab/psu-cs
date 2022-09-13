package edu.pdx.cs410J.agilston.phonebill.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.pdx.cs410J.agilston.phonebill.PhoneBillList;
import edu.pdx.cs410J.agilston.phonebill.R;
import edu.pdx.cs410J.agilston.phonebill.activities.MainActivity;
import edu.pdx.cs410J.agilston.phonebill.adapters.CustomerAdapter;
import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCustomerBinding;

public class CustomerFragment extends Fragment {
    private FragmentCustomerBinding binding;
    private CustomerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        Activity activity = requireActivity();
        NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_content_main);
        View view = requireView();
        RecyclerView recyclerView = view.findViewById(R.id.customer_list);

        adapter = new CustomerAdapter(PhoneBillList.getCustomers(), item -> {
            if(activity instanceof MainActivity) {
                ((MainActivity)activity).resetSearch();
            }

            Bundle bundle = new Bundle();
            bundle.putString(CallFragment.Extras.CUSTOMER, item);
            navController.navigate(R.id.action_customers_to_calls, bundle);
        });

        if(PhoneBillList.getCustomerAdapter() == null) {
            PhoneBillList.setCustomerAdapter(adapter);
        }

        MainActivity.addItemDecoration(recyclerView);

        if(activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity)activity;
            flipView();

            FloatingActionButton fab = activity.findViewById(R.id.fab);
            fab.setOnClickListener(v -> {
                TextInputDialogFragment inputDialog = new TextInputDialogFragment(
                        R.layout.dialog_add_customer,
                        R.string.title_add_customer,
                        R.string.add,
                        R.string.cancel,
                        R.string.hint_customer_name,
                        (dialogInterface, editText) -> {
                            String customer = editText.getText().toString();

                            if(PhoneBillList.hasCustomer(customer)) {
                                String message = getText(R.string.confirm_replace_customer).toString();
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                                builder.setTitle(R.string.confirm);
                                builder.setMessage(String.format(message, customer));
                                builder.setPositiveButton(R.string.yes, (dialog, i) -> {
                                    confirm(editText, dialog);
                                    dialogInterface.dismiss();
                                });
                                builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
                                builder.show();
                            }
                            else {
                                confirm(editText, dialogInterface);
                            }
                        },
                        (dialogInterface, editText) -> dialogInterface.cancel());
                inputDialog.show(mainActivity.getSupportFragmentManager(), TextInputDialogFragment.TAG);
            });
        }
    }

    /**
     * Flips the fragment view between an empty message and a list of customers.
     */
    private void flipView() {
        CustomerAdapter adapter = PhoneBillList.getCustomerAdapter();
        MainActivity.flipView(binding.customerFlipper, requireActivity().findViewById(R.id.customer_list),
                adapter.getItemCount(), adapter);
    }

    /**
     * Confirms the add customer dialog box.
     *
     * @param editText dialog box editText
     * @param dialog   dialog box
     */
    private void confirm(EditText editText, DialogInterface dialog) {
        MainActivity activity = (MainActivity)requireActivity();
        PhoneBillList.addCustomer(activity, editText.getText().toString());
        flipView();
        dialog.dismiss();

        if(adapter != null) {
            adapter.getFilter().filter("");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
