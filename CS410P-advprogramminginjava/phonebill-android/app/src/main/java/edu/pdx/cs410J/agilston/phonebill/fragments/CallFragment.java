package edu.pdx.cs410J.agilston.phonebill.fragments;

import android.app.Activity;
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
import edu.pdx.cs410J.agilston.phonebill.activities.MainActivity;
import edu.pdx.cs410J.agilston.phonebill.adapters.CallAdapter;
import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCallBinding;

public class CallFragment extends Fragment {
    // TODO: ensure start time before end time
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
        RecyclerView recyclerView = view.findViewById(R.id.call_list);
        PhoneBillList.addItemDecoration(recyclerView);

        if(bundle != null) {
            String customer = bundle.getString(Extras.ITEM_NAME);

            if(PhoneBillList.getCallAdapter() == null) {
                PhoneBillList.setCallAdapter(new CallAdapter(new ArrayList<>(PhoneBillList.getBill(customer).getPhoneCalls())));
            }

            Activity activity = requireActivity();

            if(activity instanceof MainActivity) {
                ((MainActivity)activity).flipCallView(customer);
            }
        }

    }

    public static class Extras {
        public static String ITEM_NAME = "ITEM_NAME";
    }
}
