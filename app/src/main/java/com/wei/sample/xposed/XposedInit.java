package com.wei.sample.xposed;

import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ServiceLoader;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019/1/7
 * @email weishuxin@icourt.cc
 */
public class XposedInit implements IXposedHookLoadPackage {
    private static IPlugin[] plugins = {
            new TestIPlugin(),
            new TestIPlugin2()
    };

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        Log.d("shuxin.wei", "handleLoadPackage: " + loadPackageParam.packageName);

        try {
            //利用上面实现的代码寻找apk文件
            File apkFile = findApkFile(loadPackageParam.packageName);
            //自定义Classloader
            PathClassLoader pathClassLoader = new PathClassLoader(apkFile.getAbsolutePath(), ClassLoader.getSystemClassLoader());


            Class.forName(IPlugin.class.getName(), true, pathClassLoader);
            Class<?> classOfClassLoader = pathClassLoader.getClass();
            while (classOfClassLoader != PathClassLoader.class) {
                classOfClassLoader = classOfClassLoader.getSuperclass();
            }


            for (IPlugin plugin : plugins) {
                //使用反射的方式去调用具体的Hook逻辑
                Class<?> cls = Class.forName(plugin.getClass().getName(), true, pathClassLoader);
                Object instance = cls.newInstance();
                Method method = cls.getDeclaredMethod("doHook", XC_LoadPackage.LoadPackageParam.class);
                method.invoke(instance, loadPackageParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File findApkFile(String packageName) {
        File file = new File("/data/app/" + packageName + "-2/base.apk");
        if (file.exists()) {
            return file;
        }
        return new File("/data/app/" + packageName + "-1/base.apk");
    }
}