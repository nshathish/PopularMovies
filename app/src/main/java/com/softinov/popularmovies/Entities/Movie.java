package com.softinov.popularmovies.Entities;

import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import static com.softinov.popularmovies.BuildConfig.MOVIE_POSTER_BASE_URL;

/**
 * Created by nshat on 09/02/2017.
 */

public class Movie {

    private final String TAG = Movie.class.getSimpleName();

    private final String POSTER_LIST_ITEM_SIZE = "w342";
    private final String POSTER_DETAIL_ITEM_SIZE = "w500";

    private String m_title;
    private String m_overview;
    private float m_voteAverage;
    private String m_posterPath;
    private Date m_releaseDate;

    public String getTitle() {
        return m_title;
    }
    public void setTitle(String title) {
        m_title = title;
    }

    public String getOverview() {
        return m_overview;
    }
    public void setOverview(String overview) {
        m_overview = overview;
    }

    public String getPosterPath() {
        if(m_posterPath.charAt(0) == '/') {
            return m_posterPath.substring(1);
        }
        return m_posterPath;
    }
    public void setPosterPath(String posterPath) {
        m_posterPath = posterPath;
    }


    public float getVoteAverage() {
        return m_voteAverage;
    }
    public void setVoteAverage(float voterAverage) {
        m_voteAverage = voterAverage;
    }

    public Date getReleaseDate() {
        return m_releaseDate;
    }
    public void setReleaseDate(Date releaseDate) {
        m_releaseDate = releaseDate;
    }

    public String getReleaseDateForDisplay() {
        return new SimpleDateFormat("dd MMM yyyy").format(m_releaseDate).toString();
    }

    public Uri getPosterListImageUri() {
        return Uri.parse(MOVIE_POSTER_BASE_URL).buildUpon()
                .appendPath(POSTER_LIST_ITEM_SIZE)
                .appendPath(getPosterPath())
                .build();
    }

    public Uri getPosterDetailsImageUri() {
        return Uri.parse(MOVIE_POSTER_BASE_URL).buildUpon()
                .appendPath(POSTER_DETAIL_ITEM_SIZE)
                .appendPath(getPosterPath())
                .build();
    }

    public Movie(String title, String overview, String posterPath, float voterAvg, Date releaseDate) {
        m_title = title;
        m_overview = overview;
        m_posterPath = posterPath;
        m_voteAverage = voterAvg;
        m_releaseDate = releaseDate;
    }

}
