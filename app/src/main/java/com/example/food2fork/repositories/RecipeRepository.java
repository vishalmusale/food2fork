package com.example.food2fork.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.food2fork.models.Recipe;

import java.util.List;

public class RecipeRepository  {
    private static RecipeRepository instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    public static RecipeRepository getInstance() {
        if(instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository(){
        mRecipes = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }
}
