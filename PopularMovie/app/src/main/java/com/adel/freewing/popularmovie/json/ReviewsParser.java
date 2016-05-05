package com.adel.freewing.popularmovie.json;

import com.adel.freewing.popularmovie.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adel on 28/04/16.
 */
public class ReviewsParser {
    private JSONArray reviewsJsonArray;
    public static final String RESULTS_KEY = "results";
    public static final String AUTHOR_KEY = "author";
    public static final String CONTENT_KEY = "content";
    public static final String URL_KEY = "url";
    public static final String ID = "id";

    public ReviewsParser(JSONArray reviewsJsonArray) {
        this.reviewsJsonArray = reviewsJsonArray;
    }

    public List<Review> parse() {
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; reviewsJsonArray.length() > i; i++) {
            try {
                JSONObject reviewJsonObject = reviewsJsonArray.getJSONObject(i);
                Review review = new Review();
                review.setId(reviewJsonObject.getString(ID));
                review.setAuthor(reviewJsonObject.getString(AUTHOR_KEY));
                review.setContent(reviewJsonObject.getString(CONTENT_KEY));
                review.setUrl(reviewJsonObject.getString(URL_KEY));
                reviews.add(review);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return reviews;
    }
}
