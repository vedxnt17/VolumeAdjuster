package com.example.zaap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import static com.example.zaap.MainActivity.clickSound;
import static com.example.zaap.MainActivity.clickSoundPlay;
import static com.example.zaap.MyService.appWork;
import static com.example.zaap.MyService.openSettings;

public class settings extends AppCompatActivity {

    Switch sw;
    Switch open;
    Switch fourth;

    public void existing(View v)
    {
        clickSoundPlay(this);
        Intent intent = new Intent(getApplicationContext(), existing.class);
        startActivity(intent);
    }

    public void yahiHo(View v)
    {
        clickSoundPlay(this);
        Toast.makeText(this, "You are already in settings", Toast.LENGTH_SHORT).show();
    }

    public void toAdd(View v)
    {
        clickSoundPlay(this);
        Intent intent = new Intent(getApplicationContext(), add.class);
        startActivity(intent);
    }

    public void Back(View v)
    {
        clickSoundPlay(this);
        Intent intent = new Intent(getApplicationContext(),selectionAct.class);
        startActivity(intent);
    }

    public void Switch4(View v)
    {
        clickSoundPlay(this);
        if(fourth.isChecked())
        {
            clickSound = 1;
            fourth.setChecked(true);
            fourth.setText("Yes");
        }
        else
        {
            clickSound = 0;
            fourth.setChecked(false);
            fourth.setText("No");
        }
    }

    public void Switch(View v)
    {
        clickSoundPlay(this);

        if(sw.isChecked())
        {
            appWork = 1;
            sw.setChecked(true);
            sw.setText(sw.getTextOn());
        }
        else
        {
            appWork = 0;
            sw.setChecked(false);
            sw.setText(sw.getTextOff());
        }
    }
    public void Switch2(View v)
    {
        clickSoundPlay(this);

        if(open.isChecked())
        {
            openSettings = 1;
            open.setChecked(true);
            open.setText("Yes");
        }
        else
        {
            openSettings = 0;
            open.setChecked(false);
            open.setText("No");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent in = getIntent();
        sw = findViewById(R.id.switch1);
        open = findViewById(R.id.switch2);
        fourth = findViewById(R.id.switch4);
        if(appWork == 0)
        {
            sw.setChecked(false);
            sw.setText(sw.getTextOff());
        }
        if(appWork == 1)
        {
            sw.setChecked(true);
            sw.setText(sw.getTextOn());
        }
        if(openSettings == 1)
        {
            open.setChecked(true);
            open.setText("Yes");
        }
        if(openSettings == 0)
        {

            open.setChecked(false);
            open.setText("No");
        }
        if(clickSound == 1)
        {
            fourth.setEnabled(true);
            fourth.setText("Yes");
        }
        if(clickSound == 0)
        {
            fourth.setEnabled(false);
            fourth.setText("No");
        }
    }
}
