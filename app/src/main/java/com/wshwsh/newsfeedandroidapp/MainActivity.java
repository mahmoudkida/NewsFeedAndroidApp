package com.wshwsh.newsfeedandroidapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsItem>> {
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private static final String REQUEST_URL = "https://content.guardianapis.com/search?show-tags=contributor&show-fields=thumbnail&api-key=a70a53c4-d46a-484c-957b-95ac3120c0a5";
    private NewsItemAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView newsList = findViewById(R.id.newsList);

        if (CheckNetwork.isInternetAvailable(this.getApplicationContext())) //returns true if internet available
        {
            newsAdapter = new NewsItemAdapter(this, new ArrayList<NewsItem>());
            newsList.setAdapter(newsAdapter);
            newsList.setEmptyView(findViewById(R.id.emptyListView));
            //getNews.execute("http://content.guardianapis.com/search?q=debates&api-key=test");
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
            newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    NewsItem currentNews = newsAdapter.getItem(i);
                    Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                    httpIntent.setData(Uri.parse(currentNews.getNewsUrl()));
                    startActivity(httpIntent);
                }
            });
        } else {
            newsList.setVisibility(View.GONE);
            TextView noInternetView = findViewById(R.id.noInternetView);
            noInternetView.setVisibility(View.VISIBLE);
        }

    }


    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<List<NewsItem>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Uri baseUri = Uri.parse(REQUEST_URL);
        String newsEntries = sharedPrefs.getString(
                getString(R.string.settings_news_entries_key),
                getString(R.string.settings_news_entries_default));

        String newsCategories = sharedPrefs.getString(
                getString(R.string.settings_news_categories_key),
                getString(R.string.settings_news_entries_default));

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("page-size", newsEntries);
        uriBuilder.appendQueryParameter("section", newsCategories);
        // Create a new loader for the given URL
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        // Clear the adapter of previous earthquake data
        newsAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        // Loader reset, so we can clear out our existing data.
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        newsAdapter.clear();

    }
}
