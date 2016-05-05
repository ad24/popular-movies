package com.adel.freewing.popularmovie.json;

import android.content.Context;

import com.adel.freewing.popularmovie.model.Movie;
import com.adel.freewing.popularmovie.storage.MovieDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adel on 24-Apr-16.
 */
public class MoviesJsonParser {
    private JSONArray moviesJsonArray;
    public static final String RESULTS_KEY = "results";
    public static final String ORIGINAL_TITLE_KEY = "original_title";
    public static final String VOTE_AVERAGE_KEY = "vote_average";
    public static final String RELEASE_DATE_KEY = "release_date";
    public static final String OVERVIEW_KEY = "overview";
    public static final String POSTER_PATH_KEY = "poster_path";
    public static final String TOTAL_PAGES_KEY = "total_pages";
    public static final String TOTAL_RESULTS_KEY = "total_results";
    public static final String PAGE_NUMBER_KEY = "page";
    public static final String ID = "id";

    private MovieDB movieDB;

    public MoviesJsonParser(Context context,JSONArray moviesJsonArray) {
        this.moviesJsonArray = moviesJsonArray;
        movieDB=MovieDB.getInstance(context);
    }

    public List<Movie> parse() {
        List<Movie> movies = new ArrayList<>();
        movieDB.open();
        for (int i = 0; moviesJsonArray.length() > i; i++) {
            try {
                JSONObject movieJsonObject = moviesJsonArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setImage_url("http://image.tmdb.org/t/p/w185"+movieJsonObject.getString(POSTER_PATH_KEY));
                movie.setTitle(movieJsonObject.getString(ORIGINAL_TITLE_KEY));
                movie.setId(movieJsonObject.getInt(ID));
                movie.setDescription(movieJsonObject.getString(OVERVIEW_KEY));
                movie.setRelease_date(movieJsonObject.getString(RELEASE_DATE_KEY));
                movie.setUser_rate(movieJsonObject.getString(VOTE_AVERAGE_KEY));
                movies.add(movie);
                movieDB.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        movieDB.close();
        return movies;
    }
}
