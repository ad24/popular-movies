package com.adel.freewing.popularmovie.storage;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.adel.freewing.popularmovie.model.Movie;
import com.adel.freewing.popularmovie.model.Review;
import com.adel.freewing.popularmovie.model.Trailer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adel on 28/04/16.
 */
public class MovieDB {
    private static MovieDB  mInstance=null;
    private SQLiteDatabase database;
    private static  DatabaseSQLiteHelper dbHelper;
    private String[] movieColumns = {
            DatabaseSQLiteHelper.MOVIE_ID,
            DatabaseSQLiteHelper.MOVIE_TITLE,
            DatabaseSQLiteHelper.MOVIE_DESCRIPTION,
            DatabaseSQLiteHelper.MOVIE_POSTER_URL,
            DatabaseSQLiteHelper.MOVIE_RATE,
            DatabaseSQLiteHelper.MOVIE_REVIEWS,
            DatabaseSQLiteHelper.MOVIE_TRAILERS,
            DatabaseSQLiteHelper.MOVIE_RELEASE_DATE,
            DatabaseSQLiteHelper.MOVIE_IS_FAVOURITE
    };

    public static synchronized MovieDB getInstance(Context context){
        dbHelper = new DatabaseSQLiteHelper(context);
        if (mInstance==null){
            mInstance=new MovieDB();
        }
        return mInstance;
    }
    private MovieDB(){
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long add(Movie movie) {
        // add exercise
        if (movieNotExit(movie.getId())) {
            ContentValues movieValues = new ContentValues();
            movieValues.put(DatabaseSQLiteHelper.MOVIE_ID,
                    movie.getId());
            movieValues.put(DatabaseSQLiteHelper.MOVIE_TITLE,
                    movie.getTitle());
            movieValues.put(DatabaseSQLiteHelper.MOVIE_DESCRIPTION,
                    movie.getDescription());
            movieValues.put(DatabaseSQLiteHelper.MOVIE_POSTER_URL,
                    movie.getImage_url());
            movieValues.put(DatabaseSQLiteHelper.MOVIE_RATE,
                    movie.getUser_rate());
            movieValues.put(DatabaseSQLiteHelper.MOVIE_RELEASE_DATE,
                    movie.getRelease_date());
            return  database.insert(
                    DatabaseSQLiteHelper.MOVIE_TABLE, null, movieValues);
        }
        return 0;
    }


    public long addReviews(List<Review> reviews,int movie_id){
        Gson gson=new Gson();
       String review= gson.toJson(reviews);
        // update reviews
        ContentValues values = new ContentValues();
        values.put(DatabaseSQLiteHelper.MOVIE_REVIEWS,
               review);
      return database.update(DatabaseSQLiteHelper.MOVIE_TABLE, values,
                DatabaseSQLiteHelper.MOVIE_ID + "=" + movie_id, null);

    }

    public long setFavourite(int movie_id){
        // update favourite
        ContentValues values = new ContentValues();
        values.put(DatabaseSQLiteHelper.MOVIE_IS_FAVOURITE,
                1);
        return database.update(DatabaseSQLiteHelper.MOVIE_TABLE, values,
                DatabaseSQLiteHelper.MOVIE_ID + "=" + movie_id, null);
    }

    public long addTrailers(List<Trailer> trailers,int movie_id){
        Gson gson=new Gson();
        String trailer= gson.toJson(trailers);
        // update trailers
        ContentValues values = new ContentValues();
        values.put(DatabaseSQLiteHelper.MOVIE_TRAILERS,
                trailer);
        return database.update(DatabaseSQLiteHelper.MOVIE_TABLE, values,
                DatabaseSQLiteHelper.MOVIE_ID + " = " + movie_id, null);
    }

    public List<Movie> getMovies(){
        List<Movie> movies = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseSQLiteHelper.MOVIE_TABLE, movieColumns, null,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Movie movie = cursorToMovie(cursor);
            movies.add(movie);
            cursor.moveToNext();
        }
        cursor.close();
        return movies;
    }

    public List<Movie> getFavMovies(){
        List<Movie> movies = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseSQLiteHelper.MOVIE_TABLE, movieColumns, DatabaseSQLiteHelper.MOVIE_IS_FAVOURITE+ " = 1",
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Movie movie = cursorToMovie(cursor);
            movies.add(movie);
            cursor.moveToNext();
        }
        cursor.close();
        return movies;
    }



    private Movie cursorToMovie(Cursor cursor) {
        int id = cursor.getInt(cursor
                .getColumnIndex(DatabaseSQLiteHelper.MOVIE_ID));
        String title = cursor.getString(cursor
                .getColumnIndex(DatabaseSQLiteHelper.MOVIE_TITLE));
        String description = cursor.getString(cursor
                .getColumnIndex(DatabaseSQLiteHelper.MOVIE_DESCRIPTION));
        String poster_url = cursor.getString(cursor
                .getColumnIndex(DatabaseSQLiteHelper.MOVIE_POSTER_URL));
        String release_date = cursor.getString(cursor
                .getColumnIndex(DatabaseSQLiteHelper.MOVIE_RELEASE_DATE));
        String rate = cursor.getString(cursor
                .getColumnIndex(DatabaseSQLiteHelper.MOVIE_RATE));
        int fav = cursor.getInt(cursor
                .getColumnIndex(DatabaseSQLiteHelper.MOVIE_IS_FAVOURITE));
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setImage_url(poster_url);
        movie.setRelease_date(release_date);
        movie.setUser_rate(rate);
        movie.setFavourite(fav==1);
        return movie;
    }


    public List<Trailer> getTrailers(int movie_id){
        List<Trailer> trailers=new ArrayList<>();
        Cursor cursor = database.query(
                DatabaseSQLiteHelper.MOVIE_TABLE, movieColumns, DatabaseSQLiteHelper.MOVIE_ID + " = " + movie_id,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Gson gson=new Gson();
            String trailer=cursor.getString(cursor
                    .getColumnIndex(DatabaseSQLiteHelper.MOVIE_TRAILERS));
            Type type = new TypeToken<List<Trailer>>(){}.getType();
            trailers=gson.fromJson(trailer,type);
            cursor.moveToNext();

        }
        cursor.close();
        return trailers;
    }

    public List<Review> getReviews(int movie_id){
        List<Review> reviews=new ArrayList<>();
        Cursor cursor = database.query(
                DatabaseSQLiteHelper.MOVIE_TABLE, movieColumns, DatabaseSQLiteHelper.MOVIE_ID + " = " + movie_id,
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Gson gson=new Gson();
            String trailer=cursor.getString(cursor
                    .getColumnIndex(DatabaseSQLiteHelper.MOVIE_REVIEWS));
            Type type = new TypeToken<List<Review>>(){}.getType();
            reviews=gson.fromJson(trailer,type);
            cursor.moveToNext();

        }
        cursor.close();
        return reviews;
    }

    private boolean movieNotExit(int id) {
        Cursor cursor = database.query(
                DatabaseSQLiteHelper.MOVIE_TABLE, movieColumns,
                DatabaseSQLiteHelper.MOVIE_ID + " = " + id, null,
                null, null, null);
        int count = cursor.getCount();
        return count==0?true:false;
    }


}
