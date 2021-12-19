package com.example.swdevclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.swdevclass.fitness.FitnessCenter;
import com.example.swdevclass.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private List list;

    public CustomAdapter(Context context, ArrayList<FitnessCenter> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this.list = arrayList;
    }

    class ViewHolder{
        public TextView tv_name;
        public TextView tv_summary;
        public ImageView iv_thumb;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.row_item, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.textView_name);
        viewHolder.tv_summary = (TextView) convertView.findViewById(R.id.textView_summary);
        //viewHolder.iv_thumb = (ImageView) convertView.findViewById(R.id.imageView_thumb);

        final FitnessCenter fitness = (FitnessCenter) list.get(position);

        String price = fitness.getPrice();
        int a = price.indexOf("\n");
        String substr = price.substring(0,a);


        viewHolder.tv_name.setText(fitness.getName());
        viewHolder.tv_summary.setText(substr);
        //Glide.with(context).load(fitness.getphoto_url).centerCrop().apply(new RequestOption().override(250, 350)).
        //into(viewHolder.iv_thumb);
        viewHolder.tv_name.setTag(fitness.getName());

        return convertView;
    }
}
