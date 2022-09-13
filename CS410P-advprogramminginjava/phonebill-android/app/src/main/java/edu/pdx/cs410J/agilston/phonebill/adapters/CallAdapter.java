package edu.pdx.cs410J.agilston.phonebill.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.pdx.cs410J.agilston.phonebill.PhoneBill;
import edu.pdx.cs410J.agilston.phonebill.PhoneCall;
import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCallEntryBinding;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.CallViewHolder> {
    private final List<PhoneCall> calls;
    private List<PhoneCall> callsFiltered;

    public CallAdapter(Collection<PhoneCall> calls) {
        this.calls = new ArrayList<>(calls);
        this.callsFiltered = new ArrayList<>(calls);
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

    /**
     * Adds a call to the adapter.
     *
     * @param call call to add
     */
    public void addCall(PhoneCall call) {
        calls.add(call);
    }

    /**
     * Filters the adapter data.
     *
     * @param caller      caller to filter for
     * @param callee      callee to filter for
     * @param startString start date/time to filter for
     * @param endString   end date/time to filter for
     */
    @SuppressLint("NotifyDataSetChanged")
    public void filter(String caller, String callee, String startString, String endString) {
        PhoneBill filteredBill = new PhoneBill("", calls).filter(caller, callee, startString, endString);
        callsFiltered = new ArrayList<>(filteredBill.getPhoneCalls());
        notifyDataSetChanged();
    }

    /**
     * Clears the filter.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void clearFilter() {
        callsFiltered = calls;
        notifyDataSetChanged();
    }

    public static class CallViewHolder extends RecyclerView.ViewHolder {
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
