package com.charles.itunesplayer.api;

import com.charles.itunesplayer.api.model.TrackModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIService {

    @GET("search")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Single<TrackModel> fetchTracks(@Query("term") String term);
}