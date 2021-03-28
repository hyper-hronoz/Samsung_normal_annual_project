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

public class SignUpActivity extends AppCompatActivity {

    private View view;
    private Button confirmButton;
    private EditText nickNameEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;

    // скрывает боттом меню при потере фокуса
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    // скрывает топ меню и делаем приложения безрамочным
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
        boolean isMen = getIntent().getExtras().getBoolean(SelectGenderActivity.GENDER);

        System.out.println(this.view);

        if (isMen) {
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_boy);
            this.confirmButton.setBackgroundResource(R.drawable.login_regirstrate_button_background_boy);
        }

        if (!isMen) {
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_girl);
            this.confirmButton.setBackgroundResource(R.drawable.login_registrate_button_background_girl);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // сдесь у нас настройка активити берзрамочноая
        hideSystemUI();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        // ну здесь мы присваем значения для полей
        this.view = (View) findViewById(R.id.sign_up_view);
        this.confirmButton = (Button) findViewById(R.id.confirm_sign_up_button);
        this.nickNameEditText = (EditText) findViewById(R.id.editText_password);
        this.passwordEditText = (EditText) findViewById(R.id.editText_password);
        this.passwordAgainEditText = (EditText) findViewById(R.id.editText_password_again);

        this.setBackGround();
        this.keyBoardStateChangeListener();
    }

    private void keyBoardStateChangeListener() {
        this.view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        view.getWindowVisibleDisplayFrame(r);
                        int screenHeight = view.getRootView().getHeight();
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
