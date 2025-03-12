package com.example.food2fork;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.food2fork.models.Recipe;
import com.example.food2fork.util.Testing;
import com.example.food2fork.viewmodels.RecipeViewModel;

import androidx.lifecycle.Observer;

public class RecipeActivity extends BaseActivity{
    private static final String TAG = "RecipeActivity";
    private RecipeViewModel mRecipeViewModel;
    private ImageView imageView;
    private TextView mRecipeTitle, mRecipeSocialScore;
    private LinearLayout mIngredientsContainer;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        imageView = findViewById(R.id.recipe_image);
        mRecipeTitle = findViewById(R.id.recipe_title);
        mRecipeSocialScore = findViewById(R.id.recipe_social_score);
        mIngredientsContainer = findViewById(R.id.ingredients_container);
        scrollView = findViewById(R.id.parent);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);


        getIntentExtra();
        subscribeObservers();
    }

    private void getIntentExtra() {
        if(getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            assert recipe != null;
            Log.d(TAG, "getIntentExtra: " + recipe.getTitle());
            mRecipeViewModel.getRecipeApi(recipe.getRecipeID());
        }
    }

    private void subscribeObservers() {
        mRecipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                if(recipe != null){
                    mRecipeViewModel.setIsPerformingQuery(false);
                    Testing.printRecipe("network test", recipe);
                    for (String ingredient : recipe.getIngredients()) {
                        Log.d(TAG, "onChanged: Ingredient: " + ingredient);
                    }
                }
            }
        });
    }
}
