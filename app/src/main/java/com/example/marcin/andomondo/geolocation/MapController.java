package com.example.marcin.andomondo.geolocation;

import android.icu.text.DecimalFormat;
import android.location.Location;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;

import com.example.marcin.andomondo.Main;
import com.example.marcin.andomondo.R;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class MapController {

    private Location location1, location2;
    private Calculator distanceCalculator;
    private DataPoint[] heightPoints;
    private DataPoint[] speedPoints;

    private TextToSpeech textToSpeech;

    public MapController() {
        distanceCalculator = new Calculator();
        heightPoints = new DataPoint[0];
        speedPoints = new DataPoint[0];

        textToSpeech = new TextToSpeech(Main.context,new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status) {
            }
        });

    }

    private void addLocation(Location location) {
        if (location1 == null){

            location1 = location;

            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            Main.googleMap.addMarker(new MarkerOptions().position(latLng).title("Start"));

        }
        else {

            location2 = location1;
            location1 = location;

            LatLng latLng1 = new LatLng(location1.getLatitude(),location1.getLongitude());
            LatLng latLng2 = new LatLng(location2.getLatitude(),location2.getLongitude());
            Main.googleMap.addPolyline(new PolylineOptions().add(latLng1).add(latLng2).width(5).color(Main.context.getResources().getColor(R.color.lines)));

        }

        Main.googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
        Main.googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(Location location){
        addLocation(location);
        float distance = distanceCalculator.distance(location)/1000;

        updateContent(distanceCalculator.minutes()*60+distanceCalculator.seconds(),distance, (float) distanceCalculator.avg_speed());

        if((int)distance>distanceCalculator.getDistance_in_km()){
            speaker();
        }

        DecimalFormat df = new DecimalFormat("#0.0");

        if(distance>0) {


            int max = (int) (distance * 5 + 1);

            Main.height_graph.getViewport().setXAxisBoundsManual(true);
            Main.height_graph.getViewport().setMinX(0);
            Main.height_graph.getViewport().setMaxX(max * 0.2);

            Main.speed_graph.getViewport().setXAxisBoundsManual(true);
            Main.speed_graph.getViewport().setMinX(0);
            Main.speed_graph.getViewport().setMaxX((float) max * 0.2);

        }
        if(distance==0) {
            addSpeed(new DataPoint(distance, 0));
        }
        else {
            addSpeed(new DataPoint(distance, distanceCalculator.speed()));
            Main.avg_spd.setText(String.valueOf(df.format(distanceCalculator.avg_speed()))+ " km/h");
            Main.time.setText(distanceCalculator.time());
        }
        Main.distance.setText(String.valueOf(df.format(distance)) + " km");

        Main.kalc.setText(distanceCalculator.getCalories()+" kalc");

        Main.allCalories.setText((Main.allTimeCalories+distanceCalculator.getCalories())+ " kalc");

        addHeight(new DataPoint(distance, location.getAltitude()));

    }

    private void addHeight(DataPoint dataPoint){
        DataPoint[]  dataPoints = new DataPoint[heightPoints.length+1];
        System.arraycopy(heightPoints, 0, dataPoints, 0, heightPoints.length);
        dataPoints[heightPoints.length]=dataPoint;

        heightPoints = dataPoints;
        Main.height_graph.removeAllSeries();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(heightPoints);
        series.setColor(Main.context.getResources().getColor(R.color.lines));
        Main.height_graph.addSeries(series);

    }

    private void addSpeed(DataPoint dataPoint){
        DataPoint[] dataPoints = new DataPoint[speedPoints.length+1];
        System.arraycopy(speedPoints, 0, dataPoints, 0, speedPoints.length);
        dataPoints[speedPoints.length]=dataPoint;

        speedPoints = dataPoints;
        Main.speed_graph.removeAllSeries();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(speedPoints);
        series.setColor(Main.context.getResources().getColor(R.color.lines));
        Main.speed_graph.addSeries(series);
    }

    private void speaker(){

        String speach = "Run "+distanceCalculator.incteaseDistance_in_km()+" kilometers in "+distanceCalculator.minutes()+" minutes and "+distanceCalculator.seconds()+" seconds";

        textToSpeech.speak(speach, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void updateContent(int duration, float distance, float speed){

        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type", "fitness.course")
                .putString("og:title", "Runing")
                .putString("og:description", "This is my run using Andomondo")
                .putInt("fitness:duration:value", duration)
                .putString("fitness:duration:units", "s")
                .putInt("fitness:distance:value", (int) distance)
                .putString("fitness:distance:units", "km")
                .putInt("fitness:speed:value", (int) (speed/3.6))
                .putString("fitness:speed:units", "m/s")
                .build();
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("fitness.runs")
                .putObject("fitness:course", object)
                .build();
        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("fitness:course")
                .setAction(action)
                .build();

        Main.facebookButton.setShareContent(content);

    }

}
