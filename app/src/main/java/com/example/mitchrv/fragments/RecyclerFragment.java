package com.example.mitchrv.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitchrv.APIs.DvachAPI;
import com.example.mitchrv.APIs.InterfaceMainActivity;
import com.example.mitchrv.R;
import com.example.mitchrv.adapters.RecyclerViewAdapter;
import com.example.mitchrv.model.Feed;
import com.example.mitchrv.viewmodels.RecyclerFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerFragment extends Fragment implements RecyclerViewAdapter.OnViewListener {

    private static final String TAG = "RecyclerFragment";

    private RecyclerViewAdapter recyclerViewAdapter;
    private InterfaceMainActivity mInterfaceMainActivity;
    private RecyclerFragmentViewModel mRecyclerFragmentViewModel;


    @Override
    public void onViewClick(int position) {
        Log.d(TAG, "onViewClick: clicked!");
        mInterfaceMainActivity.inflateFragment(
                mRecyclerFragmentViewModel.getImageUrls().get(position),
                mRecyclerFragmentViewModel.getNames().get(position),
                mRecyclerFragmentViewModel.getNums().get(position));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mInterfaceMainActivity = (InterfaceMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, null);

        initImageBitmaps(view);

        return view;
    }

    private void initImageBitmaps(View view) {


        //this or getActivity() inside of()
        mRecyclerFragmentViewModel = ViewModelProviders.of(this).get(RecyclerFragmentViewModel.class);


        mRecyclerFragmentViewModel.init();

        //was observable.subscribe(...
        mRecyclerFragmentViewModel.getFeed().subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(String path) {
                Log.d(TAG, "onNext: called");
                //recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() {
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        initRecyclerView(view);

    }

    private void initRecyclerView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        //change here mNames, mImageUrls to mRecyclerFragmentViewModel.getFeed().get... etc
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), mRecyclerFragmentViewModel.getNames(), mRecyclerFragmentViewModel.getImageUrls(), this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //adds a divider line between the rows
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

    }


}
