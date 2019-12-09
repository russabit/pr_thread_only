package com.example.mitchrv.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mitchrv.R;

public class InfoFragment extends Fragment {
    private String imageUrl, imageName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        imageUrl = getArguments().getString("image_url");
        imageName = getArguments().getString("image_name");

        View view = inflater.inflate(R.layout.fragment_info, null);

        setImage(view, imageUrl, imageName);

        return view;
    }

    public static InfoFragment newInstance(String imageUrl, String imageName) {
        
        Bundle args = new Bundle();
        args.putString("image_url", imageUrl);
        args.putString("image_name", imageName);
        
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setImage(View view, String imageUrl, String imageName) {

        TextView name = view.findViewById(R.id.image_description);
        name.setText(imageName);

        ImageView imageView = view.findViewById(R.id.image);

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(imageView);

    }
}
