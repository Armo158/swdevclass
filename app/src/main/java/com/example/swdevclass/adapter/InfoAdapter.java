package com.example.swdevclass.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.example.swdevclass.R;
import com.naver.maps.map.overlay.InfoWindow;

import java.util.ArrayList;

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



        Glide.with(mContext).asBitmap().load(Picture).override(48,32).placeholder(R.drawable.error_image).into(imagePoint);
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