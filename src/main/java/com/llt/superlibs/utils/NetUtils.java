package com.llt.superlibs.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;

import com.llt.superlibs.app.AppManager;
import com.llt.superlibs.base.BaseActivity;
import com.llt.superlibs.base.BaseFragmentActivity;

public class NetUtils {

    private Context context;

    public NetUtils(Context context) {
        this.context = context;
    }

    /**
     * 判断当前网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        try {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if (mConnectivityManager == null) {
                    showNetErrorDialog();
                    return false;
                }
                NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
                if (info != null && info.isConnected()
                        && info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        showNetErrorDialog();

        return false;
    }

    Dialog dialog = null;

    @SuppressLint("NewApi")
    private void showNetErrorDialog() {
        try {
            if (AppManager.getAppManager().currentActivity() instanceof FragmentActivity) {

                BaseFragmentActivity activity = (BaseFragmentActivity) AppManager
                        .getAppManager().currentActivity();
                activity.hideProgress();
            } else if (AppManager.getAppManager().currentActivity() instanceof Activity) {
                BaseActivity activity = (BaseActivity) AppManager
                        .getAppManager().currentActivity();
                activity.hideProgress();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dialog != null && dialog.isShowing()) {
            return;
        }

//        dialog = BBGGlobalDialog.getInstance().showDialog(context,
//                "当前网络不可用,请检查网络设置。", "去设置", new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        toSetting();
//                    }
//                });

         dialog = new AlertDialog.Builder(context)
         .setNeutralButton("取消", new DialogInterface.OnClickListener() {

         @Override
         public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            }
         })
         .setPositiveButton("去设置",
         new DialogInterface.OnClickListener() {

         @Override
         public void onClick(DialogInterface dialog,
         int which) {
         toSetting();
         }
         }).setIcon(android.R.drawable.btn_star).setTitle("提示")
         .setMessage("当前网络不可用,请检查网络设置。").create();

         dialog.show();

    }

    void toSetting() {
        Intent intent = null;
        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(
                    android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings",
                    "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.show(context, R.string.toastSetingNetwork);
        }
    }

    /**
     * 判断当前wifi是否可用
     *
     * @return
     */
    public boolean isWifiConnected() {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @return
     */
    public boolean isMobileConnected() {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息 1 wifi 2 移动网络 -1 无网络
     *
     * @return
     */
    public int getConnectedType() {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType() == mConnectivityManager.TYPE_WIFI ? 1
                        : 2;
            }
        }
        return -1;
    }
}
