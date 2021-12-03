package com.example.swdevclass.FragmentFile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swdevclass.fitness.FitnessCenter;
import com.example.swdevclass.MainActivity;
import com.example.swdevclass.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;

import java.util.ArrayList;


public class Fragment_Map extends Fragment implements OnMapReadyCallback{
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private MapView mapView;
    private ArrayList<FitnessCenter> arrayList;
    private ArrayList<Marker> markers = new ArrayList<>();
    private LocationButtonView locationButtonView;
    CameraPosition cameraPosition;

    public Fragment_Map(){}

    public static Fragment_Map newInstance(){
        Fragment_Map fragment = new Fragment_Map();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        //카메라 포지션
        cameraPosition = new CameraPosition(
                new LatLng(35.154755, 128.105026), // 대상 지점
                14
        );

        NaverMapOptions options = new NaverMapOptions()
                .mapType(NaverMap.MapType.Basic)
                .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)
                .compassEnabled(true)
                .scaleBarEnabled(true)
                .locationButtonEnabled(true);

        FragmentManager fm = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.navermap);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(options);
            fm.beginTransaction().add(R.id.navermap, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map,
                container, false);

        mapView = (MapView) rootView.findViewById(R.id.navermap);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.None);

        arrayList = ((MainActivity)getActivity()).getArrayList();

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        naverMap.setCameraPosition(cameraPosition);
        //마커 초기화
        markers.clear();;
        //마커 값 설정
        for(FitnessCenter fitnessCenter: arrayList){
            Marker marker = new Marker();
            marker.setPosition(new LatLng(fitnessCenter.getLatitude(), fitnessCenter.getLongitude()));
            marker.setTag(fitnessCenter.getName());
            markers.add(marker);
        }
        //마커 지도에 표시
        for(Marker marker: markers){
            marker.setMap(naverMap);
        }

        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getActivity()) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return (CharSequence) infoWindow.getMarker().getTag();
            }
        });
        // 지도를 클릭하면 정보0 창을 닫음
        naverMap.setOnMapClickListener((coord, point) -> infoWindow.close());
        //marker 클릭시 요약창 띄우기
       for(Marker marker: markers) {
            marker.setOnClickListener(overlay -> {
                infoWindow.open(marker);
                infoWindow.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        int a = markers.indexOf(marker);
                        Log.w("test", String.valueOf(a));
                        Log.w("test", String.valueOf(arrayList.size()));
                        Log.w("test", String.valueOf(markers.size()));
                        return false;
                    }
                });
                return true;
            });
        }

    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }




}