package com.example.zaap;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.effect.Effect;
import android.media.effect.EffectFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static MediaPlayer mediaPlayer;
    public static AudioManager audioManager;
    public static int clickSound = 1;

    public static void clickSoundPlay(Context context)
    {
        if(clickSound == 1)
        {
            mediaPlayer = MediaPlayer.create(context ,R.raw.new_act);
            mediaPlayer.start();
        }
    }

    public void ToIntro(View view)
   {
       Intent in = new Intent(getApplicationContext(),selectionAct.class);
       startActivity(in);
   }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.start_sound);
        assert audioManager != null;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),1);
        mediaPlayer.start();
    }
}
