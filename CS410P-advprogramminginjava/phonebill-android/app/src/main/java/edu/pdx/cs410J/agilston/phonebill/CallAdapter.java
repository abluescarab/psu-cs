package edu.pdx.cs410J.agilston.phonebill;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCallEntryBinding;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolder> {
    private final List<PhoneCall> calls;

    public CallAdapter(List<PhoneCall> calls) {
        this.calls = calls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentCallEntryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhoneCall call = calls.get(position);

        holder.callerNumber.setText(call.getCaller());
        holder.calleeNumber.setText(call.getCallee());
        holder.startDate.setText(call.getBeginTimeString());
        holder.endDate.setText(call.getEndTimeString());
        holder.duration.setText(call.getDurationString());
    }

    @Override
    public int getItemCount() {
        return calls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView callerNumber;
        public final TextView calleeNumber;
        public final TextView startDate;
        public final TextView endDate;
        public final TextView duration;

        public ViewHolder(FragmentCallEntryBinding binding) {
            super(binding.getRoot());
            callerNumber = binding.entryCaller;
            calleeNumber = binding.entryCallee;
            startDate = binding.entryStart;
            endDate = binding.entryEnd;
            duration = binding.entryDuration;
        }
    }
}
