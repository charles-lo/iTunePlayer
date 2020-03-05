package com.charles.itunesplayer.views.songlist;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.charles.itunesplayer.R;
import com.charles.itunesplayer.api.model.Track;
import com.charles.itunesplayer.views.songdetail.SongDetailView;

import java.util.List;

/**
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {

    private Context context;
    private List<Track> trackList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout row;
        ImageView imgTrackArtwork;
        TextView txtTrackName, txtArtistNameNGenre, txtPrice;

        MyViewHolder(View view) {
            super(view);
            row = view.findViewById(R.id.song_item_row);
            imgTrackArtwork = view.findViewById(R.id.artwork);
            txtTrackName = view.findViewById(R.id.track_name);
            txtArtistNameNGenre = view.findViewById(R.id.artist_name_and_genre);
            txtPrice = view.findViewById(R.id.price);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detail = new Intent(context, SongDetailView.class);
                    detail.putExtra("track", trackList.get(getAdapterPosition()));
                    context.startActivity(detail);
                }
            });
        }
    }

    SongAdapter(Context context, List<Track> trackList) {
        this.context = context;
        this.trackList = trackList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Track track = trackList.get(position);

        final String artworkUrl = track.getArtworkUrl100();
        Glide.with(context).load(artworkUrl).placeholder(R.drawable.ic_logo).into(holder.imgTrackArtwork);

        holder.txtTrackName.setText(track.getTrackName());
        holder.txtArtistNameNGenre.setText(String.format("%s | %s", track.getArtistName(), track.getPrimaryGenreName()));
        holder.txtPrice.setText(String.format("US $ %s", String.valueOf(track.getTrackPrice())));
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }
}