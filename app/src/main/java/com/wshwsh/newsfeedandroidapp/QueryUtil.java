package com.wshwsh.newsfeedandroidapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryUtil {
    static final String LOG_TAG = QueryUtil.class.getSimpleName();

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<NewsItem> extractNewsFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<NewsItem> newsList = new ArrayList<>();
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject topLevel = new JSONObject(newsJSON);
            JSONObject main = topLevel.getJSONObject("response");
            JSONArray newsArray = main.getJSONArray("results");
            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject jsonItem = newsArray.getJSONObject(i);
                NewsItem newsItem = new NewsItem();
                newsItem.setNewsTitle(jsonItem.getString("webTitle"));
                newsItem.setNewsCategory(jsonItem.getString("sectionName"));
                newsItem.setNewsUrl(jsonItem.getString("webUrl"));
                Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(jsonItem.getString("webPublicationDate"));
                String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
                newsItem.setNewsDate(formattedDate);

                //get news author
                JSONArray tagsList = jsonItem.getJSONArray("tags");
                JSONObject tagItem = tagsList.getJSONObject(0);
                newsItem.setNewsAuthor(tagItem.getString("webTitle"));
                newsList.add(newsItem);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Return the list of news
        return newsList;
    }

    public static List<NewsItem> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<NewsItem> newsItems = extractNewsFromJson(jsonResponse);
        // Return the list onewsItemsf {@link Earthquake}s
        return newsItems;
    }
}
