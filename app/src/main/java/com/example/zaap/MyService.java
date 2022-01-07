package com.example.zaap;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {
    public int start = 0;
    public static int appWork = 1;
    public static int openSettings = 1;
    public static final int NOTIF_ID = 1;
    public static final String NOTIF_CHANNEL_ID = "Channel_Id";



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Log.i("Running","In Background");
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                CheckLocation();

                Thread thread = new Thread(CheckLocation());

                thread.start();
            }
        };

        timer.scheduleAtFixedRate(timerTask,0,10000);

        startForeground();

        return START_NOT_STICKY;
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_check_black_24dp)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("ZaaP is Working in background")
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setContentIntent(pendingIntent)
                .build());
    }


    private void makeNotification(String content) {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_check_black_24dp)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(content)
                .setPriority(NotificationManager.IMPORTANCE_LOW)
                .setContentIntent(pendingIntent)
                .build());
    }

    Runnable CheckLocation()
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                Log.i("Updating Location in bg","using runnable");

                if(appWork == 1)
                {
                    Log.i("Get existing locations","start");

                    ArrayList<LatLng> locationList = new ArrayList<>();
                    ArrayList<Integer> volumeList = new ArrayList<>();
                    SharedPreferences sharedPreferences = getSharedPreferences("com.example.zaap", MODE_PRIVATE);
                    int maxInd = sharedPreferences.getInt("Index", 0);
                    if (maxInd == 0)
                    {
                        Log.i("Index", "zero hai");
                    }
                    else
                    {
                        int sudoIndex = 0;
                        String latitudeST = null;
                        String longitudeST = null;
                        int volume;

                        for (int index = 1; index <= maxInd; index++)
                        {
                            latitudeST = sharedPreferences.getString(index + ",latitude", null);
                            longitudeST = sharedPreferences.getString(index + ",longitude", null);
                            volume = sharedPreferences.getInt(index + ",volume", 40);
                            if (latitudeST != null && longitudeST != null && volume != 40)
                            {
                                LatLng give = new LatLng(Double.parseDouble(latitudeST),Double.parseDouble(longitudeST));
                                volumeList.add(sudoIndex, volume);
                                locationList.add(sudoIndex, give);
                                sudoIndex += 1;
                            }
                            else {
                                Log.i("error", "Obtained params are not valid");
                            }
                        }

                    }

                    Log.i("Got location list", "starting lastLocation check");

                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    LatLng latLng = null;
                    Location lastLocation = null;
                    if(locationManager != null)
                    {
                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                        {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                            {
                                List<String> providers = locationManager.getProviders(true);
                                for (String provider : providers)
                                {
                                    lastLocation = locationManager.getLastKnownLocation(provider);
                                    if (lastLocation != null)
                                    {
                                        Log.i("Not null bg", "last location ");
                                        break;
                                    }
                                }
                                if (lastLocation != null)
                                {
                                   latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                                }
                                else {
                                    Log.i("LastLocation bg thread", "Null last location");
                                }

                            }
                            else
                                {
                                Log.i("No permission bg thread", "perm denied");
                            }
                        }
                        else
                            {
                            if(openSettings == 1)
                            {
                                Intent settingLocation = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                settingLocation.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplication().startActivity(settingLocation);
                            }
                            else
                            {
                                Log.i("No location enabled","not allowed to open settings");
                            }
                        }

                    }

                    Log.i("LastLocation completed","Starting compare");

                    for(LatLng latLng1 : locationList)
                    {
                        if(latLng != null)
                        {
                            if (latLng1 == latLng)
                            {
                                int ind = locationList.indexOf(latLng);
                                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                                if(audioManager != null)
                                {
                                    audioManager.setStreamVolume(AudioManager.STREAM_RING, volumeList.get(ind),1);
                                    int percen = (volumeList.get(ind) / audioManager.getStreamMaxVolume(AudioManager.STREAM_RING))*100;
                                    makeNotification("Volume Changed to "+percen);
                                    Log.i("Volume changed!",""+volumeList.get(ind));
                                }
                                else
                                {
                                    Log.i("Audiomng bg", "Null");
                                }
                            }
                        }
                    }

                    Log.i("Comparision Done","End of bg thread");
                }
                else
                {
                    Log.i("App not","In working state");
                    Log.i("appWork value", ""+appWork);
                    appWork = 1;
                }
            }
        };
        return runnable;
    }
}
