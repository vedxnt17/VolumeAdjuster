package com.example.zaap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static com.example.zaap.MainActivity.clickSoundPlay;

public class existing extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Spinner spinner;
    ArrayList<String> indexesList = new ArrayList<>();
    ArrayList<String> displayNum = new ArrayList<>();
    ArrayList<String> indexNum = new ArrayList<>();
    ArrayList<View> textViewList = new ArrayList<>();

    public void Back(View view) {
        Intent intent = new Intent(getApplicationContext(), selectionAct.class);
        clickSoundPlay(this);
        startActivity(intent);
    }

    public void DeleteEntry(View view)
    {
        clickSoundPlay(this);
        String DeleteDisplay = (String) spinner.getSelectedItem();
        int ind = 69;
        for(int i=0; i < displayNum.size(); i++)
        {
            if(DeleteDisplay == displayNum.get(i))
            {
                ind = i;
            }
        }
        Log.i("Delete Index",""+ind + " Its value:"+ indexNum.get(ind));
        if(ind != 69)
        {
            String DeleteIndex = indexNum.get(ind);
            sharedPreferences.edit().remove(DeleteIndex+",latitude").apply();
            sharedPreferences.edit().remove(DeleteIndex+",longitude").apply();
            sharedPreferences.edit().remove(DeleteIndex+",volume").apply();
            LinearLayout linearLayout = findViewById(R.id.Actual_data_keeper);
            linearLayout.removeView(textViewList.get(ind));
            linearLayout.removeView(textViewList.get(ind));
            textViewList.remove(ind);
            Button bt = findViewById(R.id.button6);
            spinner.setAdapter(null);
            displayNum.clear();
            indexesList.clear();
            bt.performClick();
            updateSpinner();
        }
        else
        {
            Toast.makeText(this, "existing, Delete index not valid", Toast.LENGTH_LONG).show();
        }
    }


    public void DeleteDisplay(View view) {
        clickSoundPlay(this);
        if(!indexesList.isEmpty())
        {
            spinner.setEnabled(true);
            Button want = findViewById(R.id.button10);
            TextView selectText = findViewById(R.id.textView8);
            Button deleteButton = findViewById(R.id.button9);
            deleteButton.animate().alpha(1f).setDuration(2000);
            want.animate().alpha(0f).setDuration(2000);
            selectText.animate().alpha(1f).setDuration(2000);
            spinner.animate().alpha(1f).setDuration(2000);

            displayNum = new ArrayList<>();
            indexNum = new ArrayList<>();
            updateSpinner();

        }
        else
        {
            Toast.makeText(this, "Please take a look at entries before deleting them.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSpinner()
    {
        int a = 0;
        for(int i = 0; i < indexesList.size(); i++)
        {
            String abcd = indexesList.get(i);
            Log.i("IndexesList",""+indexesList.get(i));
            String[] arr = abcd.split(",");
            if(arr.length > 0)
            {
                displayNum.add(a,arr[0]);
                indexNum.add(a,arr[1]);
                a++;
            }
            else
            {
                Log.i("Error","In exisitng java at line 62");
            }
        }
        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, displayNum);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(SpinnerAdapter);
    }

    @SuppressLint("ResourceType")
    public void displayData(View view)
    {
        clickSoundPlay(this);
        Button button = findViewById(R.id.button6);
        LinearLayout dataKeeper = findViewById(R.id.Actual_data_keeper);
        int index;
        int maxInd = sharedPreferences.getInt("Index", 0);
        if (maxInd == 0)
        {
            Log.i("Index", "zero hai");
            return;
        }
        else
            {

            int displayNum = 1;
            String latitudeST = null;
            String longitudeST = null;
            int volumeST;
            if(textViewList.size() == 0)
            {
                for (index = 1; index <= maxInd; index++)
                {
                    latitudeST = sharedPreferences.getString(index + ",latitude", null);
                    longitudeST = sharedPreferences.getString(index + ",longitude", null);
                    volumeST = sharedPreferences.getInt(index + ",volume", 40);
                    if (latitudeST != null && longitudeST != null && volumeST != 40) {
                        indexesList.add(displayNum - 1, displayNum + "," + index);
                        TextView data = new TextView(this);
                        data.setTextColor(Color.LTGRAY);
                        data.setTextSize(15f);
                        data.setId(displayNum);
                        data.setText("  " + displayNum + ")    Latitude: " + latitudeST + "\n         Longitude: " + longitudeST + "\n         Volume: " + volumeST);
                        int height = data.getHeight();
                        data.setHeight(height+300);
                        dataKeeper.addView(data);
                        textViewList.add(displayNum - 1, data);
                        displayNum += 1;
                        button.setText("     Refresh     ");
                    } else {
                        Log.i("error", "Obtained params are not valid");
                    }
                }
            }
            else
            {

                textViewList.clear();
                dataKeeper.removeAllViews();
                button.performClick();
            }
            }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing);
        Intent ine = getIntent();
        spinner = findViewById(R.id.spinner);
        spinner.setEnabled(false);
        sharedPreferences = this.getSharedPreferences("com.example.zaap", MODE_PRIVATE);

    }

    public void toAdd(View view)
    {
        clickSoundPlay(this);
        Intent intent = new Intent(getApplicationContext(), add.class);
        startActivity(intent);
    }

    public  void yahiHoExisting(View v)
    {
        clickSoundPlay(this);
        Toast.makeText(this, "You are already in existing Layout", Toast.LENGTH_SHORT).show();
    }
    public  void toSettings(View v)
    {
        clickSoundPlay(this);
        Intent intent = new Intent(getApplicationContext(), settings.class);
        startActivity(intent);
    }
}
