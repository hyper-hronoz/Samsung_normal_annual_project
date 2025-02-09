package com.example.samsungnormalannualproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.API.NetworkServiceResponse;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.example.samsungnormalannualproject.Models.User;
import com.example.samsungnormalannualproject.Utils.InputDataValidator;

import org.w3c.dom.Text;

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

    private TextView haveAccount;

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
        this.haveAccount = (TextView) findViewById(R.id.haveAccount);

        setBackGround();

        BaseActivity.activityHeading = findViewById(R.id.activity_heading);


        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hello i work");
                getEditTextData();
            }
        });

        this.haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public class Register {
        public User user;

        public Register(User user) {

            Log.d("User gender", user.gender);
            Log.d("User username", user.username);
            Log.d("user userpassword", user.password);

            this.user = user;

            setContentView(R.layout.activity_loading);
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
                    if (response.code() == 409) {
                        new ToastError(getApplicationContext(), "User already exists");
                    }
                    else if (response.code() == 400) {
                        new ToastError(getApplicationContext(), "Internal server error");
                    }
                    else if (response.code() == 200) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        new ToastError(getApplicationContext(), "Server is down, we are working to solve this problem");
                    }
                }

                @Override
                public void onFailure(Call<NetworkServiceResponse> call, Throwable t) {
                    Log.e("Sign up failre", t.getMessage());
                    Intent intent = new Intent(getApplicationContext(), UserDataActivity.class);
                    startActivity(intent);
                    finish();
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
    }

    public void postNewUser() {
        User user = new User(this.login, this.password, SelectGenderActivity.gender);
        new Register(user);
    }
}
