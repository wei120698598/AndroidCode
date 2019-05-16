package com.wei.sample.thread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-14
 * @email weishuxin@icourt.cc
 */
public class ThreadActivity extends Activity {
    private final Object lock = new Object();

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
           synchronized (lock){
               Log.i("shuxin.wei", "run: " + Thread.currentThread().getName());
               try {
                   lock.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               Log.i("shuxin.wei", "run恢复: " + Thread.currentThread().getName());
           }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        setContentView(contentView);


        TextView textView = new Button(this);
        textView.setText("加2个线程");
        contentView.addView(textView, -1, -2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(runnable).start();
                new Thread(runnable).start();
            }
        });


        TextView textView2 = new Button(this);
        textView2.setText("恢复");
        contentView.addView(textView2, -1, -2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (lock){
                    lock.notifyAll();
                }
            }
        });


        for (int i = 0; i < 460; i++) {
            new Thread(runnable).start();
        }
    }
}
