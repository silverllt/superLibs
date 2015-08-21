package com.llt.superlibs.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkBroadcastReceiver extends BroadcastReceiver {
    public OnNetworkChangeListener mListener;

    public interface OnNetworkChangeListener {
        void networkChange(boolean isNetwork);
    }

    public void setOnNetworkChangeListener(OnNetworkChangeListener listener) {
        mListener = listener;
    }

    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            info = connectivityManager.getActiveNetworkInfo();
            boolean isNetwork;
            if (info != null && info.isAvailable()) {
                // 连接状态
                isNetwork = true;
            } else {
                isNetwork = false;
            }
            if (mListener != null) {
                mListener.networkChange(isNetwork);
            }
        }

    }
}
