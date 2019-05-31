package com.wei.sample.glide;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

import java.util.HashSet;

public class GlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        builder.setMemoryCache(new LruResourceCache(100 * 1024 * 1024));
        builder.setDiskCache(new DiskLruCacheFactory(context.getExternalCacheDir().getAbsolutePath(), 100 * 1_024 * 1_024));
        builder.setBitmapPool(new LruBitmapPool(100*1024*1024,new HashSet<Bitmap.Config>()));
    }
}
