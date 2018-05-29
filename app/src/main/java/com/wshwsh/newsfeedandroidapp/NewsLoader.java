package com.wshwsh.newsfeedandroidapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {
    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<NewsItem> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<NewsItem> earthquakes = QueryUtil.fetchNewsData(mUrl);
        return earthquakes;
    }
}
