package com.path7inder.player.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.path7inder.player.R;
import com.path7inder.player.ViewModelFactory;
import com.path7inder.player.adapter.VideoListAdapter;
import com.path7inder.player.data.repository.VideoListRepository;
import com.path7inder.player.data.repository.VideoListRepositoryImpl;
import com.path7inder.player.viewmodel.VideoListViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideoListActivity extends AppCompatActivity {

    private final int REQUEST_CODE_PERMISSION = 0;

    private RecyclerView recyclerView;
    private VideoListAdapter videoListAdapter;
    private VideoListViewModel videoListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(
                getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            initVideoListView();
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
                initVideoListView();
            } else {
                finish();
            }
        }
    }

    private void initVideoListView() {
        loadVideos();
        videoListViewModel.getVideoList().observe(this, videos -> {
            // update adapter
        });
        VideoListAdapter adapter = new VideoListAdapter(
                videoListViewModel.getVideoList().getValue());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        if (recyclerView.getLayoutManager() != null) {
            recyclerView.scrollToPosition(
                    ((LinearLayoutManager) recyclerView.getLayoutManager())
                            .findFirstCompletelyVisibleItemPosition());
        }
    }

    private void loadVideos() {
        VideoListRepository videoListRepository = new VideoListRepositoryImpl(getContentResolver());
        videoListViewModel = new ViewModelProvider(
                this,
                new ViewModelFactory(videoListRepository)).get(VideoListViewModel.class);
    }
}