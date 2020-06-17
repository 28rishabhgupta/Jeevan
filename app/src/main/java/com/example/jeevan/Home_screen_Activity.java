package com.example.jeevan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_screen_Activity extends AppCompatActivity {
    BottomNavigationView bottom_navigation_view_Home_Screen_Activity;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_);

        bottom_navigation_view_Home_Screen_Activity = findViewById(R.id.bottom_navigation_view_Home_Screen_Activity);
        bottom_navigation_view_Home_Screen_Activity.setSelectedItemId(R.id.action_home );
        bottom_navigation_view_Home_Screen_Activity.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.action_home:

                    case R.id.action_lodge_complain:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.action_profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;
                }
            }
        });

    }
}
