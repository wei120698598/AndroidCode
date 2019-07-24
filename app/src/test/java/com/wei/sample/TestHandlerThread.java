package com.wei.sample;

import android.os.Looper;

import org.junit.Test;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;

public class TestHandlerThread {
    private boolean mDone = false;
    private boolean mSuccess = false;
    private RuntimeException mFailure = null;
    private Looper mLooper;

    public  void go(){};

    public TestHandlerThread() {
    }

    public void doTest(long timeout) {
        (new TestHandlerThread.LooperThread()).start();

        synchronized (this) {
            long now = System.currentTimeMillis();
            long endTime = now + timeout;
            while (!mDone && now < endTime) {
                try {
                    wait(endTime - now);
                } catch (InterruptedException e) {
                }
                now = System.currentTimeMillis();
            }
        }

        mLooper.quit();

        if (!mDone) {
            throw new RuntimeException("test timed out");
        }
        if (!mSuccess) {
            throw mFailure;
        }
    }

    public Looper getLooper() {
        return mLooper;
    }

    public void success() {
        synchronized (this) {
            mSuccess = true;
            quit();
        }
    }

    public void failure(RuntimeException failure) {
        synchronized (this) {
            mSuccess = false;
            mFailure = failure;
            quit();
        }
    }

    class LooperThread extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            mLooper = Looper.myLooper();
            go();
            Looper.loop();

            synchronized (TestHandlerThread.this) {
                mDone = true;
                if (!mSuccess && mFailure == null) {
                    mFailure = new RuntimeException("no failure exception set");
                }
                TestHandlerThread.this.notifyAll();
            }
        }

    }

    private void quit() {
        synchronized (this) {
            mDone = true;
            notifyAll();
        }
    }

    @Test
    public void testRX() {
        Observable.just(true)
                .mergeWith(Observable.just(true))
                .concatMap(new Function<Boolean, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Boolean aBoolean) throws Exception {
                        return null;
                    }
                })
                .subscribe();
    }
}
