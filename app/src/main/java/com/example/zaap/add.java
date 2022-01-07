package com.example.zaap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.zaap.MainActivity.clickSoundPlay;

public class add extends AppCompatActivity {

    SeekBar seekBar;
    AudioManager audioManager;
    int volume;
    Double latitude;
    Double longitude;


    public void Back(View v)
    {
        Intent ina = new Intent(getApplicationContext(),selectionAct.class);
        clickSoundPlay(this);
        startActivity(ina);
    }

    public void openMap(View view)
    {
        Intent map = new Intent(getApplicationContext(),MapsActivity.class);
        clickSoundPlay(this);
        startActivity(map);
    }

    public void Assign(View view)
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.zaap",MODE_PRIVATE);
        int abc = sharedPreferences.getInt("Index",0);
        if(abc == 0)
        {

            sharedPreferences.edit().putInt("Index",1).apply();
            sharedPreferences.edit().putString("1,latitude",latitude.toString()).apply();
            sharedPreferences.edit().putString("1,longitude",longitude.toString()).apply();
            sharedPreferences.edit().putInt("1,volume",volume).apply();

        }
        else
        {

            int index = abc+1;
            sharedPreferences.edit().putString(index+",latitude",latitude.toString()).apply();
            sharedPreferences.edit().putString(index+",longitude",longitude.toString()).apply();
            sharedPreferences.edit().putInt(index+",volume",volume).apply();
            sharedPreferences.edit().putInt("Index",index).apply();

        }
        Intent menu = new Intent(getApplicationContext(),selectionAct.class);
        clickSoundPlay(this);
        startActivity(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
        seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_RING));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if(fromUser)
                {
                    volume = progress;
                    Log.i("Volume",""+volume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        TextView textView = findViewById(R.id.textView3);
        Intent ine = getIntent();
        latitude = ine.getDoubleExtra("Latitude",0);
        longitude = ine.getDoubleExtra("Longitude",0);
        textView.setText("Your selected location has latitude of "+latitude+" and longitude "+longitude);
    }


    public void yahiHoAdd(View V)
    {
        clickSoundPlay(this);
        Toast.makeText(this, "You are already in adding layout", Toast.LENGTH_SHORT).show();
    }

    public void toExisting(View v)
    {
        clickSoundPlay(this);
        Intent in = new Intent(getApplicationContext(), existing.class);
        startActivity(in);
    }

    public void toSettings(View v)
    {
        clickSoundPlay(this);
        Intent i = new Intent(getApplicationContext(), settings.class);
        startActivity(i);
    }
}
