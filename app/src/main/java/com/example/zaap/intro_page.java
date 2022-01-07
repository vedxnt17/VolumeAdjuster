package com.example.zaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import static com.example.zaap.MainActivity.clickSoundPlay;

public class intro_page extends AppCompatActivity
{
    public void ToExisting(View v)
    {
        clickSoundPlay(this);
        Intent intent =  new Intent(getApplicationContext(), existing.class);
        startActivity(intent);
    }

    public void toSettings(View v)
    {
        clickSoundPlay(this);
        Intent intent = new Intent(getApplicationContext(), settings.class);
        startActivity(intent);
    }

    public void ToAdd(View v)
    {
        clickSoundPlay(this);
        Intent intent = new Intent(getApplicationContext(), add.class);
        startActivity(intent);
    }

    public void next(View view)
    {
        clickSoundPlay(this);
        Handler handler = new Handler();
        final Intent a = new Intent(getApplicationContext(),selectionAct.class);
        ConstraintLayout constraintLayout = findViewById(R.id.aha);
        constraintLayout.animate().alpha(0f).setDuration(2000);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(a);
            }
        };
        handler.postDelayed(runnable,1000);
    }

    public void Back(View vc)
    {
        clickSoundPlay(this);
        Intent in = new Intent(getApplicationContext(),selectionAct.class);
        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
        Intent in = getIntent();
    }
}
