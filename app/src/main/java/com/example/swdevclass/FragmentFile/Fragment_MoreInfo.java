package com.example.swdevclass.FragmentFile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.swdevclass.MainActivity;
import com.example.swdevclass.R;
import com.example.swdevclass.adapter.ImageSliderAdapter;
import com.example.swdevclass.fitness.FitnessCenter;


public class Fragment_MoreInfo extends Fragment {

    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;
    private SharedPreferences preferences;

    private String[] images = new String[] {
            "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
    };

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

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getContext(),
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //preference
        preferences = this.getActivity().getSharedPreferences("MoreInfo", Context.MODE_PRIVATE);
        // Inflate the layout for this fragment

        int a = -1;

        View view = inflater.inflate(R.layout.fragment_more_info,container,false);
        Button button_cancel = (Button)view.findViewById(R.id.button_cancel);
        Button button_edit = (Button)view.findViewById(R.id.button_edit);
        if(getArguments() != null){
            SharedPreferences.Editor editor = preferences.edit();

            if(getArguments().get("MoreInfo") != null) {
                a = getArguments().getInt("MoreInfo");

                button_cancel.setVisibility(view.INVISIBLE);
                button_edit.setVisibility(view.INVISIBLE);

                editor.putInt("MoreInfo", a);

            }
            else if(getArguments().get("Edit") != null){
                a = getArguments().getInt("Edit");
                Log.w("test", String.valueOf(a));

                editor.putInt("MoreInfo", a);
            }
            getArguments().clear();
            editor.commit();
        }

        a = preferences.getInt("MoreInfo", a);


        //취소버튼
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });
        //수정하기 버튼
        int finalA1 = a;
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("Editting", finalA1);

                Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.layout_main);
                MainActivity.fragmentStack.push(currentFragment);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment_Edit fragment_edit = new Fragment_Edit();
                fragment_edit.setArguments(bundle);
                transaction.replace(R.id.layout_main, fragment_edit);
                transaction.commit();
            }
        });




        FitnessCenter fitnessCenter = ((MainActivity)getActivity()).fitnessArrayListControl.getFitnessCenter(a);
        
        sliderViewPager = (ViewPager2) view.findViewById(R.id.sliderViewPager);
        layoutIndicator = (LinearLayout) view.findViewById(R.id.layoutIndicators);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(getContext(), images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        TextView nameText = (TextView) view.findViewById(R.id.name);
        TextView addressText = (TextView) view.findViewById(R.id.addressText);
        TextView numberText = (TextView) view.findViewById(R.id.numberText);
        TextView priceText = (TextView) view.findViewById(R.id.priceText);
        TextView timeText = (TextView) view.findViewById(R.id.timeText);
        TextView eventText = (TextView) view.findViewById(R.id.eventText);
        TextView etcText = (TextView) view.findViewById(R.id.moreText);
        nameText.setText(fitnessCenter.getName());
        addressText.setText(fitnessCenter.getAddress());
        numberText.setText(fitnessCenter.getPhonenumber());
        priceText.setText(fitnessCenter.getPrice());
        timeText.setText(fitnessCenter.getTime());
        eventText.setText(fitnessCenter.getEvent());
        etcText.setText(fitnessCenter.getEtc());

        setupIndicators(images.length);

        return view;
    }
}