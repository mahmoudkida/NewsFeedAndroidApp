package com.wshwsh.newsfeedandroidapp;

public class NewsItem {
    private String newsTitle;
    private String newsCategory;
    private String newsUrl;

    public NewsItem(String newsTitle, String newsCategory, String newsUrl) {
        this.newsTitle = newsTitle;
        this.newsCategory = newsCategory;
        this.newsUrl = newsUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
