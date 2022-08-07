package edu.pdx.cs410J.agilston.phonebill.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.pdx.cs410J.agilston.phonebill.PhoneBillList;
import edu.pdx.cs410J.agilston.phonebill.R;
import edu.pdx.cs410J.agilston.phonebill.adapters.CallAdapter;
import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCallBinding;

public class CallFragment extends Fragment {
    private FragmentCallBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCallBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if(bundle != null) {
            String customer = bundle.getString(Extras.ITEM_NAME);
            RecyclerView recyclerView = view.findViewById(R.id.call_list);
            recyclerView.setAdapter(new CallAdapter(new ArrayList<>(PhoneBillList.getBill(customer).getPhoneCalls())));
        }
    }

    public static class Extras {
        public static String ITEM_NAME = "ITEM_NAME";
    }
}
