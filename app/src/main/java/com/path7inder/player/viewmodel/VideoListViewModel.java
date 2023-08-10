package com.path7inder.player.viewmodel;

import com.path7inder.player.data.repository.VideoListRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VideoListViewModel extends ViewModel {

    private final VideoListRepository videoListRepository;
    private final MutableLiveData<List<String>> videoListLiveData;

    public VideoListViewModel(VideoListRepository videoListRepository) {
        this.videoListRepository = videoListRepository;
        videoListLiveData = new MutableLiveData<>();
    }

    public LiveData<List<String>> getVideoList() {
        if (videoListLiveData.getValue() == null) {
            loadData();
        }
        return videoListLiveData;
    }

    private void loadData() {
        List<String> videoList  = videoListRepository.getVideoList();
        videoListLiveData.setValue(videoList);
    }
}
