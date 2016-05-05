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
import com.adel.freewing.popularmovie.adapter.TrailerAdapter;
import com.adel.freewing.popularmovie.app.AppController;
import com.adel.freewing.popularmovie.interfaces.TrailersListener;
import com.adel.freewing.popularmovie.model.NavigationInfo;
import com.adel.freewing.popularmovie.model.Trailer;
import com.adel.freewing.popularmovie.storage.MovieDB;
import com.adel.freewing.popularmovie.webservice.TrailersWS;

import java.util.List;

/**
 * Created by Adel on 28/04/16.
 */
public class TrailerFragment extends Fragment implements TrailersListener,AdapterView.OnItemClickListener{

    public static final String TAG="trailer_fragment";
    private ListView trailersListView;
    private TrailersWS trailersWS;
    private int id;
    private List<Trailer> trailers;
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
        trailersListView=(ListView)view.findViewById(R.id.listView);
        if (AppController.getInstance().isConnectingToInternet()) {
            trailersWS = new TrailersWS(getActivity(), this);
            trailersWS.makeRequest(id);
        }else{
            Toast.makeText(getActivity(),"No internet connection",Toast.LENGTH_LONG).show();
            movieDB.open();
            trailers=movieDB.getTrailers(id);
            if (trailers!=null)
            trailersListView.setAdapter(new TrailerAdapter(getActivity(),trailers));
            movieDB.close();
        }
        trailersListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onsuccessLoadTrailers(List<Trailer> trailers) {
        trailersListView.setAdapter(new TrailerAdapter(getActivity(),trailers));
        this.trailers=trailers;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+trailers.get(position).getKey()));
        startActivity(browserIntent);
    }
}
