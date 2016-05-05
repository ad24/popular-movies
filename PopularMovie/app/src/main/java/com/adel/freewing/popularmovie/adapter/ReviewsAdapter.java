package com.adel.freewing.popularmovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adel.freewing.popularmovie.R;
import com.adel.freewing.popularmovie.model.Review;

import java.util.List;

/**
 * Created by Adel on 28/04/16.
 */
public class ReviewsAdapter extends ArrayAdapter<Review> {

    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    public ReviewsAdapter(Context context, List<Review> objects) {
        super(context, R.layout.review_list_row_layout, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.review_list_row_layout, null);
            viewHolder=new ViewHolder();
            viewHolder.authorTextView=(TextView)convertView.findViewById(R.id.mAuthorTextView);
            viewHolder.contentTextView=(TextView)convertView.findViewById(R.id.mContentTextView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.authorTextView.setText(getItem(position).getAuthor());
        viewHolder.contentTextView.setText(getItem(position).getContent());
        return convertView;
    }

    class ViewHolder{
         TextView authorTextView;
         TextView contentTextView;
    }
}
