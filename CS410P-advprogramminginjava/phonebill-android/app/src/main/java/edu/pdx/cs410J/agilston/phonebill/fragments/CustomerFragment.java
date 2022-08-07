package edu.pdx.cs410J.agilston.phonebill.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import edu.pdx.cs410J.agilston.phonebill.R;
import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCustomerBinding;

public class CustomerFragment extends Fragment {
    private FragmentCustomerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        View view = getView();

        if(view != null) {
            RecyclerView recyclerView = view.findViewById(R.id.customer_list);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
