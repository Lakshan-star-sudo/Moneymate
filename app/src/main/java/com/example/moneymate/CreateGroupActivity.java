package com.example.moneymate;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateGroupActivity extends AppCompatActivity {

    EditText etName, etDescription;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        etName = findViewById(R.id.etGroupName);
        etDescription = findViewById(R.id.etDescription);
        radioGroup = findViewById(R.id.radioGroupType);

        Button btn = findViewById(R.id.btnCreateGroup);

        btn.setOnClickListener(v -> {

            if(etName.getText().toString().isEmpty()){

                Toast.makeText(this,"Enter Group Name",Toast.LENGTH_SHORT).show();

                return;

            }

            Toast.makeText(this,"Group Created Successfully!",Toast.LENGTH_SHORT).show();

            finish();

        });

    }
}