package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.fragments.AssignmentFragment;
import com.example.onlinecoachingapp.fragments.BatchFragment;
import com.example.onlinecoachingapp.fragments.CourseFragment;
import com.example.onlinecoachingapp.fragments.HomeFragment;
import com.example.onlinecoachingapp.fragments.ProfileFragment;
import com.example.onlinecoachingapp.session.SessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MaterialToolbar toolbar;
    SessionManager sessionManager;
    TextView txtUserName,txtUserEmail,txtUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        sessionManager = new SessionManager(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this,
                        drawerLayout,
                        toolbar,
                        R.string.open,
                        R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Get the Navigation Header
        View headerView = navigationView.getHeaderView(0);

        // Find TextViews inside navigation_header.xml
        txtUserName = headerView.findViewById(R.id.txtUserName);
        txtUserEmail = headerView.findViewById(R.id.txtUserEmail);
        txtUserRole = headerView.findViewById(R.id.txtUserRole);

        // Display logged-in user details
        txtUserName.setText(sessionManager.getName());
        txtUserEmail.setText(sessionManager.getEmail());
        txtUserRole.setText(sessionManager.getRole());

        // ---------------- Role Based Menu ----------------

        String role = sessionManager.getRole();

        navigationView.getMenu().clear();

        if (role.equalsIgnoreCase("ADMIN")) {

            navigationView.inflateMenu(R.menu.admin_menu);

        } else if (role.equalsIgnoreCase("TEACHER")) {

            navigationView.inflateMenu(R.menu.teacher_menu);

        } else {

            navigationView.inflateMenu(R.menu.student_menu);

        }

        // ---------------- Default Fragment ----------------

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout,
                        new HomeFragment())
                .commit();


        // ---------------- Menu Click ----------------

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .commit();

            } else if (id == R.id.nav_courses) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new CourseFragment())
                        .commit();

            } else if (id == R.id.nav_batches) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new BatchFragment())
                        .commit();

            } else if (id == R.id.nav_assignments) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new AssignmentFragment())
                        .commit();

            } else if (id == R.id.nav_profile) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new ProfileFragment())
                        .commit();

            } else if (id == R.id.nav_logout) {

                sessionManager.logout();

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }

            drawerLayout.closeDrawers();

            return true;
        });
    }
}