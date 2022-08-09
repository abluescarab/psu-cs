package edu.pdx.cs410J.agilston.phonebill.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_content_main);
        View view = requireView();
        RecyclerView recyclerView = view.findViewById(R.id.customer_list);

        adapter = new CustomerAdapter(PhoneBillList.getCustomers(), item -> {
            Bundle bundle = new Bundle();
            bundle.putString(CallFragment.Extras.CUSTOMER, item);
            navController.navigate(R.id.action_customers_to_calls, bundle);
        });

        if(PhoneBillList.getCustomerAdapter() == null) {
            PhoneBillList.setCustomerAdapter(adapter);
        }

        PhoneBillList.addItemDecoration(recyclerView);

        Activity activity = requireActivity();

        if(activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity)activity;
            mainActivity.flipCustomerView();

            FloatingActionButton fab = activity.findViewById(R.id.fab);
            fab.setOnClickListener(v -> {
                TextInputDialogFragment dialog = new TextInputDialogFragment(
                        R.layout.dialog_add_customer,
                        R.string.title_add_customer,
                        R.string.add,
                        R.string.cancel,
                        R.string.hint_customer_name,
                        R.string.required,
                        (dialogInterface, editText) -> {
                            PhoneBillList.addBill(activity, editText.getText().toString());
                            mainActivity.flipCustomerView();
                            dialogInterface.dismiss();
                        },
                        (dialogInterface, editText) -> dialogInterface.cancel());
                dialog.show(mainActivity.getSupportFragmentManager(), TextInputDialogFragment.TAG);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
