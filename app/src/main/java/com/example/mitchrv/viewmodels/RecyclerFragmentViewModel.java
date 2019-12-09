package com.example.mitchrv.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mitchrv.model.Feed;
import com.example.mitchrv.model.Threads;
import com.example.mitchrv.repositories.FeedRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RecyclerFragmentViewModel extends ViewModel {

    private Observable<String> observableFeed;
/*    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Integer> mNums = new ArrayList<>();*/

    private FeedRepository feedRepository;

    public void init() {
        if (observableFeed!=null) {
            return;
        }
        feedRepository = FeedRepository.getInstance();
        observableFeed = this.feedRepository.getFeed();
/*        mNames = this.feedRepository.getNames();
        mImageUrls = this.feedRepository.getImageUrls();
        mNums = this.feedRepository.getNums();*/
    }

    public Observable<String> getFeed() {
        return observableFeed;
    }

    public ArrayList<String> getNames() {
        return feedRepository.getNames();
    }

    public ArrayList<String> getImageUrls() {
        return feedRepository.getImageUrls();
    }

    public ArrayList<Integer> getNums() {
        return feedRepository.getNums();
    }
}
