package com.example.mitchrv.repositories;

import android.util.Log;

import com.example.mitchrv.APIs.DvachAPI;
import com.example.mitchrv.APIs.DvachMessageAPI;
import com.example.mitchrv.CustomInterceptor;
import com.example.mitchrv.model.Feed;
import com.example.mitchrv.model.Messages;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//Singleton pattern

public class MessagesRepository {

    private static final String TAG = "MessagesRepository";

    private static MessagesRepository instance;
    private ArrayList<String> mComments = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    private static final String BASE_URL = "https://2ch.hk/";

    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    public static MessagesRepository getInstance() {
        if (instance == null) instance = new MessagesRepository();
        instance.interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return instance;
    }

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(interceptor) // This is used to add ApplicationInterceptor.
            .addNetworkInterceptor(new CustomInterceptor()) //This is used to add NetworkInterceptor.
            .build();

    Retrofit retrofitMSG = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();

    DvachMessageAPI dvachMessageAPI = retrofitMSG.create(DvachMessageAPI.class);

    public Observable<Messages> getMessages(int num) {
        return dvachMessageAPI
                .getStuff("get_thread", "pr", num, 1)
                .flatMap(Observable::fromIterable)
                .doOnNext(message -> {
                    mComments.add(message.getComment());
                    Log.d(TAG, "inside RX - mComments.size() " + mComments.size());
                    try {
                        message.getFiles().get(0);
                        mImageUrls.add("https://2ch.hk" + message.getFiles().get(0).getPath());
                    } catch (IndexOutOfBoundsException exc) {
                        mImageUrls.add("nothing there");
                    }

                    Log.d(TAG, "inside RX - mImageUrls.size(): " + mImageUrls.size());
                    Log.d(TAG, "initImageBitmaps: mImageUrls: " + mImageUrls);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public ArrayList<String> getComments() {
        return mComments;
    }

    public ArrayList<String> getImageUrls() {
        return mImageUrls;
    }

    public void removeComments() {
        mComments.clear();
    }

    public void removeImages() {
        mImageUrls.clear();
    }
}
