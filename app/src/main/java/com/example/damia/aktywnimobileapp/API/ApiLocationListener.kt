package com.example.damia.aktywnimobileapp.API

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

import android.util.Log

internal class ApiLocationListener : LocationListener {

    override fun onLocationChanged(loc: Location) {

        val longitude = "Longitude: " + loc.longitude
        Log.v("HHH", longitude)
        val latitude = "Latitude: " + loc.latitude
        Log.v("HHH", latitude)


    }

    override fun onProviderDisabled(provider: String) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
}
