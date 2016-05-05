package com.adel.freewing.popularmovie;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adel.freewing.popularmovie.fragment.MovieDetailsFragment;
import com.adel.freewing.popularmovie.model.Movie;
import com.adel.freewing.popularmovie.model.NavigationInfo;
import com.adel.freewing.popularmovie.utils.FragmentUtil;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    Movie movie;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Movie Details");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        movie=(Movie)getIntent().getExtras().getSerializable("movie");
        Fragment fragment=new MovieDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("movie",movie);
        FragmentUtil.gotoFragment(this,bundle,fragment,MovieDetailsFragment.TAG);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        if (NavigationInfo.fragment!=null){
            Bundle bundle=new Bundle();
            bundle.putSerializable("movie",movie);
            FragmentUtil.gotoFragment(this,bundle,NavigationInfo.fragment,NavigationInfo.tag);
        }else{
            super.onBackPressed();
        }
    }
}
