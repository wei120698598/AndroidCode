package com.wei.sample.rxjava;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wei.sample.MyApplication;
import com.wei.sample.R;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2018/12/25
 * @email weishuxin@icourt.cc
 */
public class RxJavaActivity extends RxAppCompatActivity {

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
        Disposable disposable = Observable.just("String")
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.just(s);
                    }
                })
                .compose(this.bindToLifecycle())
                .delaySubscription(10000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(MyApplication.getInstance(), s, Toast.LENGTH_SHORT).show();
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
