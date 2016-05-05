package com.adel.freewing.popularmovie.webservice;

import android.app.ProgressDialog;
import android.content.Context;

import com.adel.freewing.popularmovie.app.AppController;
import com.adel.freewing.popularmovie.interfaces.TrailersListener;
import com.adel.freewing.popularmovie.json.MoviesJsonParser;
import com.adel.freewing.popularmovie.json.TrailersParser;
import com.adel.freewing.popularmovie.model.Trailer;
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
public class TrailersWS {
    private String baseUrl;
    private String Tag="TrailersWS";
    private Context context;
    private TrailersListener listener;
    private ProgressDialog progressDialog;
    private MovieDB movieDB;

    public TrailersWS(Context context,TrailersListener listener){
        this.context=context;
        this.listener=listener;
        movieDB=MovieDB.getInstance(context);
    }

    public void makeRequest(final int id){
        progressDialog=ProgressDialog.show(context,null,"Loading...");
        baseUrl="https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=0a6ab634e7584553a1fa12121aad26dd";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,baseUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                System.out.println("response   "+response);
                try {
                    TrailersParser parser=new TrailersParser(response.getJSONArray("results"));
                    List<Trailer> trailers=parser.parse();
                    movieDB.open();
                    movieDB.addTrailers(trailers,id);
                    movieDB.close();
                    listener.onsuccessLoadTrailers(trailers);
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
