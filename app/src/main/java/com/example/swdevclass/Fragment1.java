package com.example.swdevclass;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.naver.maps.map.util.FusedLocationSource;


public class Fragment1 extends Fragment implements OnMapReadyCallback, MainActivity.onBackPressedListener{
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private MapView mapView;

    public Fragment1(){}

    public static Fragment1 newInstance(){
        Fragment1 fragment = new Fragment1();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        CameraPosition cameraPosition = new CameraPosition(
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1,
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

        //카메라 위치
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(35.154755, 128.105026), // 대상 지점
                14
        );

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        naverMap.setCameraPosition(cameraPosition);

        Marker muscle_factory_univ = new Marker();
        muscle_factory_univ.setPosition(new LatLng(35.15553, 128.10763));
        muscle_factory_univ.setMap(naverMap);

        Marker k_gym = new Marker();
        k_gym.setPosition(new LatLng(35.156836, 128.100862));
        k_gym.setMap(naverMap);

        Marker sport_complex = new Marker();
        sport_complex.setPosition(new LatLng(35.155365, 128.105863));
        sport_complex.setMap(naverMap);

        Marker power_gym = new Marker();
        power_gym.setPosition(new LatLng(35.153498, 128.106559));
        power_gym.setMap(naverMap);

        Marker apple_gym = new Marker();
        apple_gym.setPosition(new LatLng(35.154786, 128.107345));
        apple_gym.setMap(naverMap);

        Marker doosan_gym = new Marker();
        doosan_gym.setPosition(new LatLng(35.155039, 128.107485));
        doosan_gym.setMap(naverMap);

        Marker muscle_factory_station = new Marker();
        muscle_factory_station.setPosition(new LatLng(35.15451, 128.11823));
        muscle_factory_station.setMap(naverMap);


        /*infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "정보 창 내용";
            }
        });
        */

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

        // 마커를 클릭하면:
        /*Overlay.OnClickListener listener = overlay -> {
            Marker marker = (Marker)overlay;

            if (marker.getInfoWindow() == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker);
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close();
            }

            return true;
        };

        marker1.setOnClickListener(listener);
        marker2.setOnClickListener(listener);
        marker3.setOnClickListener(listener);
        */

        k_gym.setTag("경상대 K-짐\n 위도 : 35.156836, 경도 : 128.100862");
        k_gym.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(k_gym);
            return true;
        });

        power_gym.setTag("파워짐 휘트니스\n 위도 : 35.153498, 경도 : 128.106559");
        power_gym.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(power_gym);
            return true;
        });

        apple_gym.setTag("팀애플 휘트니스\n 위도 : 35.154786, 경도 : 128.107345");
        apple_gym.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(apple_gym);
            return true;
        });

        sport_complex.setTag("GNU 스포츠 콤플렉스\n 위도 : 35.155365, 경도 : 128.105863");
        sport_complex.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(sport_complex);
            return true;
        });

        doosan_gym.setTag("두산 휘트니스 센터\n 위도 : 35.155039, 경도 : 128.107485");
        doosan_gym.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(doosan_gym);
            return true;
        });

        muscle_factory_univ.setTag("머슬팩토리 24 경상대점\n 위도 : 35.15553, 경도 : 128.10763");
        muscle_factory_univ.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(muscle_factory_univ);
            return true;
        });

        muscle_factory_station.setTag("머슬팩토리 24 신진주역점\n 위도 : 35.15451, 경도 : 128.11823");
        muscle_factory_station.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(muscle_factory_station);
            return true;
        });
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

    @Override
    public void onBackPressed() {
        gotomain();
    }

    private void gotomain() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(Fragment1.this).commit();
        fragmentManager.popBackStack();
    }
}