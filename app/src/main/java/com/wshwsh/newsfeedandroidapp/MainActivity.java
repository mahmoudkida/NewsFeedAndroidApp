package com.wshwsh.newsfeedandroidapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<NewsItem> news = new ArrayList<NewsItem>();
        NewsItemAdapter newsAdapter = new NewsItemAdapter(this, news);
        ListView newsList = findViewById(R.id.newsList);
        newsList.setAdapter(newsAdapter);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = news.get(i);
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse(currentNews.getNewsUrl()));
                startActivity(httpIntent);
            }
        });
    }
}
