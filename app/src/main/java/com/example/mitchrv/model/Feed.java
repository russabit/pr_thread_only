package com.example.mitchrv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Feed {

    @SerializedName("threads")
    @Expose
    private ArrayList<Threads> threads;

    public ArrayList<Threads> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<Threads> threads) {
        this.threads = threads;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "threads=" + threads +
                '}';
    }
}
