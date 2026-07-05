package com.example.moneymate.adapters;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymate.ExpenseStorage;
import com.example.moneymate.R;
import com.example.moneymate.models.Expense;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder>{

    ArrayList<Expense> expenseList;

    public ExpenseAdapter(ArrayList<Expense> expenseList){
        this.expenseList=expenseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){

        Expense expense=expenseList.get(position);

        holder.name.setText(expense.getName());
        holder.amount.setText("Rs."+expense.getAmount());
        holder.paidBy.setText("Paid by "+expense.getPaidBy());
        holder.type.setText(expense.getType());

        holder.itemView.setOnLongClickListener(v->{

            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Expense")
                    .setMessage("Delete this expense?")
                    .setPositiveButton("Delete",(dialog,which)->{

                        ExpenseStorage.expenses.remove(position);
                        notifyDataSetChanged();

                    })
                    .setNegativeButton("Cancel",null)
                    .show();

            return true;

        });

    }

    @Override
    public int getItemCount(){

        return expenseList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,amount,paidBy,type;

        ViewHolder(View itemView){

            super(itemView);

            name=itemView.findViewById(R.id.txtExpenseName);
            amount=itemView.findViewById(R.id.txtExpenseAmount);
            paidBy=itemView.findViewById(R.id.txtPaidBy);
            type=itemView.findViewById(R.id.txtExpenseType);

        }

    }

}