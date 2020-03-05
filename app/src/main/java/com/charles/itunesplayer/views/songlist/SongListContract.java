package com.charles.itunesplayer.views.songlist;

import com.charles.itunesplayer.api.model.Track;

import java.util.List;

/**
 *
 */

class SongListContract {

    interface View {
        void displayMessage(String message);

        void setLoadingIndicator(boolean isLoading);

        void displayTracks(List<Track> dataTracks);
    }

    interface Presenter {
        void getTracks(String term);
    }
}