package com.example.food2fork;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.food2fork.models.Recipe;
import com.example.food2fork.util.Testing;
import com.example.food2fork.viewmodels.RecipeListViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";
    private RecipeListViewModel mRecipeListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        subscribeObservers();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofitRequest();
            }
        });
    }

    private void testRetrofitRequest(){
        mRecipeListViewModel.searchRecipesApi("chicken", 1);
    }

    private void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if(recipes != null){
                    Testing.printRecipes("network test", recipes);
                }
            }
        });
    }
}