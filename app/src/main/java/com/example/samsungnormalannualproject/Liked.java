package com.example.samsungnormalannualproject;

import java.lang.reflect.Type;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.example.samsungnormalannualproject.Models.RegisteredUser;
import com.example.samsungnormalannualproject.Models.RegisteredUsers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Liked#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Liked extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<ExampleItem> mExampleList;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<RegisteredUser> registeredUsers;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Liked() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chats.
     */
    // TODO: Rename and change types and number of parameters
    public static Liked newInstance(String param1, String param2) {
        Liked fragment = new Liked();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        getLikedUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_liked, container, false);

        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.ic_show_more_button_with_three_dots, "Hello", "Line 2"));

        // Add the following lines to create RecyclerView

        loadLocalData();
        mRecyclerView = v.findViewById(R.id.recyclerview);

        return v;
    }

    private void loadLocalData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.likedSharedPreferences), Context.MODE_PRIVATE);
        String stringRegisteredUsers = sharedPreferences.getString(getString(R.string.liked), "");
        Log.d("Local liked", stringRegisteredUsers);
        Type userListType = new TypeToken<ArrayList<RegisteredUser>>(){}.getType();
        if (stringRegisteredUsers == "") {
            return;
        } else {
            this.registeredUsers = new Gson().fromJson(stringRegisteredUsers, userListType);
            Log.d("Username", registeredUsers.get(0).username);
        }
    }

    private void getLikedUsers() {
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        String JWTToken = sharedPref.getString(getString(R.string.JWTToken), "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        Call<RegisteredUsers> call = jsonPlaceHolderApi.getLikedUsers("Bearer " + JWTToken);

        call.enqueue(new Callback<RegisteredUsers>() {
            @Override
            public void onResponse(Call<RegisteredUsers> call, Response<RegisteredUsers> response) {
                Log.d("Response code", String.valueOf(response.code()));
                Log.d("GetUserDataResponse", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if (response.code() == 401) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences(getString(R.string.likedSharedPreferences), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.liked), new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getRegisteredUsers()));
                    editor.commit();
                    setRegisteredUsers(response.body().getRegisteredUsers());
                }
            }

            @Override
            public void onFailure(Call<RegisteredUsers> call, Throwable t) {
                Log.e("UserGetData", t.getMessage());
            }
        });
    }

    private void setRegisteredUsers(List<RegisteredUser> registeredUsers) {
        this.registeredUsers = registeredUsers;
        Log.d("User: ", String.valueOf(this.registeredUsers.get(0).username));
        buildRecyclerView();
    }

//    public void insertItem(int position) {
//        mExampleList.add(position, new ExampleItem(R.drawable.ic, "New Item At Position" + position, "This is Line 2"));
//        mAdapter.notifyItemInserted(position);
//    }

    public void changeItem(int position, String social) {
        Log.d("item clicked", String.valueOf(position) + "; " + social);
        Uri uri = null;
        if (social == "instagram") {
            uri = Uri.parse(this.registeredUsers.get(position).instagramProfle);
        }
        if (social == "vk") {
            uri = Uri.parse(this.registeredUsers.get(position).vkProfile);
        }
        if (social == "facebook") {
            uri = Uri.parse(this.registeredUsers.get(position).facebookProfile);
        }
        if (uri == null) {
            return;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(browserIntent);
        this.mAdapter.notifyItemChanged(position);
    }

    public void buildRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExampleAdapter(getActivity(), this.registeredUsers);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String social) {
                changeItem(position, social);
            }

            @Override
            public void onDeleteClick(int position) {
//                removeItem(position);
            }
        });
    }

}



