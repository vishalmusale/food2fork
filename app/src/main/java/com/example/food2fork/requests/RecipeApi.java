package com.example.food2fork.requests;

import com.example.food2fork.requests.response.RecipeResponse;
import com.example.food2fork.requests.response.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {
    @GET("search")
    Call<RecipeSearchResponse> searchRecipe (
            @Query("q") String query
    );

    @GET("get")
    Call<RecipeResponse> getRecipe (
            @Query("rId") String query
    );
}
