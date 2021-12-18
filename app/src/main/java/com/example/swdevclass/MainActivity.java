package com.example.swdevclass;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.swdevclass.FragmentFile.Fragment_Map;
import com.example.swdevclass.FragmentFile.Fragment_List;
import com.example.swdevclass.FragmentFile.Fragment_Login;
import com.example.swdevclass.fitness.FitnessCenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    //bottom Navigation view
    public FitnessArrayListControl fitnessArrayListControl;
    BottomNavigationView bottomNavigationView;
    private long lastTimeBackPressed; //onbackpressed와 관련
    private Thread splashThread;
    public static Stack<Fragment> fragmentStack;
    public static FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fragment 관련

        fragmentStack = new Stack<>();
        fragmentManager = getSupportFragmentManager();

        //db관련
        fitnessArrayListControl = new FitnessArrayListControl();
        if(!fitnessArrayListControl.setArrayListtoDB()){
            Toast.makeText(getApplicationContext(), "Data failed", Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().replace(R.id.layout_main, new Fragment_Map()).commit();
        }

        fitnessArrayListControl.setLoginReftoDB();

        //FrameLayout에 fragment.xml 띄우기

        bottomNavigationView = findViewById(R.id.bottomNavi);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_fragment1:
                        fragmentStack.clear();
                        fragmentManager.beginTransaction().replace(R.id.layout_main, new Fragment_Map()).commit();
                        break;
                    case R.id.item_fragment2:
                        fragmentStack.clear();
                        fragmentManager.beginTransaction().replace(R.id.layout_main, new Fragment_List()).commit();
                        break;
                    case R.id.item_fragment3:
                        fragmentStack.clear();
                        fragmentManager.beginTransaction().replace(R.id.layout_main, new Fragment_Login()).commit();
                        break;
                }
                return true;
            }
        });

    }
    //프래그먼트 전환//fragmentmanager 중복됨
    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_main, fragment).commit();
    }
    @Override
    public void onBackPressed(){
        if(!fragmentStack.isEmpty()){
            Fragment nextFragment = fragmentStack.pop();
            fragmentManager.beginTransaction().replace(R.id.layout_main, nextFragment).commit();
        }
        else{
            super.onBackPressed();
        }
    }
}