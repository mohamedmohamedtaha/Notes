package com.mohamedtaha.imagine.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainDrawableActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private AdapterNotes adapterNotes;
    private AdapterCourses adapterCourses;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawable);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialazationDisplayContent();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainDrawableActivity.this, ContentNoteActivity.class));

            }
        });
        PreferenceManager.setDefaultValues(this,R.xml.sync_preferences,false);
        PreferenceManager.setDefaultValues(this,R.xml.messages_preferences,false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_header);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_drawer
                , R.string.close_drawer);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_notes, R.id.nav_courses, R.id.nav_slideshow)
//                .setDrawerLayout(drawer).build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }
    private void updateNavigationHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view_header);
        View headerView = navigationView.getHeaderView(0);
        TextView tv_user_name = headerView.findViewById(R.id.TV_user_name);
        TextView tv_email = headerView.findViewById(R.id.TV_email);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
       String user_name = preferences.getString("signature", "");
        String email = preferences.getString("your_mail","");
        tv_user_name.setText(user_name);
        tv_email.setText(email);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // noteInfoArrayAdapter.notifyDataSetChanged();
        adapterNotes.notifyDataSetChanged();
        updateNavigationHeader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawable, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainDrawableActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
   //     NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
     //   return NavigationUI.navigateUp(navController, mAppBarConfiguration)
       //         || super.onSupportNavigateUp();
        return true;
    }

    private void initialazationDisplayContent() {
        recyclerView = findViewById(R.id.LV_list);
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.grid_layout_manager_integer));


        List<NoteInfo> noteInfos = DataManager.getInstance().getNotes();
        adapterNotes = new AdapterNotes(this, noteInfos);
        List<CourseInfo> courseInfos = DataManager.getInstance().getCourses();
        adapterCourses = new AdapterCourses(this, courseInfos);

        displayNotes();

    }

    private void displayNotes() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterNotes);
        selectedNavigationMenu(R.id.nav_notes);
    }

    private void displayCourses() {
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterCourses);
        selectedNavigationMenu(R.id.nav_courses);
    }

    private void selectedNavigationMenu(int id) {
        NavigationView navigationView = findViewById(R.id.nav_view_header);
        Menu menu = navigationView.getMenu();
        menu.findItem(id).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_notes) {
            displayNotes();
        } else if (id == R.id.nav_courses) {
            displayCourses();
            //shareWebsite();
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareWebsite() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user_name = preferences.getString("reply", "");
        View view = findViewById(R.id.LV_list);
        Snackbar.make(view, "Share the website : " + user_name, Snackbar.LENGTH_LONG).show();
    }
}