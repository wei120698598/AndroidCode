package com.wei.sample.xposed;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.wei.sample.R;

import static android.widget.Toast.LENGTH_LONG;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019/1/7
 * @email weishuxin@icourt.cc
 */
public class XposedActivity extends Activity {

    private View textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xposed);
        textView = findViewById(R.id.textView);

        if (!isModuleActive()) {
            Toast.makeText(this, "模块未启动", LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "模块已启动", LENGTH_LONG).show();
        }

    }

    private boolean isModuleActive() {
        return false;
    }
}
