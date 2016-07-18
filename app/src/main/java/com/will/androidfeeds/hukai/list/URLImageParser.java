package com.will.androidfeeds.hukai.list;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by Will on 2016/7/16.
 */
public class URLImageParser implements Html.ImageGetter {
    Context c;
    TextView container;
    float scale;
    private static final String HUKAI_HOST = "http://hukai.me";

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     */
    public URLImageParser(TextView t) {
        this.container = t;
        this.c = t.getContext();
    }

    public Drawable getDrawable(String source) {
        source = HUKAI_HOST + source;
        URLDrawable mDrawable= new URLDrawable();
        // get the actual source
        ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask( mDrawable);
        asyncTask.execute(source);
        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return mDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;
        String source;
        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // set the correct bound according to the result from HTTP call
            urlDrawable.setBounds(0, 0,  (int)(result.getIntrinsicWidth()*scale), (int)(result.getIntrinsicHeight()*scale));

            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            URLImageParser.this.container.invalidate();
            URLImageParser.this.container.setText(URLImageParser.this.container.getText());
            //URLImageParser.this.container.setHeight(URLImageParser.this.container.getHeight()+result.getIntrinsicHeight());
        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                BitmapDrawable drawable = new BitmapDrawable(c.getResources(),Picasso.with(c).load(urlString).get());
                scale = getScale(drawable);
                drawable.setBounds(0, 0,  (int)(drawable.getIntrinsicWidth() * scale), (int)(drawable.getIntrinsicHeight() * scale));
                return drawable;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    }
    private float getScale(Drawable drawable){
        float maxWidth = container.getWidth();
        float originalDrawableWidth = drawable.getIntrinsicWidth();
        return maxWidth/originalDrawableWidth;
    }
    public class URLDrawable extends BitmapDrawable {
        // the drawable that you need to set, you could set the initial drawing
        // with the loading image if you need to
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if(drawable != null) {
                drawable.draw(canvas);
            }
        }
    }
}
