package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

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

public class SignUpActivity extends AppCompatActivity implements ActivitySettings, ActivityWithEditText {

    private View view;
    private Button confirmButton;
    private EditText nickNameEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;

    private boolean isMen;

    private String nickName;
    private String password;

    // скрывает боттом меню при потере фокуса
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    // скрывает топ меню и делаем приложения безрамочным
    @Override
    public void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
    }

    // паказывает нижнее меню когда приложение открыто
    public void showBottomNavBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_VISIBLE // именно это поле и меняет состояние нижнего меню
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
    }

    // устанавливает фон для элементов в зависимости от выбранного пола
    private void setBackGround() {

        System.out.println(this.view);

        if (this.isMen) {
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_boy);
            this.confirmButton.setBackgroundResource(R.drawable.login_regirstrate_button_background_boy);
        }

        if (!this.isMen) {
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_girl);
            this.confirmButton.setBackgroundResource(R.drawable.login_registrate_button_background_girl);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_sign_up);

        hideSystemUI();

        // ну здесь мы присваем значения для полей
        this.view = (View) findViewById(R.id.sign_up_view);
        this.confirmButton = (Button) findViewById(R.id.login_confirm);
        this.nickNameEditText = (EditText) findViewById(R.id.login_login);
        this.passwordEditText = (EditText) findViewById(R.id.login_password);
        this.passwordAgainEditText = (EditText) findViewById(R.id.editText_password_again);

        this.isMen = getIntent().getExtras().getBoolean(SelectGenderActivity.GENDER);

        this.setBackGround();
        this.keyBoardStateChangeListener();

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
            postNewUser();
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


    public interface PostCommentResponseListener {
        public void requestStarted();
        public void requestCompleted();
        public void requestEndedWithError(VolleyError error);
    }

    // слушаем открыта ли клава или нет
    @Override
    public void keyBoardStateChangeListener() {
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                        int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                        int keypadHeight = screenHeight - r.bottom;
                        if (keypadHeight > screenHeight * 0.15) {
                            showBottomNavBar();
                        } else {
                            hideSystemUI();
                        }
                    }
                });
    }

}
