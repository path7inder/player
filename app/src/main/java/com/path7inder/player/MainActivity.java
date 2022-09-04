package com.path7inder.player;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private final int REQUEST_CODE_PERMISSION = 0;

    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ArrayList<String> videos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(
                getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            videos = loadVideos();
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
    protected void onStart() {
        super.onStart();
        for (String video : videos) {
            Log.d(TAG, video);
        }
        recyclerView = findViewById(R.id.recyclerView);
        customAdapter = new CustomAdapter(videos);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        if (recyclerView.getLayoutManager() != null) {
            recyclerView.scrollToPosition(
                    ((LinearLayoutManager) recyclerView.getLayoutManager())
                            .findFirstCompletelyVisibleItemPosition());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "PERMISSION_GRANTED!");
                videos = loadVideos();
            }
        }
    }

    private ArrayList<String> loadVideos() {
        ArrayList<String> videos = new ArrayList<>();
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        String[] projection = new String[] { MediaStore.Video.Media.DISPLAY_NAME };
        Cursor cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);
        int nameColumn = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME);
        while (cursor.moveToNext()) {
            videos.add(cursor.getString(nameColumn));
        }
        return videos;
    }
}