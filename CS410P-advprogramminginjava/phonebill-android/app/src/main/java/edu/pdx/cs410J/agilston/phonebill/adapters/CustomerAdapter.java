package edu.pdx.cs410J.agilston.phonebill.adapters;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.ArrayList;
import java.util.List;

import edu.pdx.cs410J.agilston.phonebill.databinding.FragmentCustomerEntryBinding;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> implements Filterable {
    private final OnItemClickListener onItemClickListener;
    private final SortedList<String> customers;
    private List<String> customersFiltered;

    public CustomerAdapter(List<String> customers, OnItemClickListener onItemClickListener) {
        this.customers = new SortedList<>(String.class, new CustomerListCallback());
        this.customers.addAll(customers);
        this.customersFiltered = customers;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerViewHolder(FragmentCustomerEntryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        holder.customerName.setText(customersFiltered.get(position));
        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(customersFiltered.get(position)));
    }

    @Override
    public int getItemCount() {
        return customersFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                customersFiltered = new ArrayList<>();

                if(TextUtils.isEmpty(constraint)) {
                    for(int i = 0; i < customers.size(); i++) {
                        customersFiltered.add(customers.get(i));
                    }
                }
                else {
                    for(int i = 0; i < customers.size(); i++) {
                        String item = customers.get(i);

                        if(item.contains(constraint)) {
                            customersFiltered.add(item);
                        }
                    }
                }

                results.count = customersFiltered.size();
                results.values = customersFiltered;
                return results;
            }

            @SuppressWarnings("unchecked")
            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results.values == null) {
                    customersFiltered = new ArrayList<>();
                }
                else {
                    if(results.values.getClass() == ArrayList.class) {
                        customersFiltered = (ArrayList<String>)results.values;
                    }
                }

                notifyDataSetChanged();
            }
        };
    }

    public void addCustomer(String customer) {
        customers.add(customer);
    }

    public void addCustomers(String[] customers) {
        this.customers.addAll(customers);
    }

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
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
            return TextUtils.equals(item1, item2);
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
