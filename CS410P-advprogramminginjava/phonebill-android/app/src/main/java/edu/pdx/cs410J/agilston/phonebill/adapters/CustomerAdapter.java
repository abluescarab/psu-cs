package edu.pdx.cs410J.agilston.phonebill.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCustomerEntryBinding;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> implements Filterable {
    private final List<String> customers;
    private final OnItemClickListener onItemClick;
    private List<String> customersFiltered;

    public CustomerAdapter(List<String> customers, OnItemClickListener onItemClick) {
        this.customers = customers;
        this.customersFiltered = customers;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerViewHolder(FragmentCustomerEntryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        holder.customerName.setText(customers.get(position));
        holder.itemView.setOnClickListener(view -> onItemClick.invoke(customersFiltered.get(position)));
    }

    @Override
    public int getItemCount() {
        return customersFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public interface OnItemClickListener {
        void invoke(String item);
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        public final TextView customerName;

        public CustomerViewHolder(FragmentCustomerEntryBinding binding) {
            super(binding.getRoot());
            customerName = binding.customerName;
        }
    }
}
