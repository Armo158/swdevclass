package com.example.swdevclass.FragmentFile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swdevclass.R;


public class Fragment_MoreInfo extends Fragment {

    public Fragment_MoreInfo() {
        // Required empty public constructor
    }

    public static Fragment_MoreInfo newInstance(String param1, String param2) {
        Fragment_MoreInfo fragment = new Fragment_MoreInfo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments() != null){
            if(getArguments().get("MoreInfo") != null) {
                int a = getArguments().getInt("MoreInfo");
                Log.w("test", String.valueOf(a));
            }
            else if(getArguments().get("Edit") != null){
                int b = getArguments().getInt("Edit");
                Log.w("test", String.valueOf(b));
            }
            getArguments().clear();
        }


        return inflater.inflate(R.layout.fragment_more_info, container, false);
    }
}