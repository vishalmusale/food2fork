package com.example.food2fork;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.food2fork.models.Recipe;

public class RecipeActivity extends BaseActivity{
    private static final String TAG = "RecipeActivity";
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

        getIntentExtra();
    }

    private void getIntentExtra() {
        if(getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            assert recipe != null;
            Log.d(TAG, "getIntentExtra: " + recipe.getTitle());
        }
    }
}
