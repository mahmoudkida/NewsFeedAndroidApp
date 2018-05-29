package com.wshwsh.newsfeedandroidapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NewsItemAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsAdapter = new NewsItemAdapter(this, new ArrayList<NewsItem>());
        ListView newsList = findViewById(R.id.newsList);
        newsList.setAdapter(newsAdapter);
        GetNewsTask getNews = new GetNewsTask();
        getNews.execute("http://content.guardianapis.com/search?q=debates&api-key=test");
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = newsAdapter.getItem(i);
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse(currentNews.getNewsUrl()));
                startActivity(httpIntent);
            }
        });
    }


    private class GetNewsTask extends AsyncTask<String, Void, List<NewsItem>> {


        @Override
        protected List<NewsItem> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<NewsItem> result = QueryUtil.fetchNewsData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<NewsItem> data) {
            //do any post task operation
            newsAdapter.clear();
            if (data != null && !data.isEmpty()) {
                newsAdapter.addAll(data);
            }
        }
    }
}
