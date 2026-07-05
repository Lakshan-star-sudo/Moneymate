package com.nibm.moneymate.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.moneymate.R;
import com.nibm.moneymate.activities.AddBankAccountActivity;
import com.nibm.moneymate.activities.BankAccountDetailActivity;
import com.nibm.moneymate.model.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class BankAccountAdapter extends RecyclerView.Adapter<BankAccountAdapter.ViewHolder>
        implements Filterable {

    private List<BankAccount> accountList;       // filter una list eka (screen eke pennanne meka)
    private final List<BankAccount> fullList;    // original full list eka (search karaddi mekin filter karanawa)
    private final OnAccountDeleteListener deleteListener;
    private final OnFilterResultListener filterResultListener;

    public interface OnAccountDeleteListener {
        void onDeleteClicked(BankAccount account);
    }

    // filter una passe result ganana kiyanna interface ekak (empty state pennanna)
    public interface OnFilterResultListener {
        void onFilterResult(int count);
    }

    public BankAccountAdapter(List<BankAccount> accountList,
                              OnAccountDeleteListener deleteListener,
                              OnFilterResultListener filterResultListener) {
        this.accountList = accountList;
        this.fullList = new ArrayList<>(accountList);
        this.deleteListener = deleteListener;
        this.filterResultListener = filterResultListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank_account, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BankAccount acc = accountList.get(position);
        holder.tvBankName.setText(acc.getBankName());
        holder.tvAccountNumber.setText("Acc No: " + acc.getAccountNumber());
        holder.tvBalance.setText("Rs. " + String.format("%.2f", acc.getBalance()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), BankAccountDetailActivity.class);
            intent.putExtra("account_id", acc.getId());
            v.getContext().startActivity(intent);
        });

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddBankAccountActivity.class);
            intent.putExtra("account_id", acc.getId());
            v.getContext().startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onDeleteClicked(acc);
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    // ---- NEW: Filter implementation ----
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<BankAccount> filteredList = new ArrayList<>();

                if (constraint == null || constraint.toString().trim().isEmpty()) {
                    filteredList.addAll(fullList);
                } else {
                    String query = constraint.toString().toLowerCase().trim();
                    for (BankAccount acc : fullList) {
                        if (acc.getBankName().toLowerCase().contains(query)) {
                            filteredList.add(acc);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                accountList = (List<BankAccount>) results.values;
                notifyDataSetChanged();

                if (filterResultListener != null) {
                    filterResultListener.onFilterResult(accountList.size());
                }
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBankName, tvAccountNumber, tvBalance;
        ImageButton btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBankName = itemView.findViewById(R.id.tvBankName);
            tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber);
            tvBalance = itemView.findViewById(R.id.tvBalance);
            btnEdit = itemView.findViewById(R.id.btnEditAccount);
            btnDelete = itemView.findViewById(R.id.btnDeleteAccount);
        }
    }
}