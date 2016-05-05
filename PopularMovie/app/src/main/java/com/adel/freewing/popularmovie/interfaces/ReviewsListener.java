package com.adel.freewing.popularmovie.interfaces;

import com.adel.freewing.popularmovie.model.Review;

import java.util.List;

/**
 * Created by Adel on 28/04/16.
 */
public interface ReviewsListener {
    public void onSuccessLoadReviews(List<Review> reviews);
}
