package com.example.marcin.andomondo.geolocation;

import android.icu.text.DecimalFormat;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.marcin.andomondo.Fragments.MyFacebookFragment;
import com.example.marcin.andomondo.Fragments.MyMapFragment;
import com.example.marcin.andomondo.Main;

/**
 * Created by Marcin on 5/12/2017.

 */
public class Calculator {
    private Location location1, location2;
    private float distance = 0;
    private long time;
    private long location1time=0;
    private long location2time=0;

    int weight=80;

    private int calories=0;

    private int distance_in_km = 0;

    public int getCalories(){
        return calories;
    }

    private void caloriesCalculator(float shift, double speed){
        calories+=(int)((shift/1000)*weight);
        Main.calories = calories;
    }

    private void addLocation(Location location) {
        if (location1 == null){
            location1 = location;
            location1time = System.currentTimeMillis();
            time = System.currentTimeMillis();
        }
        else {
            location2 = location1;
            location2time = location1time;
            location1time = System.currentTimeMillis();
            location1 = location;
        }
    }

    public float distance(Location location) {
        addLocation(location);
        float shift = shift();
        distance += shift;
        caloriesCalculator(shift,speed());
        return distance;
    }

    public double avg_speed(){
        return speedCalculator(time, System.currentTimeMillis(), distance);
    }

    public double speed(){
        return speedCalculator(location2time, location1time, shift());
    }

    private double speedCalculator(long time1, long time2, float distance){
        double speed = 3.6*distance/(double)((time2-time1)/1000);
        if(speed<45)
            return speed;
        else
            return 45;
    }

    private float shift() {
        float shift = 0;
        if (location1 != null && location2 != null) {
            shift = location1.distanceTo(location2);
        }
        return shift;
    }

    public Integer getDistance_in_km(){
        return distance_in_km;
    }

    public Integer incteaseDistance_in_km(){
        return ++distance_in_km;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String time(){
        DecimalFormat df = new DecimalFormat("00");
        return String.valueOf(df.format(minutes())+ ":"+df.format(seconds()));
    }

    public Integer minutes(){
        return (int) ((location1time - time)/60000);
    }

    public Integer seconds(){
        return (int) ((location1time - time)/1000)%60;
    }

}
