package edu.pdx.cs410J.agilston.phonebill.fragments;

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

        // set adapter for customer list
        RecyclerView recyclerView = requireView().findViewById(R.id.customer_list);
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_content_main);

        adapter = new CustomerAdapter(PhoneBillList.getCustomers(), item -> {
            Bundle bundle = new Bundle();
            bundle.putString(CallFragment.Extras.ITEM_NAME, item);
            navController.navigate(R.id.action_customers_to_calls, bundle);
        });

        PhoneBillList.setCustomerAdapter(adapter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
