package com.example.fivelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottommenu;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Task.Deserialize(new File(super.getFilesDir(),"File.json"));
        bottommenu=findViewById(R.id.bottomNavigation);
        bottommenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.List:
                        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
                            FrameLayout frame = findViewById(R.id.ListContainer);
                            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(250,0,0);
                            frame.setLayoutParams(params);
                            FrameLayout secondFrame=findViewById(R.id.TaskContainer);
                            secondFrame.removeAllViews();
                            getSupportFragmentManager().beginTransaction().replace(R.id.ListContainer, new ListFragment()).commit();
                        }
                        else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.ListContainer, new ListFragment()).commit();
                        }
                        return true;
                    case R.id.Add:
                        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
                            FrameLayout frame=findViewById(R.id.ListContainer);
                            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,0,0);
                            frame.setLayoutParams(params);
                            Fragment frag=getSupportFragmentManager().findFragmentById(R.id.ListContainer);
                            getSupportFragmentManager().beginTransaction().remove(frag).commit();
                            getSupportFragmentManager().beginTransaction().replace(R.id.TaskContainer, new AddFragment()).commit();
                        }
                        else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.ListContainer, new AddFragment()).commit();
                        }
                        return true;
                }
                return false;
            }
        });
        bottommenu.setSelectedItemId(R.id.List);
        drawerLayout=findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav=findViewById(R.id.NavMenu);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ChangeTypeDrawer:{
                        RecyclerView view=findViewById(R.id.TaskList);
                        view.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                        Log.d("ExceptionLog", "onOptionsItemSelected: " + "asd");
                        return true;
                    }
                    case R.id.AddDrawer:{
                        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
                            FrameLayout frame=findViewById(R.id.ListContainer);
                            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,0,0);
                            frame.setLayoutParams(params);
                            Fragment frag=getSupportFragmentManager().findFragmentById(R.id.ListContainer);
                            getSupportFragmentManager().beginTransaction().remove(frag).commit();
                            getSupportFragmentManager().beginTransaction().replace(R.id.TaskContainer, new AddFragment()).commit();
                        }
                        else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.ListContainer, new AddFragment()).commit();
                        }
                        return true;
                    }
                    case R.id.ListDrawer:{
                        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
                            FrameLayout frame = findViewById(R.id.ListContainer);
                            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(250,0,0);
                            frame.setLayoutParams(params);
                            FrameLayout secondFrame=findViewById(R.id.TaskContainer);
                            secondFrame.removeAllViews();
                            getSupportFragmentManager().beginTransaction().replace(R.id.ListContainer, new ListFragment()).commit();
                        }
                        else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.ListContainer, new ListFragment()).commit();
                        }
                        return true;
                    }
                }
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}