package com.example.food2fork.repositories;

import androidx.lifecycle.LiveData;

import com.example.food2fork.models.Recipe;
import com.example.food2fork.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository  {
    private static RecipeRepository instance;
    private static RecipeApiClient mRecipeApiClient;
    public static RecipeRepository getInstance() {
        if(instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository(){
        mRecipeApiClient = RecipeApiClient.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeApiClient.getRecipes();
    }

    public void searchRecipesApi(String query, int pageNumber){
        if(pageNumber == 0){
            pageNumber = 1;
        }
        mRecipeApiClient.searchRecipes(query);
    }

    public void cancelRequest(){
        mRecipeApiClient.clearDisposables();
    }
}
