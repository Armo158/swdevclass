package com.example.swdevclass.FragmentFile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swdevclass.MainActivity;
import com.example.swdevclass.R;
import com.example.swdevclass.adapter.ImageSliderAdapter;
import com.example.swdevclass.fitness.FitnessCenter;

import org.w3c.dom.Text;

public class Fragment_Edit extends Fragment implements View.OnClickListener{
    FitnessCenter fitnessCenter;
    ViewGroup v;
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;
    TextView name_id;
    EditText address_id;
    EditText number_id;
    EditText price_id;
    EditText time_id;
    EditText event_id;
    EditText more_id;
    Button btn_edit;
    Button btn_cancel;
    int indexnum; // index number

    /*
    SharedPreferences spref;
    SharedPreferences.Editor editor;
     */

    private String[] images = new String[]{
            "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
    };

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
        v = (ViewGroup)inflater.inflate(R.layout.fragment_edit, container, false);
        indexnum = getArguments().getInt("Editting");
        fitnessCenter = ((MainActivity)getActivity()).fitnessArrayListControl.getFitnessCenter(indexnum);

        sliderViewPager = v.findViewById(R.id.sliderViewPager);
        layoutIndicator = v.findViewById(R.id.layoutIndicators);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(getContext(), images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(images.length);


        name_id = (TextView)v.findViewById(R.id.name);
        address_id = (EditText)v.findViewById(R.id.addressEdit);
        number_id = (EditText)v.findViewById(R.id.numberEdit);
        price_id = (EditText)v.findViewById(R.id.priceEdit);
        time_id = (EditText)v.findViewById(R.id.timeEdit);
        event_id = (EditText)v.findViewById(R.id.eventEdit);
        more_id = (EditText)v.findViewById(R.id.moreEdit);

        name_id.setText(fitnessCenter.getName());
        address_id.setText(fitnessCenter.getAddress());
        number_id.setText(fitnessCenter.getPhonenumber());
        price_id.setText(fitnessCenter.getPrice());
        time_id.setText(fitnessCenter.getTime());
        event_id.setText(fitnessCenter.getEvent());
        more_id.setText(fitnessCenter.getEtc());

        btn_edit = (Button)v.findViewById(R.id.button_edit);
        btn_cancel = (Button)v.findViewById(R.id.button_cancel);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fitnessCenter.editFitnessCenter( address_id.getText().toString(), number_id.getText().toString(),
                        price_id.getText().toString(), time_id.getText().toString(), event_id.getText().toString(),
                        more_id.getText().toString());

                ((MainActivity)getActivity()).fitnessArrayListControl.setFitnessCenter(fitnessCenter, indexnum);
                ((MainActivity)getActivity()).fitnessArrayListControl.setDBFitnessValue();
                Toast.makeText(v.getContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        spref = getSharedPreferences("gref", MODE_PRIVATE);
        editor = spref.edit();

        String temp1 = spref.getString("editText_id1", " ");
        String temp2 = spref.getString("editText_id2", " ");
        String temp3 = spref.getString("editText_id3", " ");
        String temp4 = spref.getString("editText_id4", " ");
        String temp5 = spref.getString("editText_id5", " ");
        String temp6 = spref.getString("editText_id6", " ");
        */

        return v;
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
    public void onClick(View view) {

    }
}