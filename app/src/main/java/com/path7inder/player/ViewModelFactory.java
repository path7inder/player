package com.path7inder.player;

import com.path7inder.player.data.repository.VideoListRepository;
import com.path7inder.player.viewmodel.VideoListViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final VideoListRepository videoListRepository;

    public ViewModelFactory(VideoListRepository videoListRepository) {
        this.videoListRepository = videoListRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(VideoListViewModel.class)) {
            return (T) new VideoListViewModel(videoListRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
