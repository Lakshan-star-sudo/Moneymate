package com.example.moneymate;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymate.adapters.GroupAdapter;

import com.example.moneymate.models.Group;

import java.util.ArrayList;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.moneymate.ManageMembersActivity;
import com.example.moneymate.ActivityHistoryActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class DashboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Group> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerGroups);

        groups = new ArrayList<>();

        groups.add(new Group("🏖 Trip to Kandy","2 Members","You owe Rs.500"));
       groups.add(new Group("🎂 Birthday Party","2 Members","You get Rs.1200"));
       groups.add(new Group("🏠 House Rent","3 Members","Settled Up"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GroupAdapter(groups));

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, CreateGroupActivity.class);
            startActivity(intent);
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if(id == R.id.nav_groups){

                return true;

            }

            if(id == R.id.nav_activity){

                startActivity(new Intent(this, ActivityHistoryActivity.class));
                return true;

            }

            if(id == R.id.nav_members){

                startActivity(new Intent(this, ManageMembersActivity.class));
                return true;

            }

            return false;

        });

    }
}