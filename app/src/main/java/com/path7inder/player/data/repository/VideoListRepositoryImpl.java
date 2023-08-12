package com.path7inder.player.data.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.path7inder.player.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoListRepositoryImpl implements VideoListRepository {

    private final ContentResolver contentResolver;

    public VideoListRepositoryImpl(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public List<Video> getVideoList() {
        List<Video> videos = new ArrayList<>();

        String[] projection = new String[] {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION };
        Cursor cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);

        int idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID);
        int displayNameColumn = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME);
        int durationColumn = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColumn);
            String displayName = cursor.getString(displayNameColumn);
            long duration = cursor.getLong(durationColumn);
            videos.add(new Video(id, displayName, duration));
        }

        cursor.close();
        return videos;
    }
}
