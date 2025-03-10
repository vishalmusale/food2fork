package com.example.food2fork.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.food2fork.models.Recipe;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();
    public RecipeListViewModel() {

    }

    public LiveData<List<Recipe>> getmRecipes() {
        return mRecipes;
    }
}
