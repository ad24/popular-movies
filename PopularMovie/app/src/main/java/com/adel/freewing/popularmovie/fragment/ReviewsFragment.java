package com.adel.freewing.popularmovie.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.adel.freewing.popularmovie.R;
import com.adel.freewing.popularmovie.adapter.ReviewsAdapter;
import com.adel.freewing.popularmovie.app.AppController;
import com.adel.freewing.popularmovie.interfaces.ReviewsListener;
import com.adel.freewing.popularmovie.model.NavigationInfo;
import com.adel.freewing.popularmovie.model.Review;
import com.adel.freewing.popularmovie.storage.MovieDB;
import com.adel.freewing.popularmovie.webservice.ReviewsWS;

import java.util.List;

/**
 * Created by Adel on 28/04/16.
 */
public class ReviewsFragment extends Fragment implements ReviewsListener,AdapterView.OnItemClickListener {
    public static final String TAG="reviews_fragment";
    private ListView reviewsListView;
    private ReviewsWS reviewsWS;
    private int id;
    private List<Review> reviews;
    private MovieDB movieDB;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_layout,container,false);
        //navigation
        NavigationInfo.fragment=new MovieDetailsFragment();
        NavigationInfo.tag=MovieDetailsFragment.TAG;
        id=getArguments().getInt("id");
        movieDB=MovieDB.getInstance(getActivity());
        reviewsListView=(ListView)view.findViewById(R.id.listView);
        if (AppController.getInstance().isConnectingToInternet()) {
            reviewsWS = new ReviewsWS(getActivity(), this);
            reviewsWS.makeRequest(id);

        }else{
            Toast.makeText(getActivity(),"No internet connection",Toast.LENGTH_LONG).show();
            movieDB.open();
            reviews=movieDB.getReviews(id);
            if (reviews!=null)
            reviewsListView.setAdapter(new ReviewsAdapter(getActivity(),reviews));
            movieDB.close();
        }
        reviewsListView.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onSuccessLoadReviews(List<Review> reviews) {
        reviewsListView.setAdapter(new ReviewsAdapter(getActivity(),reviews));
        this.reviews=reviews;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviews.get(position).getUrl()));
        startActivity(browserIntent);
    }
}
