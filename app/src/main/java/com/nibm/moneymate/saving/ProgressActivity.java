package com.nibm.moneymate.saving;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nibm.moneymate.R;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        getSupportActionBar().hide();
    }
}