package com.example.chrome.aircovg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chrome.aircovg.R;
import com.example.chrome.aircovg.helpers.CategoriesHelper;
import com.example.chrome.aircovg.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by ayush AS on 27/12/16.
 */

public class CategoriesAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Category> mChannelsList;
    public CategoriesAdapter(Context context){
        mContext = context;
        String[] categories = CategoriesHelper.getCategories();
        int[] images = CategoriesHelper.getCategoriesImage();
        int[] size = CategoriesHelper.getCount();
        mChannelsList = new ArrayList<>();
        for(int i =0; i < categories.length; i++){
            mChannelsList.add(new Category(categories[i], images[i], size[i]));
        }
    }

    @Override
    public int getCount() {
        return mChannelsList.size();
    }

    @Override
    public Category getItem(int position) {
        return mChannelsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null){
            rowView = LayoutInflater.from(mContext).inflate(R.layout.categories_content_layout, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name= (TextView) rowView.findViewById(R.id.categories_content_textView);
            viewHolder.count= (TextView) rowView.findViewById(R.id.categories_content_countTextView);
            viewHolder.image= (ImageView) rowView.findViewById(R.id.categories_content_imageView);
            rowView.setTag(viewHolder);
        }

        Category category = getItem(position);
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();
        viewHolder.name.setText(category.getmName().toUpperCase());
        viewHolder.count.setText(String.valueOf(category.getmChannelCount() + " Channels"));
        Picasso.with(mContext).load(category.getmImage()).placeholder(R.drawable.back_big).into(viewHolder.image);
        return rowView;
    }

    static class ViewHolder{
        TextView name, count;
        ImageView image;
    }
}
