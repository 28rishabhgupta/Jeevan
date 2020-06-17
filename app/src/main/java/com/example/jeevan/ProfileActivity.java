package com.example.jeevan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation_view_Profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottom_navigation_view_Profile = findViewById(R.id.bottom_navigation_view_Profile);

        bottom_navigation_view_Profile.setSelectedItemId(R.id.action_profile);
        bottom_navigation_view_Profile.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(),Home_screen_Activity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;
                    case R.id.action_lodge_complain:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.action_profile:

                }
            }
        });
    }
}
