package com.example.samsungnormalannualproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.example.samsungnormalannualproject.Models.RegisteredUser;
import com.example.samsungnormalannualproject.Models.UploadImage;
import com.example.samsungnormalannualproject.Models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchNominations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchNominations extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private TextView nominationAgeTextView;
    private ImageView profilePhotoImageView;
    private TextView aboutTextView;
    private TextView nominationHeadingTextView;
    private ImageButton likeImageButton;

    public static Context context;

    private RegisteredUser registeredUser;

    public SearchNominations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchNominations.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchNominations newInstance(String param1, String param2) {
        SearchNominations fragment = new SearchNominations();
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
        context = getContext();
    }


    private void getRandomUser() {
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        String JWTToken = sharedPref.getString(getString(R.string.JWTToken), "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        Log.d("JWT is" , JWTToken);

        Call<RegisteredUser> call = jsonPlaceHolderApi.findUser("Bearer " + JWTToken);

        call.enqueue(new Callback<RegisteredUser>() {
            @Override
            public void onResponse(Call<RegisteredUser> call, Response<RegisteredUser> response) {
                Log.d("Search status code is", String.valueOf(response.code()));
                Log.d("Search response body is", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if (response.code() == 401) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else if (response.code() == 500) {
                    new ToastError(getContext(), "we apologize, but the user cannot be found INTERNAL SERVER ERROR");
                } else {
                    setFindUser(response);
                }
            }
            @Override
            public void onFailure(Call<RegisteredUser> call, Throwable t) {
                Log.e("Search nominat error", t.getMessage());
            }
        });

    }

    private void setFindUser(Response<RegisteredUser> response) {
        this.liked = false;
        this.registeredUser = response.body();
        this.nominationHeadingTextView.setText(response.body().getUsername());
        this.nominationAgeTextView.setText("age " + String.valueOf(response.body().getAge()));
        this.aboutTextView.setText(response.body().getAboutUser());
        if (URLUtil.isValidUrl(response.body().getUserPhoto())) {
            Glide.with(context).load(response.body().getUserPhoto()).into(this.profilePhotoImageView);
        } else {
            Glide.with(context).load("https://st3.depositphotos.com/4111759/13425/v/450/depositphotos_134255710-stock-illustration-avatar-vector-male-profile-gray.jpg").into(this.profilePhotoImageView);
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.userSharedPreferencesKey), Context.MODE_PRIVATE);
        String userData = sharedPreferences.getString(getString(R.string.userData), "");
        Log.d("User data is ", userData);

        RegisteredUser registeredUser = new Gson().fromJson(userData, RegisteredUser.class);

        if (registeredUser.getUserLiked().contains(response.body().username)) {
            this.liked = true;
            Log.d("if user liked", "лайкнут уже");
        }

        ifLiked();

    }

//    private void setImage(Response<RegisteredUser> response) {
//        if (getActivity() == null) {
//            return;
//        }
//    }

    private boolean liked;



    private void ifLiked() {
        if (liked) {
            this.likeImageButton.setColorFilter(getResources().getColor(R.color.red));
        } else {
            this.likeImageButton.setColorFilter(getResources().getColor(R.color.black));
        }
    }

    private void like() {
        Log.d("liked" ,"true");
        if (this.liked) {
            this.liked = false;
            ifLiked();
        } else {
            this.liked = true;
            ifLiked();
        }

        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        String JWTToken = sharedPref.getString(getString(R.string.JWTToken), "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        Log.d("JWT is" , JWTToken);

        Call<RegisteredUser> call = jsonPlaceHolderApi.likeUser("Bearer " + JWTToken, this.registeredUser);

        call.enqueue(new Callback<RegisteredUser>() {
            @Override
            public void onResponse(Call<RegisteredUser> call, Response<RegisteredUser> response) {
                Log.d("Search status code is", String.valueOf(response.code()));
                Log.d("Search response body is", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if (response.code() == 401) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {

                }
            }
            @Override
            public void onFailure(Call<RegisteredUser> call, Throwable t) {
                Log.e("Search error", t.getMessage());
                Intent intent = new Intent(getActivity().getApplicationContext(), UserDataActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_nominations, container, false);

        this.aboutTextView = v.findViewById(R.id.about);
        this.nominationAgeTextView = v.findViewById(R.id.nomination_age);
        this.nominationHeadingTextView = v.findViewById(R.id.nomination_heading);
        this.profilePhotoImageView = v.findViewById(R.id.profilePhoto);
        this.likeImageButton = v.findViewById(R.id.like);

        v.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
//                Toast.makeText(getActivity(), "TOP SWIPE", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
//                Toast.makeText(getActivity(), "RIGHT SWIPE", Toast.LENGTH_SHORT).show();
                //go back to landing page
                // Intent intent = new Intent (getApplicationContext(), MainScreen.class);
                // startActivity (intent);
            }

            public void onSwipeLeft() {
                getRandomUser();
            }

            public void onSwipeBottom() {
//                Toast.makeText(getActivity(), "BOTTOM SWIPE", Toast.LENGTH_SHORT).show();
            }
        });

        this.likeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like();
            }
        });


        getRandomUser();

        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        String JWTToken = sharedPref.getString(getString(R.string.JWTToken), "");

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
                    Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences(getString(R.string.userSharedPreferencesKey), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.userData), new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                    editor.commit();
                }

            }

            @Override
            public void onFailure(Call<RegisteredUser> call, Throwable t) {
                Log.e("UserGetData", "сервер кажись лежит");
            }
        });
        return v;
    }
}