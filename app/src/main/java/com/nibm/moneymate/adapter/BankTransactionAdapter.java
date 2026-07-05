package com.nibm.moneymate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.moneymate.R;
import com.nibm.moneymate.model.BankTransaction;

import java.util.List;

public class BankTransactionAdapter extends RecyclerView.Adapter<BankTransactionAdapter.ViewHolder> {

    private final List<BankTransaction> transactionList;
    private final OnTransactionActionListener listener;

    public interface OnTransactionActionListener {
        void onEditClicked(BankTransaction transaction);
        void onDeleteClicked(BankTransaction transaction);
    }

    public BankTransactionAdapter(List<BankTransaction> transactionList, OnTransactionActionListener listener) {
        this.transactionList = transactionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank_transaction, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BankTransaction t = transactionList.get(position);

        holder.tvTransType.setText(t.getType());
        holder.tvTransDate.setText(t.getDate());
        holder.tvTransNote.setText(t.getNote() == null || t.getNote().isEmpty() ? "-" : t.getNote());

        if (t.getType().equals("DEPOSIT")) {
            holder.tvTransAmount.setText("+ Rs. " + String.format("%.2f", t.getAmount()));
            holder.tvTransAmount.setTextColor(0xFF2E7D32); // green
        } else {
            holder.tvTransAmount.setText("- Rs. " + String.format("%.2f", t.getAmount()));
            holder.tvTransAmount.setTextColor(0xFFC62828); // red
        }

        // edit icon click
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClicked(t);
        });

        // delete icon click
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClicked(t);
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTransType, tvTransDate, tvTransNote, tvTransAmount;
        ImageButton btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransType = itemView.findViewById(R.id.tvTransType);
            tvTransDate = itemView.findViewById(R.id.tvTransDate);
            tvTransNote = itemView.findViewById(R.id.tvTransNote);
            tvTransAmount = itemView.findViewById(R.id.tvTransAmount);
            btnEdit = itemView.findViewById(R.id.btnEditTransaction);
            btnDelete = itemView.findViewById(R.id.btnDeleteTransaction);
        }
    }
}