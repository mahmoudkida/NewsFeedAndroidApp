package com.wshwsh.newsfeedandroidapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

class ImageDownloader extends AsyncTask<Object, Void, Bitmap> {

    private ImageView imv;
    private String path;

    public ImageDownloader(ImageView imv,String url) {
        this.imv = imv;
        this.path = url;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bitmap = null;
            //image view
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {

        if(result != null && imv != null){
            imv.setVisibility(View.VISIBLE);
            imv.setImageBitmap(result);
        }else{
            imv.setVisibility(View.GONE);
        }
    }

}