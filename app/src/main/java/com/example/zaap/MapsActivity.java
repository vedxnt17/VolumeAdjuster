package com.example.zaap;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.Transliterator;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Provider;
import java.util.List;

import static com.example.zaap.MainActivity.clickSoundPlay;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    double latitude,longitude;

    public void sendLocation(View view)
    {
        clickSoundPlay(this);
        Intent add = new Intent(getApplicationContext(), com.example.zaap.add.class);
        add.putExtra("Latitude",latitude);
        add.putExtra("Longitude",longitude);
        startActivity(add);
    }


    public void MyLocation(View view)
    {
        clickSoundPlay(this);
        Toast.makeText(this, "Long press on the marker ko drag it", Toast.LENGTH_LONG).show();
        Location lastLocation = null;


        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                List<String> providers = locationManager.getProviders(true);
                for(String provider : providers)
                {
                    lastLocation = locationManager.getLastKnownLocation(provider);
                    if(lastLocation != null)
                    {
                        Log.i("Not null", "last location ");
                        break;
                    }
                }
                if(lastLocation != null)
                {

                    latitude = lastLocation.getLatitude();
                    longitude = lastLocation.getLongitude();
                    mMap.clear();
                    LatLng latLng = new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Your location").draggable(true));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17f));

                }
                else
                {
                    Log.i("Error","Null last location");
                }

            }
            else {
                Log.i("error","perm denied");
            }
        }
        else
        {
            Intent settings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settings);
            Toast.makeText(this, "Please enable location service", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent in = getIntent();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            Log.i("App has location","permisiion");
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker)
            {
                latitude = marker.getPosition().latitude;
                longitude = marker.getPosition().longitude;
            }
        });
    }
}
