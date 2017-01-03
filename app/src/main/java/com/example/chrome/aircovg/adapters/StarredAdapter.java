package com.example.chrome.aircovg.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chrome.aircovg.R;
import com.example.chrome.aircovg.model.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ayush AS on 27/12/16.
 */

public class StarredAdapter extends BaseAdapter {

    ArrayList<News> mNewsList;
    Context mContext;

    public StarredAdapter(Context context, ArrayList<News> news){
        mContext = context;
        mNewsList = news;
    }



    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public News getItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null){
            rowView = LayoutInflater.from(mContext).inflate(R.layout.news_content_layout, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.author= (TextView) rowView.findViewById(R.id.news_content_author);
            viewHolder.title= (TextView) rowView.findViewById(R.id.news_content_title);
            viewHolder.description= (TextView) rowView.findViewById(R.id.news_content_description);
            viewHolder.publishedAt= (TextView) rowView.findViewById(R.id.news_content_published_at);
            viewHolder.image= (ImageView) rowView.findViewById(R.id.news_content_image);
            viewHolder.smallImage= (ImageView) rowView.findViewById(R.id.news_content_smallImage);
            rowView.setTag(viewHolder);
        }

        News news = getItem(position);
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();
        viewHolder.author.setText(news.getmAuthor());
        viewHolder.title.setText(news.getmTitle());
        viewHolder.description.setText(news.getmDescription());
        viewHolder.publishedAt.setText(news.getmPublishedAt());
        Bitmap b = news.getmLocalImage();
        if(b != null){
            viewHolder.smallImage.setImageBitmap(b);
            viewHolder.image.setImageBitmap(b);
        }else {
            Picasso.with(mContext).load(news.getmImageUrl()).placeholder(R.drawable.back_small).into(viewHolder.smallImage);
            Picasso.with(mContext).load(news.getmImageUrl()).placeholder(R.drawable.back_big).into(viewHolder.image);
        }

        return rowView;
    }

    static class ViewHolder{
        TextView author, description, title, publishedAt;
        ImageView image, smallImage;
    }
}
