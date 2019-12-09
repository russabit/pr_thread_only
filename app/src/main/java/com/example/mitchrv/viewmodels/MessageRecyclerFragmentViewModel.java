package com.example.mitchrv.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.mitchrv.model.Messages;
import com.example.mitchrv.repositories.FeedRepository;
import com.example.mitchrv.repositories.MessagesRepository;

import java.util.ArrayList;

import io.reactivex.Observable;

public class MessageRecyclerFragmentViewModel extends ViewModel {

    private Observable<Messages> observableMessages;

/*    private ArrayList<String> mComments = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();*/

    private static final String BASE_URL = "https://2ch.hk/";

    private MessagesRepository messagesRepository;

    public void init(int num) {
        if (observableMessages!=null) {
            return;
        }
        messagesRepository = MessagesRepository.getInstance();
        observableMessages = this.messagesRepository.getMessages(num);
/*        mComments = this.messagesRepository.getComments();
        mImageUrls = this.messagesRepository.getImageUrls();*/
    }

    public Observable<Messages> getMessages() {
        return observableMessages;
    }

    public ArrayList<String> getComments() { return messagesRepository.getComments(); }

    public ArrayList<String> getImageUrls() {
        return messagesRepository.getImageUrls();
    }

    public void clearComments() {
        messagesRepository.removeComments();
    }

    public void clearImages() {
        messagesRepository.removeImages();
    }

}
