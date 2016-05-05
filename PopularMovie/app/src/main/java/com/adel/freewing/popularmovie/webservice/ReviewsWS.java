package com.adel.freewing.popularmovie.webservice;

import android.app.ProgressDialog;
import android.content.Context;

import com.adel.freewing.popularmovie.app.AppController;
import com.adel.freewing.popularmovie.interfaces.ReviewsListener;
import com.adel.freewing.popularmovie.json.MoviesJsonParser;
import com.adel.freewing.popularmovie.json.ReviewsParser;
import com.adel.freewing.popularmovie.model.Review;
import com.adel.freewing.popularmovie.storage.MovieDB;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by mondy on 28/04/16.
 */
public class ReviewsWS {
    private String baseUrl;
    private String Tag="ReviewsWS";
    private Context context;
    private ReviewsListener listener;
    private ProgressDialog progressDialog;
    private MovieDB movieDB;
    public ReviewsWS(Context context,ReviewsListener listener){
        this.context=context;
        this.listener=listener;
        movieDB=MovieDB.getInstance(context);
    }
    public void makeRequest(final int id){
        progressDialog=ProgressDialog.show(context,null,"Loading...");
        baseUrl="https://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=0a6ab634e7584553a1fa12121aad26dd";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,baseUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                System.out.println("response   "+response);
                try {
                    ReviewsParser parser=new ReviewsParser(response.getJSONArray("results"));
                    List<Review> reviews=parser.parse();
                    movieDB.open();
                    movieDB.addReviews(reviews,id);
                    movieDB.close();
                    listener.onSuccessLoadReviews(reviews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error"+error.getLocalizedMessage());
                progressDialog.dismiss();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest,Tag);
    }
}
