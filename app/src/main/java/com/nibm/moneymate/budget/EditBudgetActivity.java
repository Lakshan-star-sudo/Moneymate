package com.nibm.moneymate.budget;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nibm.moneymate.R;

public class EditBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);
        getSupportActionBar().hide();

    }
}



