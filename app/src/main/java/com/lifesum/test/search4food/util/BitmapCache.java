package com.lifesum.test.search4food.util;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by David on 7/5/14.
 */
public class BitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    //================================================================================
    // Constructors
    //================================================================================

    public BitmapCache() {
        this(getDefaultLruCacheSize());
    }

    public BitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    //================================================================================
    // LruCache Methods
    //================================================================================

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    //================================================================================
    // ImageCache Methods
    //================================================================================

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    //================================================================================
    // Class Methods
    //================================================================================

    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }
}
