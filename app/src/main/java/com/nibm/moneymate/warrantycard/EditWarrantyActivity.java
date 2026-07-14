package com.nibm.moneymate.warrantycard;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nibm.moneymate.R;

public class EditWarrantyActivity extends AppCompatActivity {

    Button btnUpdateWarranty,btnSelectWarrantyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_warranty);
        getSupportActionBar().hide();

        btnUpdateWarranty = findViewById(R.id.btnUpdateWarranty);
        btnSelectWarrantyImage = findViewById(R.id.btnSelectWarrantyImage);

        btnSelectWarrantyImage.setOnClickListener(
                v ->
                Toast.makeText(EditWarrantyActivity.this, "Image selection will be added later",
                        Toast.LENGTH_SHORT).show()
        );

        btnUpdateWarranty.setOnClickListener(
                v -> {
            Toast.makeText(EditWarrantyActivity.this, "Warranty updated successfully",
                    Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}