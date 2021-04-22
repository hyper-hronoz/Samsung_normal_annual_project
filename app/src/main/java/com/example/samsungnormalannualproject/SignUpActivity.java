package com.example.samsungnormalannualproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.API.NetworkServiceResponse;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.example.samsungnormalannualproject.Models.User;
import com.example.samsungnormalannualproject.Utils.InputDataValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends BaseActivity {

    private View view;
    private Button confirmButton;
    private EditText loginEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;

    private boolean isMen;

    private String login;
    private String password;
    private String password_2;

    private String gender;

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

        setContentView(R.layout.activity_sign_up);

        this.gender = SelectGenderActivity.gender;

        // ну здесь мы присваем значения для полей
        this.view = (View) findViewById(R.id.sign_up_view);
        this.confirmButton = (Button) findViewById(R.id.login_confirm);
        this.loginEditText = (EditText) findViewById(R.id.login_login);
        this.passwordEditText = (EditText) findViewById(R.id.login_password);
        this.passwordAgainEditText = (EditText) findViewById(R.id.editText_password_again);

        setBackGround();

        BaseActivity.activityHeading = findViewById(R.id.activity_heading);


        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hello i work");
                getEditTextData();
            }
        });
    }

    public class Register {
        User user;
        public Register(User user) {
            this.user = user;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

            Call<NetworkServiceResponse> call = jsonPlaceHolderApi.registerUser(user);

            call.enqueue(new Callback<NetworkServiceResponse>() {
                @Override
                public void onResponse(Call<NetworkServiceResponse> call, Response<NetworkServiceResponse> response) {
                    System.out.println(response);
                }

                @Override
                public void onFailure(Call<NetworkServiceResponse> call, Throwable t) {
                    System.out.println(t);
                    System.out.println("failuuuuuuuuuuuuuuuuure");
                }
            });
        }
    }

    private void getEditTextData() {
        this.login = this.loginEditText.getText().toString();
        this.password = this.passwordEditText.getText().toString();
        this.password_2 = this.passwordAgainEditText.getText().toString();

        InputDataValidator inputDataValidator = new InputDataValidator(this.login, this.password, this.password_2);

        String response = inputDataValidator.validate();
        if (response == "OK") {
            postNewUser();
        } else {
            new ToastError(getApplicationContext(), response);
        }
//        Intent intent = new Intent(this, SignUpForm.class);
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(SelectGenderActivity.GENDER, this.isMen);
//        intent.putExtras(bundle);
//        startActivity(intent);

    }


    public void postNewUser(){
        User user = new User(this.login, this.password, "M");
        new Register(user);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

//        String url = "http://192.168.0.15:3000/auth/registration";
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_LONG).show();
//
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
//                params.put("username", nickName);
//                params.put("password", password);
//                return params;
//            }
//
////            @Override
////            public Map<String, String> getHeaders() {
////                HashMap<String, String> params = new HashMap<String, String>();
////                params.put("Content-Type", "application/json");
////                return params;
////            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
    }
}
