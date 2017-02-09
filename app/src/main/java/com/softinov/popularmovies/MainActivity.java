package com.softinov.popularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softinov.popularmovies.Adapters.MovieAdapter;
import com.softinov.popularmovies.Entities.Movie;
import com.softinov.popularmovies.Helpers.MovieJsonHelper;
import com.softinov.popularmovies.Helpers.NetworkHelper;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView m_RV_movieListView;
    private ProgressDialog m_PD_loadingIndicator;
    private MovieAdapter m_movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_RV_movieListView = (RecyclerView)findViewById(R.id.rv_movies);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, 1, false);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        m_RV_movieListView.setLayoutManager(gridLayoutManager);
        m_RV_movieListView.setHasFixedSize(true);

        m_movieAdapter = new MovieAdapter(this, this);
        m_RV_movieListView.setAdapter(m_movieAdapter);

        loadMovies("popular");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.sort_by_popular) {
            loadMovies("popular");
        } else {
            loadMovies("top_rated");
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMovies(String sortBy) {
        new MovieFetcherTask().execute(sortBy);
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = MovieDetails.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        String movieAsJson = new Gson().toJson(movie);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movieAsJson);
        startActivity(intentToStartDetailActivity);
        Toast.makeText(this, "Viewing " + movie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private class MovieFetcherTask extends AsyncTask<String, Void, List<Movie>> {

        private final String TAG = MovieFetcherTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            m_PD_loadingIndicator = ProgressDialog.show(MainActivity.this, "Wait...", "loading movies...", true);
        }

        @Override
        protected List<Movie> doInBackground(String... params) {

            String queryPath = null;
            if(params.length == 0) {
                queryPath = "popular";
            }
            queryPath = params[0];

            URL movieRequestUrl = NetworkHelper.buildUrl(queryPath);
            try {

                String jsonMovieResponse = NetworkHelper.getResponseFromHttpUrl(movieRequestUrl);
                Log.v(TAG, "Response Back: " + jsonMovieResponse);
                List<Movie> simpleJsonMovieData = MovieJsonHelper.getSimpleMoviesFromJson(MainActivity.this, jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            m_PD_loadingIndicator.dismiss();
            if(!movies.isEmpty()) {
                m_movieAdapter.setMovieData(movies);
            }
            super.onPostExecute(movies);
        }
    }
}
