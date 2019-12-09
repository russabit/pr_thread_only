package com.example.mitchrv.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mitchrv.R;

import java.util.ArrayList;

public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mComments = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    private RecyclerViewAdapter.OnViewListener mOnViewListener;

    public MessagesRecyclerViewAdapter(Context mContext, ArrayList<String> mComments, ArrayList<String> mImages, RecyclerViewAdapter.OnViewListener mOnViewListener) {
        this.mContext = mContext;
        this.mComments = mComments;
        this.mImages = mImages;
        this.mOnViewListener = mOnViewListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (!mImages.get(position).equals("nothing there")) return R.layout.layout_listitem_msg_img;
        else return R.layout.layout_listitem_msg;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final RecyclerView.ViewHolder viewHolder;
        View view;

        Log.d(TAG, "onCreateViewHolder: called.");

        if (viewType == R.layout.layout_listitem_msg_img) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_msg_img, parent, false);
            viewHolder = new ImageViewHolder(view, mOnViewListener);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_msg, parent, false);
            viewHolder = new ViewHolder(view, mOnViewListener);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        if (viewHolder instanceof ImageViewHolder) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(((ImageViewHolder) viewHolder).image);

        ((ImageViewHolder) viewHolder).comment.setText(Html.fromHtml(mComments.get(position)));
        }
        else if (viewHolder instanceof ViewHolder) {
            ((ViewHolder) viewHolder).comment.setText(Html.fromHtml(mComments.get(position)));
        }

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView comment;
        RelativeLayout parentLayout;

        RecyclerViewAdapter.OnViewListener onViewListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewAdapter.OnViewListener onViewListener) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment_msg);
            parentLayout = itemView.findViewById(R.id.msg_layout);
            this.onViewListener = onViewListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onViewListener.onViewClick(getAdapterPosition());
        }
    }

    //Another ViewHolder for OP post and posts with image
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView comment;
        ConstraintLayout parentLayout;

        RecyclerViewAdapter.OnViewListener onViewListener;

        public ImageViewHolder(@NonNull View itemView, RecyclerViewAdapter.OnViewListener onViewListener) {
            super(itemView);
            image = itemView.findViewById(R.id.image_msg);
            comment = itemView.findViewById(R.id.comment_msg_img);
            parentLayout = itemView.findViewById(R.id.msg_img_layout);
            this.onViewListener = onViewListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onViewListener.onViewClick(getAdapterPosition());
        }
    }

    //better way to implement onClick:

    public interface OnMessageListener {
        void onViewClick(int position);
    }
}
