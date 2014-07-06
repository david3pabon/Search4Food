package com.lifesum.test.search4food.controllers;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lifesum.test.search4food.app.S4FApplication;
import com.lifesum.test.search4food.models.Food;
import com.lifesum.test.search4food.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 7/5/14.
 */
public class APIClientController {

    public JsonObjectRequest mRequest;

    public void search(String query, final SuccessCallback success, final ErrorCallback error) {
        mRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.END_POINT + query,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (success != null) success.onSuccess(populateResultList(response));
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError err) {
                if (error != null) error.onError();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put(Constants.HEADERS_AUTH, Constants.AUTH_TOKEN);
                return headers;
            }
        };
        S4FApplication.getInstance().getRequestQueue().add(mRequest);
    }

    public List<Food> populateResultList(JSONObject result) {
        List<Food> foods = new ArrayList<Food>();

        if (result!=null) {
            try {
                JSONObject response = result.getJSONObject("response");
                JSONArray list = response.getJSONArray("list");

                if (list.length() > 0) {
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject foodJSON = list.getJSONObject(i);
                        Food food = new Food();

                        food.apiId = foodJSON.getLong("id");
                        if (foodJSON.has("title")) food.title = foodJSON.getString("title");
                        if (foodJSON.has("categoryid")) food.categoryId = foodJSON.getLong("categoryid");
                        if (foodJSON.has("category")) food.category = foodJSON.getString("category");
                        if (foodJSON.has("calories")) food.calories = foodJSON.getInt("calories");

                        foods.add(food);
                    }
                }

            } catch (JSONException e) {

            }
        }

        return foods;
    }
}
