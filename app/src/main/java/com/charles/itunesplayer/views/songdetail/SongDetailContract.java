package com.charles.itunesplayer.views.songdetail;

import com.charles.itunesplayer.api.model.Track;

/**
 *
 */

class SongDetailContract {

    interface View {
        void displayMessage(String message);

        void displayTrack(Track track);
    }
}
