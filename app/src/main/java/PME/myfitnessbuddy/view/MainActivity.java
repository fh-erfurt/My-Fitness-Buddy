package PME.myfitnessbuddy.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.myfitnessbuddy.R;

import com.google.android.material.navigation.NavigationView;

import PME.myfitnessbuddy.storage.MyFitnessBuddyDatabase;

import PME.myfitnessbuddy.storage.PersonRepository;
import PME.myfitnessbuddy.view.ui.core.Constants;


/**
 * MainActivity for MyFitnessBuddy
 * */

public class MainActivity extends AppCompatActivity  {
    private Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private NavController navController;

public static RoomDatabase.Builder<MyFitnessBuddyDatabase> database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs=getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart=prefs.getBoolean("firstStart",true);
        if (firstStart){
            showFirstAppStartFragment();
        }


        database= Room.databaseBuilder(getApplicationContext(),MyFitnessBuddyDatabase.class,"user").allowMainThreadQueries();
        PersonRepository personRepository = PersonRepository.getRepository(getApplication());
        personRepository.getLastPerson();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.fragment_traininglist,R.id.fragment_exercise,R.id.fragment_profile, R.id.firstStartActivity)
                .setDrawerLayout(drawerLayout)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkModeEnabled=sharedPreferences.getBoolean(Constants.PREF_DARK_MODE,false);
        AppCompatDelegate.setDefaultNightMode(
                darkModeEnabled?AppCompatDelegate.MODE_NIGHT_YES:AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    // initial first start if app never used before
    private void showFirstAppStartFragment(){
        Intent intent = new Intent(this, FirstStartActivity.class);
        startActivity(intent);
        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }

}