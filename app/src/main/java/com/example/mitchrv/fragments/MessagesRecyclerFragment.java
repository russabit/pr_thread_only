package com.example.mitchrv.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitchrv.APIs.DvachMessageAPI;
import com.example.mitchrv.APIs.InterfaceMainActivity;
import com.example.mitchrv.CustomInterceptor;
import com.example.mitchrv.R;
import com.example.mitchrv.adapters.MessagesRecyclerViewAdapter;
import com.example.mitchrv.adapters.RecyclerViewAdapter;
import com.example.mitchrv.model.Files;
import com.example.mitchrv.model.Messages;
import com.example.mitchrv.viewmodels.MessageRecyclerFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagesRecyclerFragment extends Fragment implements RecyclerViewAdapter.OnViewListener {

    private static final String TAG = "RecyclerMessageFragment";

    private static final String BASE_URL = "https://2ch.hk/";

    private MessagesRecyclerViewAdapter msgRecyclerViewAdapter;
    private InterfaceMainActivity mInterfaceMainActivity;
    private MessageRecyclerFragmentViewModel mMessageRecyclerFragmentViewModel;

    private ArrayList<String> mComments = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    public void onViewClick(int position) {
        Log.d(TAG, "onViewClick: clicked!");
        //mInterfaceMainActivity.inflateFragment(mImageUrls.get(position), mComments.get(position), );
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: called");
        mInterfaceMainActivity = (InterfaceMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messages_recycler, null);
        int num = getArguments().getInt("num");
        Log.d(TAG, "onCreateView: called " + num);
        initImageBitmaps(view, num);
        return view;
    }

    private void initImageBitmaps(View view, int num) {

        mMessageRecyclerFragmentViewModel = ViewModelProviders.of(this).get(MessageRecyclerFragmentViewModel.class);

        mMessageRecyclerFragmentViewModel.init(num);

        mMessageRecyclerFragmentViewModel.getMessages().subscribe(new Observer<Messages>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onNext(Messages s) {
                Log.d(TAG, "onNext: " + mComments.size());
                //msgRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() { Log.d(TAG, "on complete: mComments.size() = " + mComments.size());
                Log.d(TAG, "on complete: mImageUrls.size = " + mImageUrls.size());
                msgRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        initRecyclerView(view);

    }

    private void initRecyclerView(View view) {

        Log.d(TAG, "initRecyclerView: called");

        RecyclerView msgRecyclerView = view.findViewById(R.id.msg_recycler);
        msgRecyclerView.setHasFixedSize(false);
        msgRecyclerViewAdapter = new MessagesRecyclerViewAdapter(getActivity(), mMessageRecyclerFragmentViewModel.getComments(), mMessageRecyclerFragmentViewModel.getImageUrls(), this);
        msgRecyclerView.setAdapter(msgRecyclerViewAdapter);
        msgRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //adds a divider line between the rows
        msgRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

    }

    public static MessagesRecyclerFragment newInstance(String imageUrl, String imageName, int num) {

        Bundle args = new Bundle();
        args.putString("image_url", imageUrl);
        args.putString("image_name", imageName);
        args.putInt("num", num);

        Log.d(TAG, "newInstance: called");

        MessagesRecyclerFragment fragment = new MessagesRecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMessageRecyclerFragmentViewModel.clearComments();
        mMessageRecyclerFragmentViewModel.clearImages();
    }
}
