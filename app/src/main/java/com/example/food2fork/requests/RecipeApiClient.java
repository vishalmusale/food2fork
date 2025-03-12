package com.example.food2fork.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.food2fork.models.Recipe;
import com.example.food2fork.requests.response.RecipeResponse;
import com.example.food2fork.requests.response.RecipeSearchResponse;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {
    private static final String TAG = "RecipeApiClient";
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    private MutableLiveData<Recipe> mRecipe;
    private CompositeDisposable disposables = new CompositeDisposable();

    public static RecipeApiClient getInstance() {
        if(instance == null) {
            instance = new RecipeApiClient();
        }

        return instance;
    }

    private RecipeApiClient() {
        mRecipes = new MutableLiveData<>();
        mRecipe = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipe;
    }

    public void getRecipeById(String recipeId) {
        Observable<Response<RecipeResponse>> observable = getRecipeObservable(recipeId);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<RecipeResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Response<RecipeResponse> response) {
                        if(response.isSuccessful()) {
                            mRecipe.postValue(response.body().getRecipe());
                        }
                        else {
                            Log.d(TAG, "onNext: Error code = " + response.code());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: Error during network call", e); // Log the error with stack trace

                        if (e instanceof UnknownHostException) {
                            Log.e(TAG, "onError: UnknownHostException - Check internet or hostname");
                        } else if (e instanceof ConnectException) {
                            Log.e(TAG, "onError: ConnectException - Check server or network");
                        } else {
                            Log.e(TAG, "onError: Other Error - Check details");
                        }

                        mRecipe.postValue(null);
                    }

                    @Override
                    public void onComplete() {
                        // Optional: Called when the Observable completes
                        Log.d(TAG, "onComplete: Observable completed");
                    }
                });
    }

    private Observable<Response<RecipeResponse>> getRecipeObservable(String recipeId) {
        return Observable.create(emitter -> {
            try {
                Call<RecipeResponse> call = ServiceGenerator.getRecipeApi().getRecipe(recipeId);
                Response<RecipeResponse> response = call.execute(); // Execute call synchronously
                if (!emitter.isDisposed()) {
                    emitter.onNext(response); // Emit the response
                    emitter.onComplete(); // Signal completion
                }
            } catch (Throwable e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(e); // Emit error
                }
            }
        });
    }

    public void searchRecipes(String query) {
        //Get the call to the API
        Observable<Response<RecipeSearchResponse>> observable = getRecipeSearchObservable(query);

        //Subscribe the call
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<RecipeSearchResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d); // Add to CompositeDisposable to manage
                    }

                    @Override
                    public void onNext(Response<RecipeSearchResponse> response) {
                        if (response.isSuccessful()) {
                            List<Recipe> list = new ArrayList<>(response.body().getRecipes());
                            List<Recipe> currentRecipes = mRecipes.getValue();
                            if (currentRecipes == null) {
                                currentRecipes = new ArrayList<>();
                            }
                            currentRecipes.addAll(list);
                            mRecipes.postValue(currentRecipes); // Update UI on the main thread
                        } else {
                            // Handle error
                            Log.e(TAG, "onNext: Error Code: " + response.code());
                            mRecipes.postValue(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: Error during network call", e); // Log the error with stack trace

                        if (e instanceof UnknownHostException) {
                            Log.e(TAG, "onError: UnknownHostException - Check internet or hostname");
                        } else if (e instanceof ConnectException) {
                            Log.e(TAG, "onError: ConnectException - Check server or network");
                        } else {
                            Log.e(TAG, "onError: Other Error - Check details");
                        }

                        mRecipes.postValue(null);
                    }

                    @Override
                    public void onComplete() {
                        // Optional: Called when the Observable completes
                        Log.d(TAG, "onComplete: Observable completed");
                    }
                });
    }

    private Observable<Response<RecipeSearchResponse>> getRecipeSearchObservable(String query) {
        return Observable.create(emitter -> {
            try {
                Call<RecipeSearchResponse> call = ServiceGenerator.getRecipeApi().searchRecipe(query);
                Response<RecipeSearchResponse> response = call.execute(); // Execute call synchronously
                if (!emitter.isDisposed()) {
                    emitter.onNext(response); // Emit the response
                    emitter.onComplete(); // Signal completion
                }
            } catch (Throwable e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(e); // Emit error
                }
            }
        });
    }
    public void clearDisposables() {
        disposables.clear();
    }
}
