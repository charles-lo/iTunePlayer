package com.charles.itunesplayer.views.songlist;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import com.charles.itunesplayer.api.iTuneViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.charles.itunesplayer.R;
import com.charles.itunesplayer.api.model.Track;

import java.util.ArrayList;
import java.util.List;

public class SongListView extends AppCompatActivity {

    Context context;
    LinearLayout main;
    TextView txtNoSongs;
    ShimmerRecyclerView listTracks;

    private List<Track> dataTracks = new ArrayList<>();
    private SongAdapter adapter;

    iTuneViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        context = SongListView.this;

        viewModel = new ViewModelProvider(this).get(iTuneViewModel.class);

        main = findViewById(R.id.song_list_main);
        txtNoSongs = findViewById(R.id.txtNoSongs);
        listTracks = findViewById(R.id.listSongs);

        adapter = new SongAdapter(context, dataTracks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listTracks.setLayoutManager(mLayoutManager);
        listTracks.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        listTracks.setItemAnimator(new DefaultItemAnimator());
        listTracks.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setQueryHint("Search for Songs, Artists & More");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void search(final String strTerm) {
        txtNoSongs.setVisibility(View.GONE);
        listTracks.setVisibility(View.VISIBLE);

        dataTracks.clear();
        adapter.notifyDataSetChanged();

        setLoadingIndicator(true);
        viewModel.fetchTracks(strTerm);

        viewModel.tracks.observe(this, result -> {
            if (result != null) {
                if (result.size() > 0) {
                    displayTracks(result);
                } else {
                    displayMessage("No songs found, Try again.");
                }
            } else {
                displayMessage("network error, check again.");
            }
        });

    }

    public void displayTracks(List<Track> dataTracks) {
        setLoadingIndicator(false);
        this.dataTracks.clear();
        this.dataTracks.addAll(dataTracks);
        adapter.notifyDataSetChanged();
    }

    public void displayMessage(String message) {
        setLoadingIndicator(false);
        Snackbar.make(main, message, Snackbar.LENGTH_LONG).show();
    }

    public void setLoadingIndicator(boolean isLoading) {
        if (isLoading) {
            listTracks.showShimmerAdapter();
        } else {
            listTracks.hideShimmerAdapter();
        }
    }
}