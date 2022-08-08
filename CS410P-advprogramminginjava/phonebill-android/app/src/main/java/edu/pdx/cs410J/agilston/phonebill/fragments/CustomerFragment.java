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
            bundle.putString(CallFragment.Extras.ITEM_NAME, item);
            navController.navigate(R.id.action_customers_to_calls, bundle);
        });

        if(PhoneBillList.getCustomerAdapter() == null) {
            PhoneBillList.setCustomerAdapter(adapter);
        }

        PhoneBillList.addItemDecoration(recyclerView);

        Activity activity = requireActivity();

        if(activity instanceof MainActivity) {
            ((MainActivity)activity).flipCustomerView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
