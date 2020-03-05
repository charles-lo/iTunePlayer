package com.charles.itunesplayer.views.songlist;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
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

public class SongListView extends AppCompatActivity implements SongListContract.View {

    Context context;
    LinearLayout main;
    TextView txtNoSongs;
    ShimmerRecyclerView listTracks;

    private List<Track> dataTracks = new ArrayList<>();
    private SongAdapter adapter;

    SongListPresenter presenter;

    public SongListView() {
        presenter = new SongListPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        context = SongListView.this;

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
        listTracks.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getTracks(strTerm);
            }
        }, 2000);
    }

    @Override
    public void displayTracks(List<Track> dataTracks) {
        setLoadingIndicator(false);
        this.dataTracks.clear();
        this.dataTracks.addAll(dataTracks);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayMessage(String message) {
        setLoadingIndicator(false);
        Snackbar.make(main, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {
        if (isLoading) {
            listTracks.showShimmerAdapter();
        } else {
            listTracks.hideShimmerAdapter();
        }
    }
}