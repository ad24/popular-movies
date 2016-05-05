package com.adel.freewing.popularmovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adel.freewing.popularmovie.R;
import com.adel.freewing.popularmovie.model.Movie;
import com.adel.freewing.popularmovie.model.NavigationInfo;
import com.adel.freewing.popularmovie.storage.MovieDB;
import com.adel.freewing.popularmovie.utils.FragmentUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by Adel on 28/04/16.
 */
public class MovieDetailsFragment extends Fragment implements View.OnClickListener{
    public static final String TAG="movie_details_fragment";
    Movie movie;
    private TextView title;
    private TextView userRating;
    private TextView releaseDate;
    private TextView description;
    private ImageView logo;
    private Button trailer;
    private Button reviews;
    private Button addToFav;
    private MovieDB movieDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.movie_details_fragment_layout,container,false);
        //navigation
        NavigationInfo.fragment=null;
        NavigationInfo.tag=null;
        movieDB=MovieDB.getInstance(getActivity());
        movie= (Movie) getArguments().get("movie");
        title=(TextView)view.findViewById(R.id.mMovieTitleTextView);
        userRating=(TextView)view.findViewById(R.id.mUserRatingTextView);
        releaseDate=(TextView)view.findViewById(R.id.mReleaseDateTextView);
        description=(TextView)view.findViewById(R.id.mDescriptionTextView);
        logo=(ImageView) view.findViewById(R.id.mLogoImageView);
        trailer=(Button) view.findViewById(R.id.mTrailersButton);
        reviews=(Button) view.findViewById(R.id.mReviewsButton);
        addToFav=(Button) view.findViewById(R.id.mFavButton);
        trailer.setOnClickListener(this);
        reviews.setOnClickListener(this);
        addToFav.setOnClickListener(this);
        Picasso.with(getActivity()).load(movie.getImage_url()).into(logo);
        title.setText(movie.getTitle());
        releaseDate.setText(movie.getRelease_date());
        description.setText(movie.getDescription());
        userRating.setText(movie.getUser_rate());
        if (movie.isFavourite()){
            addToFav.setEnabled(false);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment=null;
        String tag="";
        Bundle bundle=new Bundle();
        switch (v.getId()){
            case R.id.mTrailersButton:
                fragment=new TrailerFragment();
                tag=TrailerFragment.TAG;
                break;
            case R.id.mReviewsButton:
                fragment=new ReviewsFragment();
                tag=ReviewsFragment.TAG;
                break;
            case R.id.mFavButton:
                movieDB.open();
                movieDB.setFavourite(movie.getId());
                movieDB.close();
                addToFav.setEnabled(false);
                break;
        }
        if (fragment!=null){
            bundle.putInt("id",movie.getId());
            FragmentUtil.gotoFragment((AppCompatActivity) getActivity(),bundle,fragment,tag);
        }
    }
}
