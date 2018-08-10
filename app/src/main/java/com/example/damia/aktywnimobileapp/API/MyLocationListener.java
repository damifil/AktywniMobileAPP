package com.example.damia.aktywnimobileapp.API;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;


public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location loc) {

        Log.i("hhhh","w listenerze");
        String longitude = "Longitude: " +loc.getLongitude();
        Log.i("HHHH", longitude);
        String latitude = "Latitude: " +loc.getLatitude();
        Log.i("HHHH", latitude);



    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider,
                                int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}
