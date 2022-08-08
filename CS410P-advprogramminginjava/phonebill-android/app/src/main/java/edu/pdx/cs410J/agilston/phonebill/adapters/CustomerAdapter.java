package edu.pdx.cs410J.agilston.phonebill.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.List;

import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCustomerEntryBinding;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> implements Filterable {
    private final OnItemClickListener onItemClick;
    private SortedList<String> customers;
    private SortedList<String> customersFiltered;

    public CustomerAdapter(List<String> customers, OnItemClickListener onItemClick) {
        this.customers = new SortedList<>(String.class, new CustomerListCallback());
        this.customers.addAll(customers);
        this.customersFiltered = this.customers;
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

    // TODO: do not add if duplicate
    public void addCustomer(String customer) {
        customers.add(customer);
        notifyItemInserted(customers.indexOf(customer));
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

    private class CustomerListCallback extends SortedList.Callback<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemChanged(position);
        }

        @Override
        public boolean areContentsTheSame(String oldItem, String newItem) {
            return TextUtils.equals(oldItem, newItem);
        }

        @Override
        public boolean areItemsTheSame(String item1, String item2) {
            return item1 == item2;
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemInserted(position);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRemoved(position);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }
    }
}
