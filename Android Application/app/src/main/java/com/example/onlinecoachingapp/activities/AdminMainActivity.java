package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.fragments.AdminHomeFragment;
import com.example.onlinecoachingapp.session.SessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    private SessionManager sessionManager;

    private TextView txtUserName;
    private TextView txtUserEmail;
    private TextView txtUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        sessionManager = new SessionManager(this);

        initViews();
        setupToolbar();
        setupDrawer();
        loadHeader();

        if (savedInstanceState == null) {
            loadFragment(new AdminHomeFragment());
            navigationView.setCheckedItem(R.id.admin_home);
        }

        handleBackPressed();
    }

    private void initViews() {

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        View header = navigationView.getHeaderView(0);

        txtUserName = header.findViewById(R.id.txtUserName);
        txtUserEmail = header.findViewById(R.id.txtUserEmail);
        txtUserRole = header.findViewById(R.id.txtUserRole);
    }

    private void setupToolbar() {

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this,
                        drawerLayout,
                        toolbar,
                        R.string.open_drawer,
                        R.string.close_drawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupDrawer() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadHeader() {

        txtUserName.setText(sessionManager.getName());
        txtUserEmail.setText(sessionManager.getEmail());
        txtUserRole.setText(sessionManager.getRole());
    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.admin_home) {

            toolbar.setTitle("Admin Dashboard");

            loadFragment(new AdminHomeFragment());
        }

        else if (id == R.id.admin_students) {

            startActivity(new Intent(
                    AdminMainActivity.this,
                    ManageStudentsActivity.class));
        }

        else if (id == R.id.admin_teachers) {

            startActivity(new Intent(
                    AdminMainActivity.this,
                    ManageTeachersActivity.class));
        }

        else if (id == R.id.admin_courses) {

            startActivity(new Intent(
                    AdminMainActivity.this,
                    ManageCoursesActivity.class));
        }

        else if (id == R.id.admin_batches) {

            startActivity(new Intent(
                    AdminMainActivity.this,
                    ManageBatchesActivity.class));
        }

        else if (id == R.id.admin_reports) {

            startActivity(new Intent(
                    AdminMainActivity.this,
                    AdminReportActivity.class));
        }

        else if (id == R.id.admin_profile) {

            startActivity(new Intent(
                    AdminMainActivity.this,
                    AdminProfileActivity.class));
        }

        else if (id == R.id.admin_logout) {

            sessionManager.clearSession();

            Intent intent = new Intent(
                    AdminMainActivity.this,
                    LoginActivity.class);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

            finish();
        }

        drawerLayout.closeDrawers();

        return true;
    }

    private void handleBackPressed() {

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        if (drawerLayout.isDrawerOpen(navigationView)) {

                            drawerLayout.closeDrawer(navigationView);

                        } else {

                            finish();
                        }
                    }
                });
    }
}