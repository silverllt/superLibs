package com.llt.superlibs.utils;


import android.util.Log;

import com.bbg.monitor.MonitorManager;

public class LogUtils {

    /**
     * LOG标签
     */
    private static String TAG = "bbg";

    /**
     * 是否调试模式
     */
    private static boolean isDebug = true;

    /**
     * 设置调试模式
     *
     * @param isDebug
     * @return
     */
    public static void setDebug(boolean isDebug) {
        LogUtils.isDebug = isDebug;
    }

    /**
     * 设置标签
     *
     * @param tag
     * @return
     */
    public static void setTAG(String tag) {
        LogUtils.TAG = tag;
    }

    /**
     * 调试内容
     *
     * @param tag
     * @param msg
     */
    public static void debug(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    /**
     * 调试内容
     *
     * @param msg
     * @param msg
     */
    public static void debug(String msg) {
        if (isDebug) {
            Log.d(LogUtils.TAG, msg);
        }
    }

    /**
     * 异常发生
     *

     */
    public static void error(Exception e) {
        if (isDebug) {
            Log.e(TAG, e.toString());
        }
        MonitorManager.getExceptionManager().onException(e, null);
    }
}
