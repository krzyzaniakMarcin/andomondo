package com.example.marcin.andomondo.geolocation;

import android.location.Location;

/**
 * Created by Marcin on 5/12/2017.
 */

public class DistanceCalculator {
    private Location location1, location2;
    private float distance = 0;
    private long time = 0;

    private void addLocation(Location location) {
        if (location1 == null){
            location1 = location;
            time = System.currentTimeMillis();
        }
        else {
            location2 = location1;
            location1 = location;
        }
    }

    public float distance(Location location) {
        addLocation(location);
        distance += shift();
        return distance;
    }

    public double avgSpeed(){
        return 3.6*distance/((double) ((System.currentTimeMillis()-time)*1000));
    }


    private float shift() {
        float shift = 0;
        if (location1 != null && location2 != null) {
            shift = location1.distanceTo(location2);
        }
        return shift;
    }

}
