package com.nibm.moneymate.warrantycard;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nibm.moneymate.R;

public class AddWarrantyActivity extends AppCompatActivity {

    Button btnSaveWarranty,btnSelectWarrantyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warranty);
        getSupportActionBar().hide();

        btnSaveWarranty = findViewById(R.id.btnSaveWarranty);
        btnSelectWarrantyImage = findViewById(R.id.btnSelectWarrantyImage);

        btnSelectWarrantyImage.setOnClickListener(
                v ->
                Toast.makeText(AddWarrantyActivity.this, "Image selection will be added later",
                        Toast.LENGTH_SHORT).show()
        );

        btnSaveWarranty.setOnClickListener(
                v -> {
                    Toast.makeText(AddWarrantyActivity.this, "Warranty saved successfully",
                    Toast.LENGTH_SHORT).show();
                    finish();
        });
    }
}