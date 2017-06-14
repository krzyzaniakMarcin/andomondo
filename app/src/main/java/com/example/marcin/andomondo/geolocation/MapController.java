package com.example.marcin.andomondo.geolocation;

import android.graphics.Color;
import android.location.Location;

import com.example.marcin.andomondo.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapController {

    private Location location1, location2;
    private DistanceCalculator distanceCalculator;

    public MapController() {
        distanceCalculator = new DistanceCalculator();
    }

    private void addLocation(Location location) {
        if (location1 == null){

            location1 = location;

            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            MapsActivity.mMap.addMarker(new MarkerOptions().position(latLng).title("Start"));

        }
        else {

            location2 = location1;
            location1 = location;

            LatLng latLng1 = new LatLng(location1.getLatitude(),location1.getLongitude());
            LatLng latLng2 = new LatLng(location2.getLatitude(),location2.getLongitude());
            MapsActivity.mMap.addPolyline(new PolylineOptions().add(latLng1).add(latLng2).width(5).color(Color.RED));

        }

        MapsActivity.mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
        MapsActivity.mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
    }

    public void update(Location location){
        addLocation(location);
        MapsActivity.distanceView.setText(Float.toString(distanceCalculator.distance(location))+" m");
        MapsActivity.speedView.setText(Double.toString(distanceCalculator.avgSpeed())+" km/h");
    }


}
