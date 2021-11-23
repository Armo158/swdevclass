package com.example.swdevclass;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //bottom Navigation view
    BottomNavigationView bottomNavigationView;
    //realtime database
    private ArrayList<FitnessCenter> arrayList; //db로부터 내용을 받아와서 넣는곳

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //db관련
        arrayList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("fitness"); //DB table

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Datasnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear();// 기존배열 리스트가 존재하지 않게 초기화
                for(DataSnapshot snapshot: Datasnapshot.getChildren()) {//반복문으로 데이터 list를 추출해냄
                    FitnessCenter fitnessCenter = snapshot.getValue(FitnessCenter.class);//만들어뒀던 fitness 객체에 데이터 넣
                    arrayList.add(fitnessCenter); //담은 데이터들을 배열리스트에 넣음

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavi); //처음화면

        getSupportFragmentManager().beginTransaction().add(R.id.layout_main, new Fragment1()).commit();
        //FrameLayout에 fragment.xml 띄우기

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_fragment1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, new Fragment1()).commit();
                        break;
                    case R.id.item_fragment2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, new Fragment2()).commit();
                        break;
                    case R.id.item_fragment3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main, new Fragment3()).commit();
                }
                return;
            }
        });

    }
    //프래그먼트 전환
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_main, fragment).commit();
    }
    //뒤로가기
    public interface onBackPressedListener{
        void onBackPressed();
    }
    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for(Fragment fragment: fragmentList){
            if(fragment instanceof onBackPressedListener){
                ((onBackPressedListener)fragment).onBackPressed();
                return;
            }
        }
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        }
        lastTimeBackPressed = System.currentTimeMillis();
        Toast.makeText(this, "'뒤로'버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }
}