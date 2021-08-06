package com.swagatsamal.swagatsamalassignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.swagatsamal.DbClasses.DbConfig;
import com.swagatsamal.DbClasses.StudentPOJO;
import com.swagatsamal.fragments.GradeEntryFragment;
import com.swagatsamal.fragments.SearchFragment;
import com.swagatsamal.fragments.ViewGradesFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Object declaration
    StudentPOJO studentPOJO;
    DbConfig dbConfig;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initiating all view variables
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.mainLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigationOpen, R.string.navigationClose);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //check state, refresh the frame only if the app starts for the first time.
        if(savedInstanceState == null) {
            //set the default page of app to view all grades.
            //default fragment is set to fragment_view_grade
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ViewGradesFragment()).commit();
        }

    }

    //Check for what navigation button was clicked and open the fragment accordingly.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.view_all: // If View Grades button was clicked
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ViewGradesFragment()).commit();
                break;
            case R.id.add_grade: // Grade Entry button was clicked
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new GradeEntryFragment()).commit();
                break;
            case R.id.search_grade: // If search button was clicked
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new SearchFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}