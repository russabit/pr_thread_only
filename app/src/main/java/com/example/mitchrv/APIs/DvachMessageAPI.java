package com.example.mitchrv.APIs;

import com.example.mitchrv.model.Messages;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DvachMessageAPI {

    //"https://2ch.hk/makaba/mobile.fcgi?task=get_thread&board=b&thread=208189901&post=1"

    @GET("/makaba/mobile.fcgi")
    Observable<List<Messages>> getStuff(@Query("task") String task, @Query("board") String board, @Query("thread") int num, @Query("post") int post);

}
