package com.wei.sample.glide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-24
 * @email weishuxin@icourt.cc
 */
public class GlideActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this).load("").into(new ImageView(this));
    }
}
