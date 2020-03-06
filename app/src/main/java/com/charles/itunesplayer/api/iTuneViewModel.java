package com.charles.itunesplayer.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.charles.itunesplayer.api.model.Track;

import java.util.List;

public class iTuneViewModel extends ViewModel {

    public LiveData<List<Track>> tracks = AppRepository.getInstance().getTracks();

    public void fetchTracks(String term) {
        AppRepository.getInstance().fetchTracks(term);
    }
}
