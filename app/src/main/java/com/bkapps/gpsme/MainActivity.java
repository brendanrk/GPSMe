package com.bkapps.gpsme;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textGPS;
    private Button getCoords;
    private Boolean isGettingGPS;
    // Acquire a reference to the system Location Manager
    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textGPS = (TextView) findViewById(R.id.textGPS);
        getCoords = (Button) findViewById(R.id.getCoords);
        isGettingGPS = false;

        getCoords.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (isGettingGPS) {
                    String locationProvider = LocationManager.NETWORK_PROVIDER;
                    // Or use LocationManager.GPS_PROVIDER

                    Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                    textGPS.setText(lastKnownLocation.toString());
                    startLocationCalls();
                    isGettingGPS = true;
                } else {
                    stopLocationCalls();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").

    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").

    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
//                makeUseOfNewLocation(location);
            textGPS.setText(location.toString());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };


    protected void startLocationCalls() {

        // Define a listener that responds to location updates


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
        locationManager.removeUpdates(locationListener);


    }
}
