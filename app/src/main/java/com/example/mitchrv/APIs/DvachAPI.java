package com.example.mitchrv.APIs;

import com.example.mitchrv.model.Feed;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface DvachAPI {

    String BASE_URL = "https://2ch.hk/b/";

    @Headers("Content-Type: application/json")
    @GET("catalog.json")
    Observable<Feed> getStuff();
}
