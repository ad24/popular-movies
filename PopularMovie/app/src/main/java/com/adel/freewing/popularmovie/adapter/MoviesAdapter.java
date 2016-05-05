package com.adel.freewing.popularmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.adel.freewing.popularmovie.R;
import com.adel.freewing.popularmovie.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Adel on 20-Apr-16.
 */
public class MoviesAdapter extends BaseAdapter {

    private List<Movie> movies;
    private Context context;

    private LayoutInflater layoutInflater;

    public MoviesAdapter(Context context,List<Movie> movies){
        this.context=context;
        this.movies=movies;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        System.out.println("movies.get(position).getImage_url()  "+movies.get(position).getImage_url());
        ImageView imageView;
            view=layoutInflater.inflate(R.layout.movie_item_layout,parent,false);
            imageView=(ImageView) view.findViewById(R.id.mMovieImageView);
        Picasso.with(context).load(movies.get(position).getImage_url()).into(imageView);
        return view;
    }
}
