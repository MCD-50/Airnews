package com.air.aircovg.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.air.aircovg.R;
import com.air.aircovg.events.ClickListener;
import com.air.aircovg.model.News;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by mcd-50 on 29/6/17.
 */

public class SlidingAdapter extends RecyclerView.Adapter<SlidingAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mItems;
    private ClickListener mListener;
    private String mSelected;

    public SlidingAdapter(Context context, ArrayList<String> items, ClickListener listener, String selected){
        mContext = context;
        mItems = items;
        mListener = listener;
        mSelected = selected;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sliding_content_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String item = mItems.get(position);
        holder.bindData(item, mListener);
        holder.text.setText(item);
        if(item.equals(mSelected)){
            holder.text.setTextColor(Color.parseColor("#ff4500"));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView text;
        private CardView cardView;
        ViewHolder(View view){
            super(view);
            text= (TextView) view.findViewById(R.id.sliding_content_layout_text);
            cardView = (CardView) view.findViewById(R.id.sliding_content_layout_card);
        }

        public void bindData(final String item, final ClickListener listener){
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(item);
                }
            });
        }

    }
}
