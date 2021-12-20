package com.example.swdevclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.swdevclass.fitness.FitnessCenter;

import java.util.ArrayList;

public class TitleActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        DBControl dBControl = new DBControl();
        dBControl.setArrayListtoDB();
        dBControl.setLoginReftoDB();

        ArrayList<FitnessCenter> fitnessCenterArrayList = dBControl.getArrayList();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TitleActivity.this, MainActivity.class);
                intent.putExtra("Object", fitnessCenterArrayList);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
