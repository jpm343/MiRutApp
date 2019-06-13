package com.example.mirutapp;

import android.net.Uri;
import android.os.Bundle;

import com.example.mirutapp.Fragment.InfoPatenteFragment;

import com.example.mirutapp.Fragment.MapsFragment;
import com.example.mirutapp.Fragment.NewsFragment;
import com.example.mirutapp.Fragment.RouteFragment;
import com.example.mirutapp.Fragment.VehicleFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        InfoPatenteFragment.OnFragmentInteractionListener,
        NewsFragment.OnListFragmentInteractionListener,
        RouteFragment.OnListFragmentInteractionListener,
        VehicleFragment.OnFragmentInteractionListener,
        MapsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //verify if the app is launched from a notification
        String comesFromNotification = getIntent().getStringExtra("comesFromNotification");
        if(comesFromNotification != null) {
            if(comesFromNotification.equals("postFragment")) {
                NewsFragment fragment = new NewsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
            }
            else if(comesFromNotification.equals("vehiclesFragment")) {
                VehicleFragment fragment = new VehicleFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
            }
        }


        //subscribe app to NEWS fireBase topic
        FirebaseMessaging.getInstance().subscribeToTopic("NEWS");

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        System.out.println(token);
                    }
                });


        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new MapsFragment() ).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment selectedFragment = null;
        boolean fragmentIsSelected = false;



        if (id == R.id.nav_home) {
            // Handle the camera action
            selectedFragment = new InfoPatenteFragment();
            fragmentIsSelected = true;
        } else if (id == R.id.nav_gallery) {
            selectedFragment = new NewsFragment();
            fragmentIsSelected = true;

        } else if (id == R.id.nav_slideshow) {
            selectedFragment = new VehicleFragment();
            fragmentIsSelected = true;

        } else if (id == R.id.nav_tools) {
            selectedFragment = new MapsFragment();
            fragmentIsSelected = true;

        } else if(id==R.id.nav_routes){
            selectedFragment = new RouteFragment();
            fragmentIsSelected = true;

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if(fragmentIsSelected) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, selectedFragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onListFragmentInteraction(Uri uri) {

    }

}
