package com.example.zaap;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;

import static com.example.zaap.MyService.NOTIF_CHANNEL_ID;

public class App extends Application{

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        if(Build.VERSION.SDK_INT >= 26) {
            startForegroundService(new Intent(this, MyService.class));
        }
        else
        {
            startService(new Intent(this,MyService.class));
        }
    }

    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
        {
            NotificationChannel  notificationChannel = new NotificationChannel(NOTIF_CHANNEL_ID, "Notification", NotificationManager
            .IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null)
            {
                notificationManager.createNotificationChannel(notificationChannel);
            }
            else {
                Log.i("NotificationManager","Not available");
            }
        }
    }

}