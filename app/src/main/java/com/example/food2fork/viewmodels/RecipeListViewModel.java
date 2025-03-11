package com.example.food2fork.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.food2fork.models.Recipe;
import com.example.food2fork.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository mRecipeRepository;
    private boolean mIsViewingRecipes;
    public RecipeListViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public boolean ismIsViewingRecipes() {
        return mIsViewingRecipes;
    }

    public void setmIsViewingRecipes(boolean mIsViewingRecipes) {
        this.mIsViewingRecipes = mIsViewingRecipes;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query, int pageNumber){
        mIsViewingRecipes = true;
        mRecipeRepository.searchRecipesApi(query, pageNumber);
    }

    public boolean onBackPressed(){
        if(mIsViewingRecipes){
            mIsViewingRecipes = false;
            return false;
        }else{
            return true;
        }
    }
}
