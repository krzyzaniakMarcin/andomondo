package com.example.marcin.andomondo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.marcin.andomondo.Fragments.MyAdapter;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.maps.GoogleMap;
import com.jjoe64.graphview.GraphView;

public class Main extends FragmentActivity{

    public static Activity activity;
    public static Context context;

    public static GoogleMap googleMap;

    public static GraphView height_graph;
    public static GraphView speed_graph;

    public static TextView distance;
    public static TextView avg_spd;
    public static TextView time;
    public static TextView kalc;

    public static TextView allCalories;

    public static int allTimeCalories=0;
    public static int calories = 0;

    public static ShareButton facebookButton;

    SharedPreferences preferences;

    MyAdapter mAdapter;

    ViewPager mPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        allTimeCalories = preferences.getInt("CALORIES",0);


        mAdapter = new MyAdapter(getSupportFragmentManager());

        distance = (TextView) findViewById(R.id.distance);
        avg_spd = (TextView) findViewById(R.id.avg_spd);
        time = (TextView) findViewById(R.id.time);
        kalc = (TextView) findViewById(R.id.kalc);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mPager.setCurrentItem(1);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        context = this;
        activity = this;


    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putInt("CALORIES", allTimeCalories+calories);
        preferencesEditor.commit();

    }

}
