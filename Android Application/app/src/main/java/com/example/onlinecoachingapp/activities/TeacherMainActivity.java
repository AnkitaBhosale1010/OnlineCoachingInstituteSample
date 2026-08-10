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
import com.example.onlinecoachingapp.fragments.TeacherHomeFragment;
import com.example.onlinecoachingapp.session.SessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class TeacherMainActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_teacher_main);

        sessionManager = new SessionManager(this);

        initViews();
        setupToolbar();
        setupDrawer();
        loadHeader();

        if(savedInstanceState == null){
            loadFragment(new TeacherHomeFragment());
            navigationView.setCheckedItem(R.id.teacher_home);
        }

        handleBackPressed();
    }

    private void initViews(){

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        View header = navigationView.getHeaderView(0);

        txtUserName = header.findViewById(R.id.txtUserName);
        txtUserEmail = header.findViewById(R.id.txtUserEmail);
        txtUserRole = header.findViewById(R.id.txtUserRole);
    }

    private void setupToolbar(){

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this,
                        drawerLayout,
                        toolbar,
                        R.string.open_drawer,
                        R.string.close_drawer
                );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupDrawer(){

        navigationView.setNavigationItemSelectedListener(this);

    }

    private void loadHeader(){

        txtUserName.setText(sessionManager.getName());
        txtUserEmail.setText(sessionManager.getEmail());
        txtUserRole.setText(sessionManager.getRole());

    }

    private void loadFragment(Fragment fragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        drawerLayout.closeDrawers();
    }

    private void openCourseSelection(String action){

        Intent intent =
                new Intent(
                        TeacherMainActivity.this,
                        SelectCourseActivity.class
                );

        intent.putExtra("action", action);

        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        int id = item.getItemId();

        if(id == R.id.teacher_home){

            toolbar.setTitle("Teacher Dashboard");

            loadFragment(new TeacherHomeFragment());

        }
        else if(id == R.id.teacher_courses){

            startActivity(new Intent(
                    this,
                    MyCoursesActivity.class
            ));

        }
        else if(id == R.id.teacher_create_course){

            startActivity(new Intent(
                    this,
                    CreateCourseActivity.class
            ));

        }
        else if(id == R.id.teacher_lecture){

            openCourseSelection("lecture");

        }
        else if(id == R.id.teacher_material){

            openCourseSelection("material");

        }
        else if(id == R.id.teacher_assignment){

            openCourseSelection("assignment");

        }
        else if(id == R.id.teacher_quiz){

            openCourseSelection("quiz");

        }
        else if(id == R.id.teacher_students){

            openCourseSelection("student");

        }
        else if(id == R.id.teacher_profile){

            startActivity(new Intent(
                    this,
                    TeacherProfileActivity.class
            ));

        }
        else if(id == R.id.teacher_logout){

            sessionManager.clearSession();

            Intent intent =
                    new Intent(
                            this,
                            LoginActivity.class
                    );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();

        }

        drawerLayout.closeDrawers();

        return true;
    }

    private void handleBackPressed(){

        getOnBackPressedDispatcher()
                .addCallback(this,
                        new OnBackPressedCallback(true) {

                            @Override
                            public void handleOnBackPressed(){

                                if(drawerLayout.isDrawerOpen(navigationView)){

                                    drawerLayout.closeDrawer(navigationView);

                                }else{

                                    finish();

                                }
                            }
                        });
    }
}