package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.samsungnormalannualproject.interfaces.ActivitySettings;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends BaseActivity {

    private View view;
    private Button confirmButton;
    private EditText nickNameEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;

    private boolean isMen;

    private String nickName;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);


        // ну здесь мы присваем значения для полей
        this.view = (View) findViewById(R.id.sign_up_view);
        this.confirmButton = (Button) findViewById(R.id.login_confirm);
        this.nickNameEditText = (EditText) findViewById(R.id.login_login);
        this.passwordEditText = (EditText) findViewById(R.id.login_password);
        this.passwordAgainEditText = (EditText) findViewById(R.id.editText_password_again);

        this.isMen = getIntent().getExtras().getBoolean(SelectGenderActivity.GENDER);


        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditTextData();
            }
        });
    }

    private void getEditTextData() {
        if (this.passwordEditText.getText().toString().equals(this.passwordAgainEditText.getText().toString())) {
            this.nickName = this.nickNameEditText.getText().toString();
            this.password = this.passwordEditText.getText().toString();
            Intent intent = new Intent(this, SignUpForm.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(SelectGenderActivity.GENDER, this.isMen);
            intent.putExtras(bundle);
            startActivity(intent);
//            postNewUser();

        } else {
            System.out.println("Не совпадают");
        }
    }

    public void postNewUser(){
        String url = "https://heavy-moose-70.loca.lt/auth/registration";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", nickName);
                params.put("password", password);
                return params;
            }

//            @Override
//            public Map<String, String> getHeaders() {
//                HashMap<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json");
//                return params;
//            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
