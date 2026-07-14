package com.nibm.moneymate.warrantycard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nibm.moneymate.R;

public class WarrantyActivity extends AppCompatActivity {

     FloatingActionButton fabAddWarranty;
     Button btnViewWarranty1, btnEditWarranty1, btnDeleteWarranty1, btnViewWarranty2,  btnEditWarranty2,btnDeleteWarranty2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warranty);
        getSupportActionBar().hide();

        fabAddWarranty = findViewById(R.id.fabAddWarranty);

        btnViewWarranty1 = findViewById(R.id.btnViewWarranty1);
        btnEditWarranty1 = findViewById(R.id.btnEditWarranty1);
        btnDeleteWarranty1 = findViewById(R.id.btnDeleteWarranty1);

        btnViewWarranty2 = findViewById(R.id.btnViewWarranty2);
        btnEditWarranty2 = findViewById(R.id.btnEditWarranty2);
        btnDeleteWarranty2 = findViewById(R.id.btnDeleteWarranty2);

        fabAddWarranty.setOnClickListener(
                v -> {
                 Intent intent = new Intent(WarrantyActivity.this, AddWarrantyActivity.class);
                 startActivity(intent);
        });

        btnViewWarranty1.setOnClickListener(
                v -> {
                    Intent intent = new Intent(WarrantyActivity.this, WarrantyDetailsActivity.class);
                    startActivity(intent);
        });

        btnViewWarranty2.setOnClickListener(
                v-> {
                    Intent intent = new Intent(WarrantyActivity.this, WarrantyDetailsActivity.class);
                    startActivity(intent);
        });

        btnEditWarranty1.setOnClickListener(
                v -> {
                     Intent intent = new Intent(WarrantyActivity.this, EditWarrantyActivity.class);
                     startActivity(intent);
        });

        btnEditWarranty2.setOnClickListener(
                v-> {
                      Intent intent = new Intent(WarrantyActivity.this, EditWarrantyActivity.class);
                      startActivity(intent);
        });

        btnDeleteWarranty1.setOnClickListener(
                v ->
                Toast.makeText(WarrantyActivity.this, "Warranty deleted",
                        Toast.LENGTH_SHORT).show()
        );

        btnDeleteWarranty2.setOnClickListener(
                v ->
                        Toast.makeText(WarrantyActivity.this, "Warranty deleted",
                        Toast.LENGTH_SHORT).show()
        );
    }
}