package com.charles.itunesplayer;

import android.app.Application;

import com.charles.itunesplayer.api.AppRepository;


public class iTuneApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        AppRepository.getInstance(this);
    }
}
