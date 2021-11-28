package com.example.swdevclass.FragmentFile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swdevclass.R;

public class Fragment_Edit extends Fragment {

    public Fragment_Edit() {
        // Required empty public constructor
    }


    public static Fragment_Edit newInstance(String param1, String param2) {
        Fragment_Edit fragment = new Fragment_Edit();

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
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }
}