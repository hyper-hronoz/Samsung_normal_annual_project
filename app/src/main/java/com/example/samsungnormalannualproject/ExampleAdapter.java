package com.example.samsungnormalannualproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungnormalannualproject.Models.RegisteredUser;
import com.example.samsungnormalannualproject.Models.RegisteredUsers;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private List<RegisteredUser> mExampleList;
    private OnItemClickListener mListener;
    private Activity activity;
    private List<RegisteredUser> registeredUsers;

    public interface OnItemClickListener {
        void onItemClick(int position, String social);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView userPhoto;
        public TextView userHeading;

        public ImageView facebook;
        public ImageButton vk;
        public ImageButton instagram;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.userPhoto = itemView.findViewById(R.id.profile);
            this.userHeading = itemView.findViewById(R.id.user_heading);
            this.instagram = itemView.findViewById(R.id.instagram);
            this.facebook = itemView.findViewById(R.id.facebook);
            this.vk = itemView.findViewById(R.id.vk);

            if (URLUtil.isValidUrl( "" )) {


            }

            instagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("id is : ", String.valueOf(v.getId()));
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, "instagram");
                        }
                    }
                }
            });
            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("id is : ", String.valueOf(v.getId()));
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, "facebook");
                        }
                    }
                }
            });
            vk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("id is : ", String.valueOf(v.getId()));
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, "vk");
                        }
                    }
                }
            });
        }
    }
    public ExampleAdapter(Activity activity, List<RegisteredUser> registeredUser) {
        this.mExampleList = registeredUser;
        this.activity = activity;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_like_user, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        RegisteredUser currentItem = mExampleList.get(position);
        holder.userHeading.setText(currentItem.getUsername());
        if (!URLUtil.isValidUrl(currentItem.instagramProfle)) {
            holder.instagram.setVisibility(View.GONE);
        }

        if (!URLUtil.isValidUrl(currentItem.vkProfile)) {
            holder.vk.setVisibility(View.GONE);
        }

        if (!URLUtil.isValidUrl(currentItem.facebookProfile)) {
            holder.facebook.setVisibility(View.GONE);
        }

    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}