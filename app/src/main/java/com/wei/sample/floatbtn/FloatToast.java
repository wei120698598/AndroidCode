package com.wei.sample.floatbtn;

import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019/1/15
 * @email weishuxin@icourt.cc
 */
public class FloatToast {

    private Toast toast;

    private Object mTN;

    public FloatToast(Context applicationContext) {
        toast = Toast.makeText(applicationContext, "哈哈", Toast.LENGTH_LONG);
        try {
            Field tnField = toast.getClass().getDeclaredField("mTN");
            tnField.setAccessible(true);
            mTN = tnField.get(toast);
            Field short_duration_timeout = mTN.getClass().getDeclaredField("SHORT_DURATION_TIMEOUT");
            short_duration_timeout.setAccessible(true);
            short_duration_timeout.set(mTN, -1L);
            Field long_duration_timeout = mTN.getClass().getDeclaredField("LONG_DURATION_TIMEOUT");
            long_duration_timeout.setAccessible(true);
            long_duration_timeout.set(mTN, -1L);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}