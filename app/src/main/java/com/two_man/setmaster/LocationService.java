package com.two_man.setmaster;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * сервис для отслеживания местоположения
 */
public class LocationService extends Service {
    private static final String TAG = LocationService.class.getSimpleName();
    private LocationListener locationListener;
    private LocationManager locationManager;

    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initVar();
        initListeners();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        testStartObserveLocation();
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initListeners() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                writeTestData(location);
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
    }

    private void initVar() {
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    }

    private void testStartObserveLocation() {
        final int DELAY = 60*1000;
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                //LocationManager.GPS_PROVIDER,
                DELAY, 0,
                locationListener);
    }


    private void writeTestData(Location location) {
        BufferedWriter bw = null;
        try {
            final String FILENAME = "SetMasterTestData2";
            File sdPath = Environment.getExternalStorageDirectory();
            File sdFile = new File(sdPath, FILENAME);
            bw = new BufferedWriter(new FileWriter(sdFile, true));

            Time now = new Time();
            now.setToNow();
            bw.append("Time " + now.format("%d.%m.%Y %H.%M.%S") + "\n");
            bw.append(String.format("Lon: %f\nLat: %f\n\n", location.getLongitude(), location.getLatitude()));
            Log.d(TAG, "new Location  "+ now.format("%d.%m.%Y %H.%M.%S") + "\n"+ String.format("Lon: %f\nLat: %f\n\n", location.getLongitude(), location.getLatitude()));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
