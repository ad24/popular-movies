package com.adel.freewing.popularmovie.interfaces;

import com.adel.freewing.popularmovie.model.Trailer;

import java.util.List;

/**
 * Created by Adel on 28/04/16.
 */
public interface TrailersListener {
    public void onsuccessLoadTrailers(List<Trailer> trailers);
}
