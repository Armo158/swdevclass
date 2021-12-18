package com.example.swdevclass.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.swdevclass.R;
import com.naver.maps.map.overlay.InfoWindow;

public class InfoAdapter extends InfoWindow.DefaultViewAdapter
{
    private final Context mContext;
    private final ViewGroup mParent;
    private String TextTitle;
    private String TextAddress;
    private String TextNumber;
    private String Picture;

    public InfoAdapter(@NonNull Context context, ViewGroup parent)
    {
        super(context);
        mContext = context;
        mParent = parent;
    }

    @NonNull
    @Override
    protected View getContentView(@NonNull InfoWindow infoWindow)
    {

        View view = (View) LayoutInflater.from(mContext).inflate(R.layout.info_window, mParent, false);

        TextView txtTitle = (TextView) view.findViewById(R.id.txttitle);
        ImageView imagePoint = (ImageView) view.findViewById(R.id.imagepoint);
        TextView txtAddr = (TextView) view.findViewById(R.id.txtaddr);
        TextView txtTel = (TextView) view.findViewById(R.id.txttel);

        txtTitle.setText(TextTitle);

        imagePoint.setImageResource(R.drawable.ic_baseline_account_balance_24);
        txtAddr.setText(TextAddress);
        txtTel.setText(TextNumber);

        return view;
    }
    public void setTextTitle(String TextTitle){
        this.TextTitle = TextTitle;
    }

    public void setTextAddress(String textAddress) {
        this.TextAddress = textAddress;
    }

    public void setTextNumber(String textNumber) {
        this.TextNumber = textNumber;
    }

    public void setPicture(String picture){this.Picture = picture;}
}