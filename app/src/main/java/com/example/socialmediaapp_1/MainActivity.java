package com.example.socialmediaapp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.socialmediaapp_1.Fragments.AddFragment;
import com.example.socialmediaapp_1.Fragments.HomeFragment;
import com.example.socialmediaapp_1.Fragments.ActivityFragment;
import com.example.socialmediaapp_1.Fragments.ProfileFragment;
import com.example.socialmediaapp_1.Fragments.SearchFragment;
import com.example.socialmediaapp_1.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    // Listener for Bottom Navigation View
    private NavigationBarView.OnItemSelectedListener btmNavListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_notification:
                            selectedFragment = new ActivityFragment();
                            break;
                        case R.id.nav_add:
                            selectedFragment = new AddFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };


    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_fragment_container,
                new HomeFragment()).commit();

        binding.bottomNavigationView.setOnItemSelectedListener(btmNavListener);
    }
}