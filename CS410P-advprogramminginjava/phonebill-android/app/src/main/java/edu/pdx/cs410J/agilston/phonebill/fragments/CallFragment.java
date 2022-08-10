package edu.pdx.cs410J.agilston.phonebill.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.pdx.cs410J.agilston.phonebill.PhoneBill;
import edu.pdx.cs410J.agilston.phonebill.PhoneBillList;
import edu.pdx.cs410J.agilston.phonebill.PhoneCall;
import edu.pdx.cs410J.agilston.phonebill.R;
import edu.pdx.cs410J.agilston.phonebill.activities.CallActivity;
import edu.pdx.cs410J.agilston.phonebill.activities.MainActivity;
import edu.pdx.cs410J.agilston.phonebill.adapters.CallAdapter;
import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCallBinding;

public class CallFragment extends Fragment {
    private FragmentCallBinding binding;
    private String customer;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();

                if(data != null) {
                    String action = data.getStringExtra(CallActivity.Extras.ACTION);
                    String caller = data.getStringExtra(CallActivity.Extras.RESULT_CALLER);
                    String callee = data.getStringExtra(CallActivity.Extras.RESULT_CALLEE);
                    String start = String.format("%s %s",
                            data.getStringExtra(CallActivity.Extras.RESULT_START_DATE),
                            data.getStringExtra(CallActivity.Extras.RESULT_START_TIME));
                    String end = String.format("%s %s",
                            data.getStringExtra(CallActivity.Extras.RESULT_END_DATE),
                            data.getStringExtra(CallActivity.Extras.RESULT_END_TIME));

                    if(!TextUtils.isEmpty(caller)) {
                        caller = new StringBuilder(caller)
                                .insert(3, "-")
                                .insert(7, "-")
                                .toString();
                    }

                    if(!TextUtils.isEmpty(callee)) {
                        callee = new StringBuilder(callee)
                                .insert(3, "-")
                                .insert(7, "-")
                                .toString();
                    }

                    CallAdapter adapter = PhoneBillList.getCallAdapter();

                    if(TextUtils.equals(action, CallActivity.Extras.ACTION_ADD_CALL)) {
                        PhoneCall call = new PhoneCall(caller, callee, start, end);
                        Activity activity = requireActivity();
                        PhoneBillList.addCall(activity, customer, call);

                        if(PhoneBillList.getCallCount(customer) == 1 && activity instanceof MainActivity) {
                            ((MainActivity)activity).flipCallView(customer);
                        }

                        adapter.clearFilter();
                    }
                    else {
                        adapter.filter(caller, callee, start, end);
                    }
                }
            }
        });
    }

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
            String newCustomer = bundle.getString(Extras.CUSTOMER);

            if(!TextUtils.equals(newCustomer, customer)) {
                customer = bundle.getString(Extras.CUSTOMER);
                PhoneBill bill = PhoneBillList.getBill(customer);

                if(bill != null) {
                    PhoneBillList.setCallAdapter(new CallAdapter(bill.getPhoneCalls()));
                }
            }

            Activity activity = requireActivity();

            if(activity instanceof MainActivity) {
                ((MainActivity)activity).flipCallView(customer);
                FloatingActionButton fab = activity.findViewById(R.id.fab);

                if(fab != null) {
                    fab.setOnClickListener(v -> {
                        launchCallActivity(CallActivity.Extras.ACTION_ADD_CALL);
                    });
                }
            }
        }
    }

    public void launchCallActivity(String action) {
        Intent intent = new Intent(getActivity(), CallActivity.class);
        intent.putExtra(CallActivity.Extras.ACTION, action);
        intent.putExtra(CallActivity.Extras.RESULT_CUSTOMER, customer);
        launcher.launch(intent);
    }

    public static class Extras {
        public static String CUSTOMER = "CUSTOMER";
    }
}
