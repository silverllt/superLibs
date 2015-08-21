package com.llt.superlibs.app;

import android.app.Activity;

/**
 * Created by Administrator on 2015/8/19.
 */
public class AppManager {

    private static AppManager instance;

    private Activity activity;


    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activity;
    }
}
