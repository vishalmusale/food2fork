package com.example.food2fork;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.food2fork.models.Recipe;
import com.example.food2fork.viewmodels.RecipeViewModel;

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

        showProgressBar(true);
        getIntentExtra();
        subscribeObservers();
    }

    private void getIntentExtra() {
        if(getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            assert recipe != null;
            Log.d(TAG, "getIntentExtra: " + recipe.getTitle());
            mRecipeViewModel.getRecipeApi(recipe.getRecipe_id());
        }
    }

    private void subscribeObservers() {
        mRecipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                if(recipe != null && mRecipeViewModel.getRecipeId().equals(recipe.getRecipe_id())){
                    mRecipeViewModel.setIsPerformingQuery(false);
                    setRecipeProperties(recipe);
                }
            }
        });
    }

    private void setRecipeProperties(Recipe recipe){
        if(recipe != null){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_foreground);
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipe.getImage_url())
                    .into(imageView);
            mRecipeTitle.setText(recipe.getTitle());
            mRecipeSocialScore.setText(String.valueOf(Math.round(recipe.getSocial_rank())));
            mIngredientsContainer.removeAllViews();
            for(String ingredient : recipe.getIngredients()) {
                TextView textView = new TextView(this);
                textView.setText(ingredient);
                textView.setTextSize(15);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT ));
                textView.setPadding(16, 16, 16, 16);
                mIngredientsContainer.addView(textView);
            }
        }
        showParent();
        showProgressBar(false);
    }

    private void showParent() {
        scrollView.setVisibility(View.VISIBLE);
    }
}
