package com.example.food2fork.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.food2fork.models.Recipe;
import com.example.food2fork.persistence.RecipeDao;
import com.example.food2fork.persistence.RecipeDatabase;
import com.example.food2fork.requests.response.ApiResponse;
import com.example.food2fork.requests.response.RecipeSearchResponse;
import com.example.food2fork.util.NetworkBoundResource;
import com.example.food2fork.util.Resource;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeDao recipeDao;

    public static RecipeRepository getInstance(Context context){
        if(instance == null){
            instance = new RecipeRepository(context);
        }
        return instance;
    }


    private RecipeRepository(Context context) {
        recipeDao = RecipeDatabase.getInstance(context).getRecipeDao();
    }

    public LiveData<Resource<List<Recipe>>> searchRecipesApi(final String query, final int pageNumber){
        return new NetworkBoundResource<List<Recipe>, RecipeSearchResponse>(Schedulers.io()){

            @Override
            public void saveCallResult(@NonNull RecipeSearchResponse item) {

            }

            @Override
            public boolean shouldFetch(@Nullable List<Recipe> data) {
                return true; // always query the network since the queries can be anything
            }

            @NonNull
            @Override
            public LiveData<List<Recipe>> loadFromDb() {
                return recipeDao.searchRecipes(query, pageNumber);
            }

            @NonNull
            @Override
            public Flowable<ApiResponse<RecipeSearchResponse>> createCall() {
                return null;
            }

        }.getAsLiveData();
    }
}
