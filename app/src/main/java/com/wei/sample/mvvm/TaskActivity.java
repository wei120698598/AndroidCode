package com.wei.sample.mvvm;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.wei.sample.MyApplication;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-11
 * @email weishuxin@icourt.cc
 */
public class TaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        textView.setText("Hello Word");
        setContentView(textView);
//        TaskViewModel taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
//        TaskViewModel taskViewModel = new ViewModelProvider.AndroidViewModelFactory(MyApplication.getInstance()).create(TaskViewModel.class);
        TaskViewModel taskViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(MyApplication.getInstance())).get(TaskViewModel.class);
        taskViewModel.getString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });


        taskViewModel.getData();
    }
}
