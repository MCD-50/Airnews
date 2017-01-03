package com.air.aircovg.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.air.aircovg.R;
import com.air.aircovg.model.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ayush AS on 27/12/16.
 */

public class AllNewsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<News> mNews;
    public AllNewsAdapter(Context context, ArrayList<News> news){
        mContext = context;
        mNews = news;
    }

    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public News getItem(int position) {
        return mNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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
        Picasso.with(mContext).load(news.getmImageUrl()).placeholder(R.drawable.back_small).into(viewHolder.smallImage);
        Picasso.with(mContext).load(news.getmImageUrl()).placeholder(R.drawable.back_big).into(viewHolder.image);

        return rowView;
    }

    static class ViewHolder{
        TextView author, description, title, publishedAt;
        ImageView image, smallImage;
    }

}
