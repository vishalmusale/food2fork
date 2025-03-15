package com.example.food2fork.util;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.food2fork.requests.response.ApiResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

// CacheObject: Type for the Resource data. (database cache)
// RequestObject: Type for the API response. (network request)
public abstract class NetworkBoundResource<CacheObject, RequestObject> {

    private static final String TAG = "NetworkBoundResource";
    private final MediatorLiveData<Resource<CacheObject>> results = new MediatorLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final Scheduler backgroundScheduler;
    private LiveData<ApiResponse<RequestObject>> apiResponseLiveData;

    public NetworkBoundResource(Scheduler backgroundScheduler) {
        this.backgroundScheduler = backgroundScheduler;
        init();
    }

    private void init() {
        // update LiveData for loading status
        results.setValue((Resource<CacheObject>) Resource.loading(null));

        // observe LiveData source from local db
        final LiveData<CacheObject> dbSource = loadFromDb();

        results.addSource(dbSource, cacheObject -> {
            results.removeSource(dbSource);
            if (shouldFetch(cacheObject)) {
                // get data from the network
                fetchFromNetwork(dbSource);
            } else {
                results.addSource(dbSource, cacheObject1 -> setValue(Resource.success(cacheObject1)));
            }
        });
    }

    private void fetchFromNetwork(final LiveData<CacheObject> dbSource) {
        Log.d(TAG, "fetchFromNetwork: called.");

        // update LiveData for loading status
        results.addSource(dbSource, cacheObject -> setValue(Resource.loading(cacheObject)));

        Flowable<ApiResponse<RequestObject>> apiResponse = createCall();
        //Convert the flowable to LiveData
        apiResponseLiveData = new LiveData<ApiResponse<RequestObject>>() {
            @Override
            protected void onActive() {
                super.onActive();
                disposables.add(apiResponse
                        .subscribeOn(backgroundScheduler)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSubscriber<ApiResponse<RequestObject>>() {
                            @Override
                            public void onNext(ApiResponse<RequestObject> requestObjectApiResponse) {
                                setValue(requestObjectApiResponse);
                            }

                            @Override
                            public void onError(Throwable t) {
                                Log.e(TAG, "onError: ", t);

                            }

                            @Override
                            public void onComplete() {
                                // Not used in this case
                            }
                        }));
            }

            @Override
            protected void onInactive() {
                super.onInactive();
                disposables.clear();
            }
        };

        results.addSource(apiResponseLiveData, requestObjectApiResponse -> {
            results.removeSource(dbSource);
            results.removeSource(apiResponseLiveData);
            handleApiResponse(requestObjectApiResponse);
        });
    }

    private void handleApiResponse(ApiResponse<RequestObject> apiResponse) {
        if (apiResponse instanceof ApiResponse.ApiSuccessResponse) {
            Log.d(TAG, "onChanged: ApiSuccessResponse.");
            RequestObject response = processResponse((ApiResponse.ApiSuccessResponse) apiResponse);
            disposables.add(Flowable.just(response)
                    .subscribeOn(backgroundScheduler)
                    .subscribe(requestObject -> {
                        saveCallResult(requestObject);
                        // Fetch from db again
                        results.addSource(loadFromDb(), cacheObject -> setValue(Resource.success(cacheObject)));
                    }));
        } else if (apiResponse instanceof ApiResponse.ApiEmptyResponse) {
            Log.d(TAG, "onChanged: ApiEmptyResponse");
            results.addSource(loadFromDb(), cacheObject -> setValue(Resource.success(cacheObject)));
        } else if (apiResponse instanceof ApiResponse.ApiErrorResponse) {
            Log.d(TAG, "onChanged: ApiErrorResponse.");
            results.addSource(loadFromDb(), cacheObject -> setValue(
                    Resource.error(((ApiResponse.ApiErrorResponse) apiResponse).getErrorMessage(), cacheObject)
            ));
        }
    }

    private RequestObject processResponse(ApiResponse.ApiSuccessResponse response) {
        return (RequestObject) response.getBody();
    }

    private void setValue(Resource<CacheObject> newValue) {
        if (results.getValue() == null || !results.getValue().equals(newValue)) {
            results.setValue(newValue);
        }
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestObject item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable CacheObject data);

    @NonNull
    @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Flowable<ApiResponse<RequestObject>> createCall();

    public final LiveData<Resource<CacheObject>> getAsLiveData() {
        return results;
    }

    public void cancelRequest(){
        disposables.clear();
    }
}
