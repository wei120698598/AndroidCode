package com.wei.sample;

import androidx.multidex.MultiDexApplication;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-04-18
 * @email weishuxin@icourt.cc
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
