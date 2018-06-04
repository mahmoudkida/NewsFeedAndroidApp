package com.wshwsh.newsfeedandroidapp;

public class NewsItem {
    private String newsTitle;
    private String newsCategory;
    private String newsUrl;
    private String newsDate;
    private String newsAuthor;
    private String newsImage;


    //constructor without paramteres
    public NewsItem() {
    }

    public NewsItem(String newsTitle, String newsCategory, String newsUrl, String newsDate, String newsAuthor, String newsImage) {
        this.newsTitle = newsTitle;
        this.newsCategory = newsCategory;
        this.newsUrl = newsUrl;
        this.newsDate = newsDate;
        this.newsAuthor = newsAuthor;
        this.newsImage = newsImage;
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

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }
}
