package com.adel.freewing.popularmovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adel.freewing.popularmovie.R;
import com.adel.freewing.popularmovie.model.Trailer;

import java.util.List;

/**
 * Created by Adel on 28/04/16.
 */
public class TrailerAdapter extends ArrayAdapter<Trailer> {

    private ViewHolder viewHolder;
    public TrailerAdapter(Context context, List<Trailer> trailers) {
        super(context, R.layout.trailer_list_row_layout, trailers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.trailer_list_row_layout, null);
            viewHolder=new ViewHolder();
            viewHolder.nameTextView =(TextView)convertView.findViewById(R.id.mTrailerNameTextView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.nameTextView.setText(getItem(position).getName());
        return convertView;
    }

    class ViewHolder{
        TextView nameTextView;
    }
}
