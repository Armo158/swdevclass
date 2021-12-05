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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //bottom Navigation view
    BottomNavigationView bottomNavigationView;
    //realtime database
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference managerRef;
    private ArrayList<FitnessCenter> arrayList; //db로부터 내용을 받아와서 넣는곳
    private long lastTimeBackPressed; //onbackpressed와 관련

    private Thread splashThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db관련
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("fitness"); //DB table
        managerRef = database.getReference("manger");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear();// 기존배열 리스트가 존재하지 않게 초기화
                for(DataSnapshot snapshot: datasnapshot.getChildren()) {//반복문으로 데이터 list를 추출해냄
                    FitnessCenter fitnessCenter = snapshot.getValue(FitnessCenter.class);//만들어뒀던 fitness 객체에 데이터 넣
                    arrayList.add(fitnessCenter); //담은 데이터들을 배열리스트에 넣음
                }
                getSupportFragmentManager().beginTransaction().add(R.id.layout_main, new Fragment_Map()).commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Data failed", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().add(R.id.layout_main, new Fragment_Map()).commit();
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavi); //네비게이션

        //FrameLayout에 fragment.xml 띄우기

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_fragment1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, new Fragment_Map()).commit();
                        break;
                    case R.id.item_fragment2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, new Fragment_List()).commit();
                        break;
                    case R.id.item_fragment3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, new Fragment_Login()).commit();
                        break;
                }
                return true;
            }
        });

    }
    //프래그먼트 전환
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_main, fragment).commit();
    }/*
    public interface OnBackPressedListener{
        void onBackPressed();
    }

    long backKeyPressedTime;

    public void onBackPressed(){
        if(System.currentTimeMillis() >backKeyPressedTime+2000){
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "두번 누르면 앱을 종료합니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            finish();
        }
    }
    public void onResume(){
        super.onResume();


    }
    */

    //arraylist 전달
    public ArrayList<FitnessCenter> getArrayList(){
        return arrayList;
    }
    //arraylist 값 설정
    public FitnessCenter getFitnessCenter(int i){
        return arrayList.get(i);
    }
    public void setFitnessCenter(FitnessCenter fitnessCenter, int i){
        arrayList.set(i, fitnessCenter);
        setDBFitnessValue(arrayList);
        return;
    }
    public void  setArrayList(ArrayList<FitnessCenter> arrayList){
        this.arrayList = arrayList;
        return;
    }
    //arraylist 값 DB로 보내기
    public void setDBFitnessValue(ArrayList<FitnessCenter> arrayList){
        myRef.setValue(arrayList);
        return;
    }
}