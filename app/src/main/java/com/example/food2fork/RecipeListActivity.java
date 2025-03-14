package com.example.food2fork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food2fork.adapters.OnRecipeListener;
import com.example.food2fork.adapters.RecipeRecyclerAdapter;
import com.example.food2fork.models.Recipe;
import com.example.food2fork.util.Testing;
import com.example.food2fork.viewmodels.RecipeListViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {
    private static final String TAG = "RecipeListActivity";
    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mRecipeRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);

        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        initRecyclerView();
        initSearchView();
        subscribeObserver();
    }

    private void subscribeObserver() {
        mRecipeListViewModel.getViewState().observe(
                this,
                new Observer<RecipeListViewModel.ViewState>() {
                    @Override
                    public void onChanged(RecipeListViewModel.ViewState viewState) {
                        if(viewState != null) {
                            switch (viewState) {
                                case RECIPES:
                                    Log.d(TAG, "onChanged: RECIPES");
                                    break;
                                case CATEGORIES:
                                    displaySearchCategories();
                                    break;
                            }
                        }
                    }
                }
        );
    }

    private void displaySearchCategories() {
        mRecipeRecyclerAdapter.displaySearchCategories();
    }

    private void initSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initRecyclerView(){
        mRecipeRecyclerAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mRecipeRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}