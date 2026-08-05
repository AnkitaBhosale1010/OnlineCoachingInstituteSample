package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.fragments.CourseFragment;
import com.example.onlinecoachingapp.fragments.StudentHomeFragment;
import com.example.onlinecoachingapp.fragments.MyCourseFragment;
import com.example.onlinecoachingapp.fragments.ProfileFragment;
import com.example.onlinecoachingapp.session.SessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import android.widget.TextView;
import android.view.View;

public class StudentMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
        setContentView(R.layout.activity_student_main);

        sessionManager = new SessionManager(this);

        initViews();

        setupToolbar();

        setupNavigationDrawer();

        loadHeaderData();

        if (savedInstanceState == null) {
            loadFragment(new StudentHomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }

        handleBackPressed();
    }

    private void initViews() {

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        View headerView = navigationView.getHeaderView(0);

        txtUserName = headerView.findViewById(R.id.txtUserName);
        txtUserEmail = headerView.findViewById(R.id.txtUserEmail);
        txtUserRole = headerView.findViewById(R.id.txtUserRole);
    }

    private void setupToolbar() {

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavigationDrawer() {

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadHeaderData() {

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

        if (id == R.id.nav_home) {

            toolbar.setTitle("Home");
            loadFragment(new StudentHomeFragment());

        } else if (id == R.id.nav_courses) {

            toolbar.setTitle("Courses");
            loadFragment(new CourseFragment());

        } else if (id == R.id.nav_my_courses) {

            toolbar.setTitle("My Courses");
            loadFragment(new MyCourseFragment());

        }  else if (id == R.id.nav_messages) {

        Intent intent = new Intent(
                StudentMainActivity.this,
                MessageActivity.class);

        startActivity(intent);

        } else if (id == R.id.nav_profile) {

            toolbar.setTitle("Profile");
            loadFragment(new ProfileFragment());

        } else if (id == R.id.nav_logout) {

            logout();
        }

        return true;
    }

    private void logout() {

        sessionManager.clearSession();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
    }

    private void handleBackPressed() {

        getOnBackPressedDispatcher().addCallback(this,
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