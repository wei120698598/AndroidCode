package com.wei.sample.rxjava;

import com.google.gson.Gson;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableCreate;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJava {
    public static void main(String[] args) {

        RxJavaPlugins.setOnObservableAssembly(new Function<Observable, Observable>() {
            @Override
            public Observable apply(Observable observable) throws Exception {
                return observable;
            }
        });
        RxJavaPlugins.setOnMaybeSubscribe(new BiFunction<Maybe, MaybeObserver, MaybeObserver>() {
            @Override
            public MaybeObserver apply(Maybe maybe, MaybeObserver maybeObserver) throws Exception {
                return maybeObserver;
            }
        });
        Observable<String> just = Observable.just("1", "2", "3");


        Observable<Integer> map = just.map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return Integer.parseInt(s);
            }
        });
        Observable<String> stringObservable = map.flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                return Observable.just(integer.toString());
            }
        });
        stringObservable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });

        new ObservableCreate<String>(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("");
                emitter.onComplete();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });

        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(new Gson()));
    }
}
