package com.nibm.moneymate.common;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.nibm.moneymate.R;

public class CurrencyConverterActivity extends AppCompatActivity {

    EditText etAmount;
    Button btnConvert;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        etAmount = findViewById(R.id.et_convert_amount);
        btnConvert = findViewById(R.id.btn_convert);
        tvResult = findViewById(R.id.tv_converted_result);

        btnConvert.setOnClickListener(v -> {
            String amountStr = etAmount.getText().toString();
            if (!amountStr.isEmpty()) {
                double amount = Double.parseDouble(amountStr);
                // Simple demo calculation (e.g., USD to LKR)
                double result = amount * 300.0; 
                tvResult.setText(String.format("%.2f", result));
            } else {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            }
        });
    }
}