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

import java.util.ArrayList;

import edu.pdx.cs410J.agilston.phonebill.PhoneBillList;
import edu.pdx.cs410J.agilston.phonebill.PhoneCall;
import edu.pdx.cs410J.agilston.phonebill.R;
import edu.pdx.cs410J.agilston.phonebill.activities.CallActivity;
import edu.pdx.cs410J.agilston.phonebill.activities.MainActivity;
import edu.pdx.cs410J.agilston.phonebill.adapters.CallAdapter;
import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCallBinding;

public class CallFragment extends Fragment {
    // TODO: ensure start time before end time
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
                    PhoneCall call = new PhoneCall(
                            data.getStringExtra(CallActivity.Extras.RESULT_CALLER),
                            data.getStringExtra(CallActivity.Extras.RESULT_CALLEE),
                            data.getStringExtra(CallActivity.Extras.RESULT_START),
                            data.getStringExtra(CallActivity.Extras.RESULT_END)
                    );

                    Activity activity = requireActivity();
                    PhoneBillList.addCall(activity, customer, call);

                    if(PhoneBillList.getCallCount(customer) == 1 && activity instanceof MainActivity) {
                        ((MainActivity)activity).flipCallView(customer);
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
            Log.i("NEW_CUSTOMER", newCustomer);
            if(!TextUtils.equals(newCustomer, customer)) {
                customer = bundle.getString(Extras.CUSTOMER);
                PhoneBill bill = PhoneBillList.getBill(customer);

                if(bill != null) {
                    PhoneBillList.setCallAdapter(new CallAdapter(new ArrayList<>(PhoneBillList.getBill(customer)
                                                                                              .getPhoneCalls())));
                }
            }

            Activity activity = requireActivity();

            if(activity instanceof MainActivity) {
                ((MainActivity)activity).flipCallView(customer);
                FloatingActionButton fab = activity.findViewById(R.id.fab);

                if(fab != null) {
                    fab.setOnClickListener(v -> {
                        Intent intent = new Intent(activity, CallActivity.class);
                        intent.putExtra(CallActivity.Extras.ACTION, CallActivity.Extras.ACTION_ADD_CALL);
                        intent.putExtra(CallActivity.Extras.ACTION_CUSTOMER, customer);
                        launcher.launch(intent);
                    });
                }
            }
        }
    }

    public static class Extras {
        public static String CUSTOMER = "CUSTOMER";
    }
}
