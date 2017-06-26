package com.example.marcin.andomondo.geolocation;

import android.Manifest;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.example.marcin.andomondo.Main;

/**
 * Created by Marcin on 5/12/2017.
 */

public class MyLocationProvider {

    Context context;
    Activity activity;
    LocationManager locationManager;
    LocationListener locationListener;

    public MyLocationProvider() {
        context = Main.context;
        activity = Main.activity;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            MapController mapController = new MapController();

            @Override
            public void onLocationChanged(Location location) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }

                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                mapController.update(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 10, locationListener);

    }
}
