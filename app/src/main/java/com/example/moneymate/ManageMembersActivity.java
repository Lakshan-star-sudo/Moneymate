package com.example.moneymate;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManageMembersActivity extends AppCompatActivity {

    ArrayList<String> members;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_members);

        ListView listView = findViewById(R.id.listMembers);
        EditText etMember = findViewById(R.id.etMember);
        Button btnAdd = findViewById(R.id.btnAddMember);

        members = new ArrayList<>();

        members.add("Kavi");
        members.add("Nimal");
        members.add("Kasun");

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                members);

        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {

            String name = etMember.getText().toString().trim();

            if(name.isEmpty()) return;

            members.add(name);

            adapter.notifyDataSetChanged();

            etMember.setText("");

        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {

            new AlertDialog.Builder(this)
                    .setTitle("Remove Member")
                    .setMessage("Are you sure you want to remove this member?")
                    .setPositiveButton("Remove", (dialog, which) -> {

                        members.remove(position);
                        adapter.notifyDataSetChanged();

                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;

        });

    }

}


