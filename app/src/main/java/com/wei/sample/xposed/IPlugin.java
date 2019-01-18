package com.wei.sample.xposed;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019/1/16
 * @email weishuxin@icourt.cc
 */
public interface IPlugin {
    void doHook(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable;
}
