package com.example.moneymate.adapters;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymate.GroupDetailsActivity;
import com.example.moneymate.R;
import com.example.moneymate.models.Group;

import java.util.List;
import android.content.Context;
import android.content.Intent;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    List<Group> groupList;

    public GroupAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Group group = groupList.get(position);

        holder.name.setText(group.getName());
        holder.members.setText(group.getMembers());
        holder.balance.setText(group.getBalance());

        holder.itemView.setOnClickListener(v -> {

            Context context = v.getContext();

            Intent intent = new Intent(context, GroupDetailsActivity.class);
            context.startActivity(intent);

        });
        holder.itemView.setOnLongClickListener(v -> {

            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Group")
                    .setMessage("Are you sure you want to delete this group?")
                    .setPositiveButton("Delete", (dialog, which) -> {

                        groupList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, groupList.size());

                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;

        });

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, members, balance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtGroupName);
            members = itemView.findViewById(R.id.txtMembers);
            balance = itemView.findViewById(R.id.txtBalance);

        }
    }

}