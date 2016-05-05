package com.adel.freewing.popularmovie.json;

import android.content.Context;

import com.adel.freewing.popularmovie.model.Review;
import com.adel.freewing.popularmovie.model.Trailer;
import com.adel.freewing.popularmovie.storage.MovieDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adel on 28/04/16.
 */
public class TrailersParser {

    private JSONArray trailersJsonArray;
    public static final String RESULTS_KEY = "results";
    public static final String NAME_KEY = "name";
    public static final String KEY = "key";
    public static final String ID = "id";

    public TrailersParser(JSONArray trailersJsonArray) {
        this.trailersJsonArray = trailersJsonArray;
    }

    public List<Trailer> parse() {
        List<Trailer> trailers = new ArrayList<>();
        for (int i = 0; trailersJsonArray.length() > i; i++) {
            try {
                JSONObject trailerJsonObject = trailersJsonArray.getJSONObject(i);
                Trailer trailer = new Trailer();
                trailer.setId(trailerJsonObject.getString(ID));
                trailer.setName(trailerJsonObject.getString(NAME_KEY));
                trailer.setKey(trailerJsonObject.getString(KEY));
                trailers.add(trailer);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return trailers;
    }
}
