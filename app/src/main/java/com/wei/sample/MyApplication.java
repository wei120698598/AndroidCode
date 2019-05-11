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
    private static MyApplication myApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
//        SoLoader.init(this, false);
    }

    public static MyApplication getInstance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }
}
