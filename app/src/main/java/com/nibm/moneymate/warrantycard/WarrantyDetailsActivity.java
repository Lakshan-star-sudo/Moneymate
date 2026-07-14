package com.nibm.moneymate.warrantycard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nibm.moneymate.R;

public class WarrantyDetailsActivity extends AppCompatActivity {
    Button btnEditWarranty, btnDeleteWarranty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warranty_details);
        getSupportActionBar().hide();

        btnEditWarranty = findViewById(R.id.btnEditWarranty);
        btnDeleteWarranty = findViewById(R.id.btnDeleteWarranty);

        btnEditWarranty.setOnClickListener(
                v -> {
            Intent intent = new Intent(WarrantyDetailsActivity.this, EditWarrantyActivity.class);
            startActivity(intent);
        });

        btnDeleteWarranty.setOnClickListener(
                v -> {
            Toast.makeText(WarrantyDetailsActivity.this, "Warranty deleted",
                    Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}