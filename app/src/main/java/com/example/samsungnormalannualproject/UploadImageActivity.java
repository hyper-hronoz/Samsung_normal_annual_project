package com.example.samsungnormalannualproject;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.loader.content.CursorLoader;

import android.annotation.SuppressLint;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.API.NetworkServiceResponse;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.example.samsungnormalannualproject.Models.UploadImage;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private Uri fileUri;
    private int IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String filePath;

    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload_image);

        this.uploadProfilePhoto = findViewById(R.id.uploadProfilePhoto);

        this.uploadButton = findViewById(R.id.uploadButton);

        this.editText = findViewById(R.id.editText);


        this.uploadProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
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

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            this.fileUri = data.getData();
            this.filePath = data.getData().getPath();
            this.uploadProfilePhoto.setImageURI(fileUri);
        }
    }

    private void uploadImage() throws IOException {
        this.filePath = this.editText.getText().toString();
        Glide.with(getApplicationContext()).load(this.filePath).into(this.uploadProfilePhoto);
        Log.d("FIle path", this.filePath);
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

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        String JWTToken = sharedPref.getString(getString(R.string.JWTToken), "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        UploadImage uploadImage = new UploadImage(this.filePath);

        Log.d("JWT is" , JWTToken);

        Call<UploadImage> call = jsonPlaceHolderApi.postImage("Bearer " + JWTToken, uploadImage);

        call.enqueue(new Callback<UploadImage>() {
            @Override
            public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {
                Log.d("Login status code is", String.valueOf(response.code()));
                Log.d("Login response body is", String.valueOf(response.body()));
                if (response.code() == 401) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), BottomMenu.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<UploadImage> call, Throwable t) {
                System.out.println("Failure");
            }
        });
    }
}

//class UriUtils {
//    private static Uri contentUri = null;
//
//    @SuppressLint("NewApi")
//    public static String getPathFromUri(final Context context, final Uri uri) {
//        // check here to is it KITKAT or new version
//        final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//        String selection = null;
//        String[] selectionArgs = null;
//        // DocumentProvider
//        if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
//            // ExternalStorageProvider
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                String fullPath = getPathFromExtSD(split);
//                if (fullPath != "") {
//                    return fullPath;
//                } else {
//                    return null;
//                }
//            }
//
//            // DownloadsProvider
//            else if (isDownloadsDocument(uri)) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    final String id;
//                    Cursor cursor = null;
//                    try {
//                        cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DISPLAY_NAME}, null, null, null);
//                        if (cursor != null && cursor.moveToFirst()) {
//                            String fileName = cursor.getString(0);
//                            String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
//                            if (!TextUtils.isEmpty(path)) {
//                                return path;
//                            }
//                        }
//                    } finally {
//                        if (cursor != null)
//                            cursor.close();
//                    }
//                    id = DocumentsContract.getDocumentId(uri);
//                    if (!TextUtils.isEmpty(id)) {
//                        if (id.startsWith("raw:")) {
//                            return id.replaceFirst("raw:", "");
//                        }
//                        String[] contentUriPrefixesToTry = new String[]{
//                                "content://downloads/public_downloads",
//                                "content://downloads/my_downloads"
//                        };
//                        for (String contentUriPrefix : contentUriPrefixesToTry) {
//                            try {
//                                final Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
//
//                                return getDataColumn(context, contentUri, null, null);
//                            } catch (NumberFormatException e) {
//                                //In Android 8 and Android P the id is not a number
//                                return uri.getPath().replaceFirst("^/document/raw:", "").replaceFirst("^raw:", "");
//                            }
//                        }
//                    }
//
//                } else {
//                    final String id = DocumentsContract.getDocumentId(uri);
//                    if (id.startsWith("raw:")) {
//                        return id.replaceFirst("raw:", "");
//                    }
//                    try {
//                        contentUri = ContentUris.withAppendedId(
//                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//                    if (contentUri != null) {
//                        return getDataColumn(context, contentUri, null, null);
//                    }
//                }
//            }
//            // MediaProvider
//            else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//                selection = "_id=?";
//                selectionArgs = new String[]{split[1]};
//
//
//                return getDataColumn(context, contentUri, selection,
//                        selectionArgs);
//            } else if (isGoogleDriveUri(uri)) {
//                return getDriveFilePath(uri, context);
//            }
//        }
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//
//            if (isGooglePhotosUri(uri)) {
//                return uri.getLastPathSegment();
//            }
//
//            if (isGoogleDriveUri(uri)) {
//                return getDriveFilePath(uri, context);
//            }
//            if( Build.VERSION.SDK_INT == Build.VERSION_CODES.N)
//            {
//                return getMediaFilePathForN(uri, context);
//            }else
//            {
//                return getDataColumn(context, uri, null, null);
//            }
//        }
//        // File
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//    }
//
//    private static boolean fileExists(String filePath) {
//        File file = new File(filePath);
//
//        return file.exists();
//    }
//
//    private static String getPathFromExtSD(String[] pathData) {
//        final String type = pathData[0];
//        final String relativePath = "/" + pathData[1];
//        String fullPath = "";
//
//        if ("primary".equalsIgnoreCase(type)) {
//            fullPath = Environment.getExternalStorageDirectory() + relativePath;
//            if (fileExists(fullPath)) {
//                return fullPath;
//            }
//        }
//
//        fullPath = System.getenv("SECONDARY_STORAGE") + relativePath;
//        if (fileExists(fullPath)) {
//            return fullPath;
//        }
//
//        fullPath = System.getenv("EXTERNAL_STORAGE") + relativePath;
//        if (fileExists(fullPath)) {
//            return fullPath;
//        }
//
//        return fullPath;
//    }
//
//    private static String getDriveFilePath(Uri uri, Context context) {
//        Uri returnUri = uri;
//        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
//
//        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
//        returnCursor.moveToFirst();
//        String name = (returnCursor.getString(nameIndex));
//        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
//        File file = new File(context.getCacheDir(), name);
//        try {
//            InputStream inputStream = context.getContentResolver().openInputStream(uri);
//            FileOutputStream outputStream = new FileOutputStream(file);
//            int read = 0;
//            int maxBufferSize = 1 * 1024 * 1024;
//            int bytesAvailable = inputStream.available();
//
//            //int bufferSize = 1024;
//            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
//
//            final byte[] buffers = new byte[bufferSize];
//            while ((read = inputStream.read(buffers)) != -1) {
//                outputStream.write(buffers, 0, read);
//            }
//            Log.e("File Size", "Size " + file.length());
//            inputStream.close();
//            outputStream.close();
//            Log.e("File Path", "Path " + file.getPath());
//            Log.e("File Size", "Size " + file.length());
//        } catch (Exception e) {
//            Log.e("Exception", e.getMessage());
//        }
//        return file.getPath();
//    }
//
//    private static String getMediaFilePathForN(Uri uri, Context context) {
//        Uri returnUri = uri;
//        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
//
//        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
//        returnCursor.moveToFirst();
//        String name = (returnCursor.getString(nameIndex));
//        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
//        File file = new File(context.getFilesDir(), name);
//        try {
//            InputStream inputStream = context.getContentResolver().openInputStream(uri);
//            FileOutputStream outputStream = new FileOutputStream(file);
//            int read = 0;
//            int maxBufferSize = 1 * 1024 * 1024;
//            int bytesAvailable = inputStream.available();
//
//            //int bufferSize = 1024;
//            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
//
//            final byte[] buffers = new byte[bufferSize];
//            while ((read = inputStream.read(buffers)) != -1) {
//                outputStream.write(buffers, 0, read);
//            }
//            Log.e("File Size", "Size " + file.length());
//            inputStream.close();
//            outputStream.close();
//            Log.e("File Path", "Path " + file.getPath());
//            Log.e("File Size", "Size " + file.length());
//        } catch (Exception e) {
//            Log.e("Exception", e.getMessage());
//        }
//        return file.getPath();
//    }
//
//
//    private static String getDataColumn(Context context, Uri uri,
//                                        String selection, String[] selectionArgs) {
//        Cursor cursor = null;
//        final String column = "_data";
//        final String[] projection = {column};
//
//        try {
//            cursor = context.getContentResolver().query(uri, projection,
//                    selection, selectionArgs, null);
//
//            if (cursor != null && cursor.moveToFirst()) {
//                final int index = cursor.getColumnIndexOrThrow(column);
//                return cursor.getString(index);
//            }
//        } finally {
//            if (cursor != null)
//                cursor.close();
//        }
//
//        return null;
//    }
//
//    private static boolean isExternalStorageDocument(Uri uri) {
//        return "com.android.externalstorage.documents".equals(uri.getAuthority());
//    }
//
//    private static boolean isDownloadsDocument(Uri uri) {
//        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
//    }
//
//    private static boolean isMediaDocument(Uri uri) {
//        return "com.android.providers.media.documents".equals(uri.getAuthority());
//    }
//
//    private static boolean isGooglePhotosUri(Uri uri) {
//        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
//    }
//
//    private static boolean isGoogleDriveUri(Uri uri) {
//        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
//    }
//
//}
