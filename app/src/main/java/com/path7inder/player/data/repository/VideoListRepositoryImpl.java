package com.path7inder.player.data.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class VideoListRepositoryImpl implements VideoListRepository {

    private final ContentResolver contentResolver;

    public VideoListRepositoryImpl(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public List<String> getVideoList() {
        List<String> videos = new ArrayList<>();
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
        cursor.close();
        return videos;
    }
}
