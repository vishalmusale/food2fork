package com.example.food2fork.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.food2fork.models.Recipe;
import com.example.food2fork.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {
    private RecipeRepository mRecipeRepository;
    private boolean mIsPerformingQuery;
    private String mRecipeId;

    public RecipeViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
        mIsPerformingQuery = false;
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeRepository.getRecipe();
    }

    public String getRecipeId() {
        return mRecipeId;
    }

    public void getRecipeApi(String recipeId) {
        mRecipeId = recipeId;
        mRecipeRepository.getRecipeApi(recipeId);
    }

    public boolean isIsPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void setIsPerformingQuery(boolean mIsPerformingQuery) {
        this.mIsPerformingQuery = mIsPerformingQuery;
    }
}
