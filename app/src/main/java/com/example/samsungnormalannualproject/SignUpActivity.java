package com.example.samsungnormalannualproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.samsungnormalannualproject.API.NetworkService;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.example.samsungnormalannualproject.Models.User;
import com.example.samsungnormalannualproject.Utils.FormValidator;

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

    private void setBackGround() {

        System.out.println(this.view);

        if (this.isMen) {
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_boy);
        }

        if (!this.isMen) {
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_girl);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);


        // ну здесь мы присваем значения для полей
        this.view = (View) findViewById(R.id.sign_up_view);
        this.confirmButton = (Button) findViewById(R.id.login_confirm);
        this.loginEditText = (EditText) findViewById(R.id.login_login);
        this.passwordEditText = (EditText) findViewById(R.id.login_password);
        this.passwordAgainEditText = (EditText) findViewById(R.id.editText_password_again);

        this.isMen = getIntent().getExtras().getBoolean(SelectGenderActivity.GENDER);
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

    private void getEditTextData() {
        this.login = this.loginEditText.getText().toString();
        this.password = this.passwordEditText.getText().toString();
        this.password_2 = this.passwordAgainEditText.getText().toString();

        InputDataValidator inputDataValidator = new InputDataValidator(this.login, this.password, this.password_2);
        if (inputDataValidator.validate()) {
            postNewUser();
        }
//        Intent intent = new Intent(this, SignUpForm.class);
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(SelectGenderActivity.GENDER, this.isMen);
//        intent.putExtras(bundle);
//        startActivity(intent);

    }

    private class InputDataValidator {
        private String login;
        private String password;
        private String password_2;

        private final int MINIMAL_LOGIN_LENGTH = 4;
        private final int MINIMAL_PASSWORD_LENGTH = 8;

        public InputDataValidator(String login, String password, String password_2) {
            this.login = login;
            this.password = password;
            this.password_2 = password_2;
        }

        public boolean validate() {

            if (this.password.equals(password_2)) {
                if (new FormValidator(this.login).setMinLength(this.MINIMAL_LOGIN_LENGTH)) {

                    if (new FormValidator(this.password).setMinLength(this.MINIMAL_PASSWORD_LENGTH))  {
                        return true;
                    } else {
                        new ToastError(getApplicationContext(), "Password minimal length: " + this.MINIMAL_PASSWORD_LENGTH);
                    }

                } else {
                    new ToastError(getApplicationContext(), "Login minimal length is: " + this.MINIMAL_LOGIN_LENGTH);
                }
            } else {
                new ToastError(getApplicationContext(), "Password do not match");
            }

            return false;
        }
    }

    public void postNewUser(){
        User user = new User(this.login, this.password);
        new NetworkService();
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
