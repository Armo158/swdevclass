package com.example.swdevclass.FragmentFile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swdevclass.adapter.InfoAdapter;
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
    private ViewGroup rootView;

    public Fragment_Map(){}

    public static Fragment_Map newInstance(){
        Fragment_Map fragment = new Fragment_Map();
        return fragment;
    }

    public ArrayList<Marker> getMarkers(){
        return markers;
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
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map,
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

        arrayList = ((MainActivity)getActivity()).fitnessArrayListControl.getArrayList();

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        naverMap.setCameraPosition(cameraPosition);
        //마커 초기화


        markers.clear();
        //마커 값 설정
        for(FitnessCenter fitnessCenter: arrayList){
            Marker marker = new Marker();
            marker.setPosition(new LatLng(fitnessCenter.getLatitude(), fitnessCenter.getLongitude()));
            markers.add(marker);
        }
        //마커 지도에 표시
        for(Marker marker: markers){
            marker.setMap(naverMap);
        }

        InfoWindow infoWindow = new InfoWindow();
        InfoAdapter infoAdapter = new InfoAdapter(getContext(), rootView);
        infoWindow.setAdapter(infoAdapter);
        // 지도를 클릭하면 정보창을 닫음
        naverMap.setOnMapClickListener((coord, point) -> infoWindow.close());
        //marker 클릭시 요약창 띄우기
       for(Marker marker: markers) {
            marker.setOnClickListener(overlay -> {
                int a = markers.indexOf(marker);
                FitnessCenter fitnessCenter = arrayList.get(a);
                infoAdapter.setTextAddress(fitnessCenter.getAddress());
                infoAdapter.setTextNumber(fitnessCenter.getPhonenumber());
                infoAdapter.setTextTitle(fitnessCenter.getName());
                infoWindow.open(marker);
                infoWindow.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        int a = markers.indexOf(marker);
                        Bundle bundle = new Bundle();
                        bundle.putInt("MoreInfo", a);

                        Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.navermap);
                        MainActivity.fragmentStack.push(currentFragment);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        Fragment_MoreInfo fragment_moreInfo = new Fragment_MoreInfo();
                        fragment_moreInfo.setArguments(bundle);
                        transaction.replace(R.id.layout_main, fragment_moreInfo);
                        transaction.commit();
                        return false;
                    }
                });
                return true;
            });
        }

    }

    private void setMarkers(ArrayList<Marker> markers) {

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