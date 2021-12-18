package com.example.swdevclass.FragmentFile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.swdevclass.fitness.FitnessCenter;
import com.example.swdevclass.MainActivity;
import com.example.swdevclass.R;
import com.example.swdevclass.adapter.CustomAdapter;

import java.util.ArrayList;

public class Fragment_MyFitnessList extends Fragment{

    ListView customListView;
    private static CustomAdapter customAdapter;

    public static Fragment_MyFitnessList newInstance() {
        Fragment_MyFitnessList fragment = new Fragment_MyFitnessList();
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
        ArrayList<FitnessCenter> arrayList = ((MainActivity)getActivity()).fitnessArrayListControl.getArrayList();

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        customListView = (ListView) rootView.findViewById(R.id.listView_custom);
        customAdapter = new CustomAdapter(getContext(), arrayList);
        customListView.setAdapter(customAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt("Edit", i);

                Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.layout_main);
                MainActivity.fragmentStack.push(currentFragment);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment_MoreInfo fragment_moreInfo = new Fragment_MoreInfo();
                fragment_moreInfo.setArguments(bundle);
                transaction.replace(R.id.layout_main, fragment_moreInfo);
                transaction.commit();
            }
        });


        return rootView;
    }

}