package com.nibm.moneymate.common;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.nibm.moneymate.R;

public class ReportsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}