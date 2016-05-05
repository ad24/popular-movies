package com.adel.freewing.popularmovie.interfaces;

import com.adel.freewing.popularmovie.model.Movie;

import java.util.List;

/**
 * Created by Adel on 24-Apr-16.
 */
public interface WebservicesListener {
    public void onSuccess(List<Movie> movies);
}
