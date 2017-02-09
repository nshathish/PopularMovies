package com.softinov.popularmovies.Helpers;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.softinov.popularmovies.BuildConfig.API_KEY;
import static com.softinov.popularmovies.BuildConfig.MOVIE_DB_BASE_URL;

/**
 * Created by nshat on 09/02/2017.
 */

public final class NetworkHelper {
    private static final String TAG = NetworkHelper.class.getSimpleName();

    private static final String API_KEY_PARAM = "api_key";

    public static URL buildUrl(String query) {
        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendPath(query)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {

            url = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Uri: " + url.toString());
        return url;
    }

    public static String getResponseFromHttpUrl(URL movieRequestUrl) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection)movieRequestUrl.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
