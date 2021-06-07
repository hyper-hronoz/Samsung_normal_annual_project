package com.example.samsungnormalannualproject;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.API.NetworkServiceResponse;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadImageActivity extends BaseActivity {
    private ImageView uploadProfilePhoto;
    private Button uploadButton;
    private String part_image;

    private Uri selectedImage;

    private static final int PERMISSIONS_CODE = 1001;
    private static final int IMAGE_PICK_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload_image);

        this.uploadProfilePhoto = findViewById(R.id.uploadProfilePhoto);

        this.uploadButton = findViewById(R.id.uploadButton);

        this.uploadProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        this.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    uploadImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    static final int REQUEST_IMAGE_GALLERY = 1;

    private void dispatchTakePictureIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_GALLERY);//one can be replaced with any action code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            this.selectedImage = data.getData();
            String[] imageprojection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(this.selectedImage,imageprojection,null,null,null);

            if (cursor != null)
            {
                cursor.moveToFirst();
                int indexImage = cursor.getColumnIndex(imageprojection[0]);
                this.part_image = cursor.getString(indexImage);

                if(this.part_image != null)
                {
                    File image = new File(part_image);
                    this.uploadProfilePhoto.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
                }
            }
        }
    }


    private void uploadImage() throws IOException {
        File file = new File(part_image);
        System.out.println(file.length());

        if (file.length() == 0)
            System.out.println("File is empty!!!");
        else
            System.out.println("File is not empty!!!");

//        OkHttpClient client = new OkHttpClient.Builder()
//                .readTimeout(2, TimeUnit.MINUTES)
//                .writeTimeout(2, TimeUnit.MINUTES).addInterceptor(chain -> {
//                    Request original = chain.request();
//                    Request.Builder requestBuilder = requestBuilder = original.newBuilder()
//                            .method(original.method(), original.body());
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request);
//                }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse(getContentResolver().getType(this.selectedImage)), file);

        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

        Call<ResponseBody> call = jsonPlaceHolderApi.upload(description, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });

//        String selectedFilePath = FilePath.getPath(getActivity(), uri);
//        System.out.println(file.getName());
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//        this.uploadProfilePhoto.setImageBitmap(bitmap);
//        Log.d("File uri", String.valueOf(this.fileUri));
//        Log.d("file path", UriUtils.getPathFromUri(this, this.fileUri));
//        File file = new File(UriUtils.getPathFromUri(this,this.fileUri));
//        Log.d("file absolute path", file.getAbsolutePath());
//
//        if (file.length() == 0)
//            System.out.println("File is empty!!!");
//        else
//            System.out.println("File is not empty!!!");

//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), this.fileUri.getPath());
//
//        MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);

//        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
//        String JWTToken = sharedPref.getString(getString(R.string.JWTToken), "");
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(NetworkConfig.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);
//
//        UploadImage uploadImage = new UploadImage(this.filePath);
//
//        Log.d("JWT is" , JWTToken);
//
//        Call<UploadImage> call = jsonPlaceHolderApi.postImage("Bearer " + JWTToken, uploadImage);
//
//        call.enqueue(new Callback<UploadImage>() {
//            @Override
//            public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {
//                Log.d("Login status code is", String.valueOf(response.code()));
//                Log.d("Login response body is", String.valueOf(response.body()));
//                if (response.code() == 401) {
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), UserDataActivity.class);
//                    startActivity(intent);
//                }
//            }
//            @Override
//            public void onFailure(Call<UploadImage> call, Throwable t) {
//                System.out.println("Failure");
//                Log.e("Login error ", t.getMessage());
//                Intent intent = new Intent(getApplicationContext(), UserDataActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}


