package edu.pdx.cs410J.agilston.phonebill.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.pdx.cs410J.agilston.phonebill.PhoneBill;
import edu.pdx.cs410J.agilston.phonebill.PhoneCall;
import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCallEntryBinding;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.CallViewHolder> {
    private final PhoneBill bill;
    private final List<PhoneCall> calls;
    private List<PhoneCall> callsFiltered;

    public CallAdapter(PhoneBill bill) {
        this.bill = bill;
        this.calls = new ArrayList<>(bill.getPhoneCalls());
        this.callsFiltered = new ArrayList<>(bill.getPhoneCalls());
    }

    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CallViewHolder(FragmentCallEntryBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CallViewHolder holder, int position) {
        PhoneCall call = callsFiltered.get(position);

        holder.callerNumber.setText(call.getCaller());
        holder.calleeNumber.setText(call.getCallee());
        holder.startDate.setText(call.getBeginTimeString());
        holder.endDate.setText(call.getEndTimeString());
        holder.duration.setText(call.getDurationString());
    }

    @Override
    public int getItemCount() {
        return callsFiltered.size();
    }

    public void addCall(PhoneCall call) {
        calls.add(call);
    }

    public void filter(String caller, String callee, String startString, String endString) {
        PhoneBill filteredBill = bill.filter(caller, callee, startString, endString);
        callsFiltered = new ArrayList<>(filteredBill.getPhoneCalls());
        notifyDataSetChanged();
    }

    public void clearFilter() {
        callsFiltered = new ArrayList<>(bill.getPhoneCalls());
        notifyDataSetChanged();
    }

    public class CallViewHolder extends RecyclerView.ViewHolder {
        public final TextView callerNumber;
        public final TextView calleeNumber;
        public final TextView startDate;
        public final TextView endDate;
        public final TextView duration;

        public CallViewHolder(FragmentCallEntryBinding binding) {
            super(binding.getRoot());
            callerNumber = binding.entryCaller;
            calleeNumber = binding.entryCallee;
            startDate = binding.entryStart;
            endDate = binding.entryEnd;
            duration = binding.entryDuration;
        }
    }
}
