package com.wshwsh.newsfeedandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsItemAdapter extends ArrayAdapter<NewsItem> {
    public NewsItemAdapter(Context context, ArrayList<NewsItem> MusicCategories) {
        super(context, 0, MusicCategories);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }
        NewsItem news = getItem(position);
        TextView newsHeader = listItemView.findViewById(R.id.newsHeader);
        TextView newsCategory = listItemView.findViewById(R.id.newsCategory);
        TextView newDate = listItemView.findViewById(R.id.newsDate);
        TextView newAuthor = listItemView.findViewById(R.id.newsAuthor);
        newsHeader.setText(news.getNewsTitle());
        newsCategory.setText(news.getNewsCategory());
        newDate.setText(news.getNewsDate());
        newAuthor.setText(news.getNewsAuthor());

        ImageView newsImage = listItemView.findViewById(R.id.newsImage);
        newsImage.setImageResource(R.drawable.default_image_thumbnail);
        new ImageDownloader(newsImage, news.getNewsImage()).execute();
        return listItemView;
    }
}
