package com.example.marcin.andomondo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.marcin.andomondo.Main;
import com.example.marcin.andomondo.R;
import com.example.marcin.andomondo.geolocation.MyLocationProvider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Marcin on 6/14/2017.
 */

public class MyMapFragment extends Fragment{

    private SupportMapFragment mSupportMapFragment;

    int mNum;

    private MyLocationProvider myLocationProvider;

    public static Fragment newInstance() {
        Fragment f = new MyMapFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment_layout, container, false);
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapwhere);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapwhere, mSupportMapFragment).commit();
        }
        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap gMap) {
                    if (gMap != null) {
                        Main.googleMap = gMap;
                        myLocationProvider = new MyLocationProvider();
                    }
                }
            });
        }
        return v;
    }

}
