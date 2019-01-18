package com.wei.sample.rxjava;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wei.sample.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2018/12/25
 * @email weishuxin@icourt.cc
 */
public class RxJavaActivity extends AppCompatActivity {

    private String sd;
    private String sdf;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
            void onLifecycleChanged(@NotNull LifecycleOwner owner, @NotNull Lifecycle.Event event) {
                System.out.println("onDestroy1:" + event.name());
            }
        });
    }




    @Override
    protected void onDestroy() {
        System.out.println("onDestroy2");
        super.onDestroy();
        System.out.println("onDestroy");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RxJavaActivity that = (RxJavaActivity) o;
        return java.util.Objects.equals(sd, that.sd) &&
                java.util.Objects.equals(sdf, that.sdf);
    }

    @Override
    public int hashCode() {

        return java.util.Objects.hash(sd, sdf);
    }
}
