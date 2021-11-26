package com.example.swdevclass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    ListView customListView;
    private static CustomAdapter customAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<FitnessCenter> arrayList = ((MainActivity)getActivity()).getArrayList();

        //test code
        /*
        FitnessCenter fit = arrayList.get(0);
        String a = fit.getName();
        String b = fit.getAddress();
        Log.w("test",a+", "+ b);
        */

        View rootView = inflater.inflate(R.layout.fragment_2, container, false);

        customListView = (ListView) rootView.findViewById(R.id.listView_custom);
        customAdapter = new CustomAdapter(getContext(), arrayList);
        customListView.setAdapter(customAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) view.findViewById(R.id.textView_name).getTag().toString();
            }
        });


        return rootView;
    }
}