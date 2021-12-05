package com.example.swdevclass;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.swdevclass.FragmentFile.Fragment_Map;
import com.example.swdevclass.fitness.FitnessCenter;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest extends MainActivity{
    private Fragment_Map Map;
    private ArrayList<FitnessCenter> arrayList;
    private ArrayList<Marker> mapMarkers;
    private ArrayList<Marker> mainMarkers;

    @Test
    public void addition_isCorrect() {
    }

}