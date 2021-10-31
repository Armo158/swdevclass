package com.example.swdevclass;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}