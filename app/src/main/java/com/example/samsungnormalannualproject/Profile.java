package com.example.samsungnormalannualproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.Models.RegisteredUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton logoutButton;
    private ImageButton settingButton;

    public Profile() {
        // Required empty public constructor
    }

    private TextView nominationAgeTextView;
    private ImageView profilePhotoImageView;
    private TextView aboutTextView;
    private TextView nominationHeadingTextView;
    private RegisteredUser registeredUser;
    private ImageButton likedUsers;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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

    }

    private void logOut() {
        Log.d("Key is", getString(R.string.JWTTokenSharedPreferencesKey));
        SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();

        SharedPreferences sharedPref2 = getActivity().getApplicationContext().getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.clear().commit();
        editor2.apply();

        Intent intent = new Intent(getContext(), LoginOrSignUp.class);
        startActivity(intent);

        getActivity().finish();
    }

    private void getCurrentUserData() {
        SharedPreferences sharedPrefToken = getContext().getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        String JWTToken = sharedPrefToken.getString(getString(R.string.JWTToken), "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        Call<RegisteredUser> call = jsonPlaceHolderApi.getUserData("Bearer "+ JWTToken);

        call.enqueue(new Callback<RegisteredUser>() {
            @Override
            public void onResponse(Call<RegisteredUser> call, Response<RegisteredUser> response) {
                Log.d("Response code", String.valueOf(response.code()));
                Log.d("GetUserDataResponse", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if (response.code() == 401) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.userSharedPreferencesKey), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.userData), new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                    if (response.body().getUserPhoto() == "" || response.body().getUserPhoto() == null) {
                        Intent intent = new Intent(getContext(), UploadImageActivity.class);
                        startActivity(intent);
                    }
                    else if (response.body().getAge() == 0 && response.body().height == 0 && (response.body().hairColor == null || response.body().hairColor.trim() == "")) {
                        Intent intent = new Intent(getContext(), SignUpForm.class);
                        startActivity(intent);
                    } else {
                        setCurrentUserData(response.body());
                    }
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<RegisteredUser> call, Throwable t) {
                Log.e("UserGetData", "сервер кажись лежит");
                Log.e("Login error ", t.getMessage());
                Intent intent = new Intent(getActivity().getApplicationContext(), UserDataActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void setCurrentUserData(RegisteredUser registeredUser) {
        this.nominationHeadingTextView.setText(registeredUser.getUsername());
        this.nominationAgeTextView.setText("age " + String.valueOf(registeredUser.getAge()));
        this.aboutTextView.setText(registeredUser.getAboutUser());
        if (URLUtil.isValidUrl(registeredUser.getUserPhoto())) {
            Glide.with(getContext()).load(registeredUser.getUserPhoto()).into(this.profilePhotoImageView);
        } else {
            Glide.with(getContext()).load("https://st3.depositphotos.com/4111759/13425/v/450/depositphotos_134255710-stock-illustration-avatar-vector-male-profile-gray.jpg").into(this.profilePhotoImageView);
        }
    }

    private void getLocalUserData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.userSharedPreferencesKey), Context.MODE_PRIVATE);
        String StringregisteredUser = sharedPreferences.getString(getString(R.string.userData), "");
        Log.d("Local user data", StringregisteredUser);
        if (StringregisteredUser == "") {

        } else {
            this.registeredUser = new Gson().fromJson(StringregisteredUser, RegisteredUser.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        this.aboutTextView = v.findViewById(R.id.about);
        this.nominationAgeTextView = v.findViewById(R.id.nomination_age);
        this.nominationHeadingTextView = v.findViewById(R.id.nomination_heading);
        this.profilePhotoImageView = v.findViewById(R.id.profilePhoto);
        this.logoutButton = v.findViewById(R.id.logout);
        this.settingButton = v.findViewById(R.id.settings);
        this.likedUsers = v.findViewById(R.id.liked);


        this.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        this.settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SignUpForm.class);
                startActivity(intent);
            }
        });


        getCurrentUserData();
        getLocalUserData();

//        Intent i = new Intent(LoginOrSignUp., Profile.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        startActivity(i);

//        this.likedUsers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), LikedUsers.class);
//                startActivity(intent);
//            }
//        });


        return v;
    }
}