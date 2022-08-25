package com.path7inder.player;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private final int REQUEST_CODE_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(
                getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            loadVideos();
        } else if (shouldShowRequestPermissionRationale(
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Log.d(TAG, "User has denied READ_EXTERNAL_STORAGE explicitly!");
        } else {
            requestPermissions(
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    REQUEST_CODE_PERMISSION
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "PERMISSION_GRANTED!");
                loadVideos();
            }
        }
    }

    private void loadVideos() {
        Log.d(TAG, "loadVideos");
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null);
        Log.d(TAG, "row : " + cursor.getCount());
        Log.d(TAG, "col : " + cursor.getColumnCount());
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String name = cursor.getColumnName(i);
            int type = cursor.getType(i);
            String value = "null";
            if (type == Cursor.FIELD_TYPE_STRING) {
                value = "(String) " + cursor.getString(i);
            } else if (type == Cursor.FIELD_TYPE_INTEGER) {
                value = "(Integer) " + String.valueOf(cursor.getInt(i));
            } else if (type == Cursor.FIELD_TYPE_FLOAT) {
                value = "(Float) " + String.valueOf(cursor.getFloat(i));
            } else if (type == Cursor.FIELD_TYPE_BLOB) {
                value = "(Blob) data";
            }
            Log.d(TAG, i + ": " + name + ": " + value);
        }
    }
}