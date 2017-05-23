package com.air.aircovg.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.air.aircovg.R;
import com.air.aircovg.model.Base;
import com.air.aircovg.model.Country;
import com.air.aircovg.model.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ayush AS on 20/5/17.
 */

public class SpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Country> mBase;

    public SpinnerAdapter(Context context, ArrayList<Country> base){
        mContext = context;
        mBase = base;
    }

    public int getCount() {
        return mBase.size();
    }

    @Override
    public Country getItem(int position) {
        return mBase.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null){
            rowView = LayoutInflater.from(mContext).inflate(R.layout.spinner, null);
            SpinnerAdapter.ViewHolder viewHolder = new SpinnerAdapter.ViewHolder();
            viewHolder.title= (TextView) rowView.findViewById(R.id.spinner_text_view);
            rowView.setTag(viewHolder);
        }

        Country country = getItem(position);
        SpinnerAdapter.ViewHolder viewHolder = (SpinnerAdapter.ViewHolder) rowView.getTag();
        viewHolder.title.setText(country.getmTitle());
        return rowView;
    }

    private static class ViewHolder{
        TextView title;
    }
}
