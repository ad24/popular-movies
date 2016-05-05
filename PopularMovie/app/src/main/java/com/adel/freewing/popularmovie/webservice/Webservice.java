package com.adel.freewing.popularmovie.webservice;

import android.app.ProgressDialog;
import android.content.Context;

import com.adel.freewing.popularmovie.app.AppController;
import com.adel.freewing.popularmovie.interfaces.WebservicesListener;
import com.adel.freewing.popularmovie.json.MoviesJsonParser;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adel on 24-Apr-16.
 */
public class Webservice {
    private String baseUrl;
    private String Tag="Webservice";
    private Context context;
    private WebservicesListener listener;
    private ProgressDialog progressDialog;
    public Webservice(Context context,WebservicesListener listener){
        this.context=context;
        this.listener=listener;
    }
    public void makeRequest(int sort){
        progressDialog=ProgressDialog.show(context,null,"Loading...");
        String sortType=sort == 0 ? "popularity.desc" : "vote_average.desc";
        baseUrl="https://api.themoviedb.org/3/discover/movie?sort_by="+sortType+"&api_key=0a6ab634e7584553a1fa12121aad26dd";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,baseUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                System.out.println("response   "+response);
                try {
                    MoviesJsonParser parser=new MoviesJsonParser(context,response.getJSONArray("results"));
                    listener.onSuccess(parser.parse());
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
