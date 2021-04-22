package com.example.samsungnormalannualproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import retrofit2.Call;

import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.API.NetworkServiceResponse;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.example.samsungnormalannualproject.Models.User;
import com.example.samsungnormalannualproject.Utils.InputDataValidator;
import com.google.gson.GsonBuilder;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {

    private String login;
    private String password;

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button button;

    private String gender;
    private View view;

    private void setBackGround() {

        System.out.println(this.gender);

        try {
            if (this.gender == getString(R.string.genderMan)) {
                this.view.setBackgroundResource(R.drawable.select_gender_gradient_boy);
            }
            if (this.gender == getString(R.string.genderWoman)) {
                this.view.setBackgroundResource(R.drawable.select_gender_gradient_girl);
            }
        } catch (Exception e) {
            return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        System.out.println(this.loginEditText);

        this.view = (View) findViewById(R.id.login_view);
        this.loginEditText = findViewById(R.id.login_login);
        this.passwordEditText = findViewById(R.id.login_password);
        this.button = findViewById(R.id.login_confirm);

        this.gender = SelectGenderActivity.gender;

        setBackGround();

        BaseActivity.activityHeading = findViewById(R.id.activity_heading);

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        this.login = this.loginEditText.getText().toString();
        this.password = this.passwordEditText.getText().toString();


        InputDataValidator inputDataValidator = new InputDataValidator(this.login, this.password);

        String response = inputDataValidator.validate();
        if (response == "OK") {
            User user = new User(this.login, this.password);

            new Login(user, getApplicationContext());


        } else {
            new ToastError(getApplicationContext(), response);
        }
    }

    public class Login {
        private String JWTToken;
        User user;
        public Login(User user, Context context) {
            this.user = user;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

            Call<NetworkServiceResponse> call = jsonPlaceHolderApi.loginUser(user);

            call.enqueue(new Callback<NetworkServiceResponse>() {
                @Override
                public void onResponse(Call<NetworkServiceResponse> call, Response<NetworkServiceResponse> response) {
                    System.out.println("Network service response is: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    String jwt = new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getResponse()).replaceAll("^.|.$", "");
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.JWTToken), jwt);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), UserDataActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<NetworkServiceResponse> call, Throwable t) {
                    System.out.println("Failure");
                }
            });
        }
    }



//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("username", login);
//                params.put("password", password);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
}