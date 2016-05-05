package com.adel.freewing.popularmovie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.adel.freewing.popularmovie.DetailsActivity;
import com.adel.freewing.popularmovie.R;
import com.adel.freewing.popularmovie.adapter.MoviesAdapter;
import com.adel.freewing.popularmovie.app.AppController;
import com.adel.freewing.popularmovie.interfaces.WebservicesListener;
import com.adel.freewing.popularmovie.model.Movie;
import com.adel.freewing.popularmovie.storage.MovieDB;
import com.adel.freewing.popularmovie.utils.FragmentUtil;
import com.adel.freewing.popularmovie.webservice.Webservice;

import java.util.List;

/**
 * Created by Adel on 28/04/16.
 */
public class MovieFragment extends Fragment implements WebservicesListener,AdapterView.OnItemClickListener {
    public static final String TAG="movie_fragment";
    private GridView gridView;
    private List<Movie> movies;
    private MovieDB movieDB;
    private int sort;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.movies_grid_view_layout,container,false);
        sort=getArguments().getInt("sort");
        gridView=(GridView)view.findViewById(R.id.mMovieGridView);
        movieDB=MovieDB.getInstance(getActivity());
        if (sort==-1) {
            movieDB.open();
            this.movies=movieDB.getFavMovies();
            gridView.setAdapter(new MoviesAdapter(getActivity(),movies));
            movieDB.close();
        }else if (AppController.getInstance().isConnectingToInternet()){
            Webservice webservice = new Webservice(getActivity(), this);
            webservice.makeRequest(sort);
        }else{
            Toast.makeText(getActivity(),"No internet connection",Toast.LENGTH_LONG).show();
            movieDB.open();
            this.movies=movieDB.getMovies();
            gridView.setAdapter(new MoviesAdapter(getActivity(),movies));
            movieDB.close();
        }

        gridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onSuccess(List<Movie> movies) {
        if (sort!=-1&&AppController.getInstance().isConnectingToInternet()) {
            gridView.setAdapter(new MoviesAdapter(getActivity(), movies));
            this.movies = movies;
        }
        if (AppController.getInstance().isTablet(getActivity())||getActivity().getResources().getConfiguration().orientation== 2)
        {
            Fragment fragment=new MovieDetailsFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable("movie",movies.get(0));
            FragmentUtil.gotoFragment((AppCompatActivity) getActivity(),R.id.mDetailsContainer,bundle,fragment,MovieDetailsFragment.TAG);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (AppController.getInstance().isTablet(getActivity())||getActivity().getResources().getConfiguration().orientation==2 )
        {
            Fragment fragment=new MovieDetailsFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable("movie",movies.get(position));
            FragmentUtil.gotoFragment((AppCompatActivity) getActivity(),R.id.mDetailsContainer,bundle,fragment,MovieDetailsFragment.TAG);
        }else {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("movie", movies.get(position));
            startActivity(intent);
        }
    }
}
