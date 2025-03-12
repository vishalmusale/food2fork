package com.example.food2fork.util;

import android.util.Log;

import com.example.food2fork.models.Recipe;

import java.util.List;

public class Testing {

    public static void printRecipes(String tag, List<Recipe> list){
        for(Recipe recipe: list){
            Log.d(tag, "printRecipes: " + recipe.toString());
        }
    }

    public static void printRecipe(String tag, Recipe recipe){
        Log.d(tag, "printRecipe: " + recipe.toString());
    }
}
