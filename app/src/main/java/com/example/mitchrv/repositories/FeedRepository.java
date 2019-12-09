package com.example.mitchrv.repositories;

import com.example.mitchrv.APIs.DvachAPI;
import com.example.mitchrv.model.Feed;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//Singleton pattern

public class FeedRepository {

    private static FeedRepository instance;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Integer> mNums = new ArrayList<>();

    private static final String BASE_URL = "https://2ch.hk/pr/";


    public static FeedRepository getInstance() {
        if (instance == null) instance = new FeedRepository();
        return instance;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    DvachAPI dvachAPI = retrofit.create(DvachAPI.class);

    public Observable<String> getFeed() {

        return dvachAPI
                .getStuff()
                .flatMapIterable(Feed::getThreads)
                .doOnNext(threads -> mNames.add(threads.getSubject()))
                .doOnNext(threads -> mNums.add(threads.getNum()))
                .flatMap(threads -> Observable.fromArray(threads.getFiles().get(0)))
                .doOnNext(files -> mImageUrls.add("https://2ch.hk" + files.getPath()))
                .flatMap(files -> Observable.fromArray(files.getPath()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public ArrayList<String> getNames() {
        return mNames;
    }

    public ArrayList<String> getImageUrls() {
        return mImageUrls;
    }

    public ArrayList<Integer> getNums() {
        return mNums;
    }
}
