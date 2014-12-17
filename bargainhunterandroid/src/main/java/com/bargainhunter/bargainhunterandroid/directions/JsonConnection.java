package com.bargainhunter.bargainhunterandroid.directions;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vasovourka on 12/16/14.
 */
public class JsonConnection {
    protected URL url;

    protected JsonConnection(final String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            Log.e("Routing Error", e.getMessage());
        }
    }

    protected InputStream getInputStream() {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.e("Routing Error", e.getMessage());
            return null;
        }
    }
}
