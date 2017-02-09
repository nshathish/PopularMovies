package com.softinov.popularmovies.Helpers;

import android.content.Context;

import com.softinov.popularmovies.Entities.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nshat on 09/02/2017.
 */

public final class MovieJsonHelper {

    public static List<Movie> getSimpleMoviesFromJson(Context context, String movieResponseString) throws JSONException, ParseException {

        final String MDB_RESULTS = "results";
        final String MDB_TITLE = "title";
        final String MDB_OVERVIEW = "overview";
        final String MDB_RELEASE_DATE = "release_date";
        final String MDB_VOTE_AVERAGE = "vote_average";
        final String MDB_POSTER_PATH = "poster_path";

        List<Movie> movies = new ArrayList<Movie>();

        JSONObject movieResponseJson = new JSONObject(movieResponseString);
        JSONArray moviesArray = movieResponseJson.getJSONArray(MDB_RESULTS);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for(int i=0; i < moviesArray.length(); i++) {
            JSONObject movieObject = moviesArray.getJSONObject(i);
            String tite = movieObject.getString(MDB_TITLE);
            String overview = movieObject.getString(MDB_OVERVIEW);
            String posterPath = movieObject.getString(MDB_POSTER_PATH);
            float voterAvg = (float)movieObject.getDouble(MDB_VOTE_AVERAGE);
            Date releaseDate = dateFormat.parse(movieObject.getString(MDB_RELEASE_DATE));
            movies.add(new Movie(tite, overview, posterPath, voterAvg, releaseDate));
        }

        return movies;
    }
}
