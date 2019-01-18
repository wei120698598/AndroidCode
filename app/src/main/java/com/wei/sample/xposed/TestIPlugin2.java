package com.wei.sample.xposed;

import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019/1/16
 * @email weishuxin@icourt.cc
 */
public class TestIPlugin2 implements IPlugin {
    String packageName = "com.wei.sample";

    @Override
    public void doHook(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        // Xposed模块自检测
        if (loadPackageParam.packageName.equals(packageName)) {
            XposedHelpers.findAndHookMethod(XposedActivity.class.getName(), loadPackageParam.classLoader, "isModuleActive", XC_MethodReplacement.returnConstant(true));
        }
        if (loadPackageParam.packageName.equals(packageName)) {
            XposedHelpers.findAndHookMethod(XposedActivity.class.getName(), loadPackageParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    //不能通过Class.forName()来获取Class ，在跨应用时会失效
                    Class c = loadPackageParam.classLoader.loadClass("com.wei.sample.xposed.XposedActivity");
                    Field field = c.getDeclaredField("textView");
                    field.setAccessible(true);
                    //param.thisObject 为执行该方法的对象，在这里指MainActivity
                    TextView textView = (TextView) field.get(param.thisObject);
                    textView.setText("Hello Xposed4");
                }
            });
        }
    }
}
