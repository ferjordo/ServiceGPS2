package com.example.estudiante.gps;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by Carlos on 18/08/2016.
 */
public class Ubicacion implements LocationListener {
    private Context ctx;

    LocationManager locationManager;
    String proveedor;
    private boolean networkOn;


    public Ubicacion(Context ctx) {
        this.ctx = ctx;
        locationManager= (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        proveedor= locationManager.NETWORK_PROVIDER;
        networkOn = locationManager.isProviderEnabled(proveedor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                return;
            } else {
                locationManager.requestLocationUpdates(proveedor, 10000,1, this);
            }
        } else {
            locationManager.requestLocationUpdates(proveedor, 10000,1, this);
        }
        locationManager.requestLocationUpdates(proveedor, 10000,1, this);
        getLocation();
    }

    private void getLocation(){
        if(networkOn){
            if (ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                Location lo = locationManager.getLastKnownLocation(proveedor);

                if(lo != null){
                    StringBuilder builder = new StringBuilder();
                    builder.append("Longitud :").append(lo.getLongitude())
                            .append("Latitud :").append(lo.getLatitude());

                    Toast.makeText(ctx, builder.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }

    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

        getLocation();
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}