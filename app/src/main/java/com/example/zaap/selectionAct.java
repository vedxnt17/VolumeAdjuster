package com.example.zaap;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import android.app.Application;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import javax.xml.datatype.Duration;

import static com.example.zaap.MainActivity.clickSoundPlay;
import static com.example.zaap.MyService.appWork;

public class selectionAct extends AppCompatActivity {

    public void settings(View v)
    {
        clickSoundPlay(this);
        Intent intent = new Intent(getApplicationContext(), settings.class);
        startActivity(intent);
    }

    public void add(View view)
    {
        clickSoundPlay(this);
        Intent z = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(z);
    }

    public void About(View v)
    {
        clickSoundPlay(this);
        Intent intent = new Intent(getApplicationContext(),intro_page.class);
        startActivity(intent);
    }

    public void Status(View v)
    {
        clickSoundPlay(this);
        Toast.makeText(this, "Working of ZaaP can be adjusted in settings", Toast.LENGTH_LONG).show();
    }

    public void ToExisting(View view)
    {
        clickSoundPlay(this);
        Intent exist = new Intent(getApplicationContext(), existing.class);
        startActivity(exist);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        ImageView tick = findViewById(R.id.tick);
        if(appWork == 1)
        {
            tick.setEnabled(true);
            tick.setAlpha(1f);
        }
        else
        {
            tick.setEnabled(false);
            tick.setAlpha(0f);
        }
        Intent in = getIntent();
        ConstraintLayout constraint = findViewById(R.id.selection);
        constraint.animate().translationXBy(3000f);
        constraint.animate().translationXBy(0f).setDuration(0);
    }
}
