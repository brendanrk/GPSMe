package com.bkapps.gpsme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textGPS;
    private Button getCoords;
    private Boolean isGettingGPS;
    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textGPS = (TextView) findViewById(R.id.textGPS);
        getCoords = (Button) findViewById(R.id.getCoords);
        isGettingGPS = false;

        Boolean isGPSEnabled =  checkTheSettingForGPS();
        if(!isGPSEnabled) {
            TurnOnToast("please turn on GPS in Locations");
        }

        getCoords.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (checkTheSettingForGPS()) {
                    if (!isGettingGPS) {
                        String locationProvider = LocationManager.NETWORK_PROVIDER;
                        // Or use LocationManager.GPS_PROVIDER

                        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                        textGPS.setText(lastKnownLocation.toString());
                        startLocationCalls();
                        getCoords.setText("running...Press here to Stop");
                        isGettingGPS = true;

                    } else {
                        stopLocationCalls();
                        getCoords.setText("Press here for GPS");
                        isGettingGPS = false;
                    }
                } else {
                    TurnOnToast("please turn on GPS in Locations");
                }
            }
        });

    }

    private void TurnOnToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    private boolean checkTheSettingForGPS() {
        LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}


        return (gps_enabled && network_enabled);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        TurnOnToast("onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        TurnOnToast("onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        TurnOnToast("onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        TurnOnToast("onDestroy");
    }




    protected void startLocationCalls() {

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
//                makeUseOfNewLocation(location);
                //textGPS.setText(location.toString());
                textGPS.setText("Lat  :"+String.format("%f", location.getLatitude())
                                +"\n"+"Long :"+String.format("%f", location.getLongitude())
                                +"\n"+"Speed:"+String.format("%f",location.getSpeed())
                );

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

///        String locationProvider = LocationManager.NETWORK_PROVIDER;
// Or, use GPS location data:
// String locationProvider = LocationManager.GPS_PROVIDER;
//        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);

//        String locationProvider = LocationManager.NETWORK_PROVIDER;
// Or use LocationManager.GPS_PROVIDER

//        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);


// Remove the listener you previously added
//        locationManager.removeUpdates(locationListener);


    }


    protected void stopLocationCalls() {

        locationManager.removeUpdates(locationListener);


    }
}
