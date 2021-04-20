package com.example.samsungnormalannualproject.Erors.UserErrors;

import android.content.Context;
import android.widget.Toast;

public class ToastError {
    private Context context;
    private String error;

    public ToastError(Context context, String error) {
        this.context = context;
        this.error = error;

        Toast.makeText(context, this.error, Toast.LENGTH_SHORT).show();
    }
}
