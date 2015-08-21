package com.llt.superlibs.base;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import com.llt.superlibs.Net.NetCenter;
import com.llt.superlibs.app.AppManager;
import com.llt.superlibs.app.NetworkBroadcastReceiver;
import com.llt.superlibs.ui.widget.dialog.CustomProgressDialog;
import com.llt.superlibs.utils.LogUtils;

public abstract class BaseActivity extends Activity implements IBaseView{

    private CustomProgressDialog progressDialog = null;

    /** 初始化布局 */
    public abstract void initContentView();

    /** 初始化控件 */
    public abstract void initView();

    /** 初始化控制中心 */
    public abstract void initPresenter();

    /** 初始化监听事件 */
    public void initListener() {};

    private NetworkBroadcastReceiver mNetworkReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        initContentView();
        initView();
        initListener();
        initPresenter();
    }


    @Override
    protected void onPause() {
        super.onPause();
        NetCenter.getInstance().clearRequestQueue();
        if (mNetworkReceiver != null) {
            try {
                unregisterReceiver(mNetworkReceiver);
            } catch (Exception e) {
            }
            mNetworkReceiver = null;
        }
    }

    @Override
    protected void onResume() {
        AppManager.getAppManager().addActivity(this);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        initNetwork();
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        AppManager.getAppManager().addActivity(this);
    }


    @Override
    protected void onDestroy() {
        if (mNetworkReceiver != null) {
            try {
                unregisterReceiver(mNetworkReceiver);
            } catch (Exception e) {
                LogUtils.error(e);
            }
            mNetworkReceiver = null;
        }
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
        super.onDestroy();

    }

    private void initNetwork() {
        if (mNetworkReceiver != null) {
            return;
        }
        mNetworkReceiver = new NetworkBroadcastReceiver();
        mNetworkReceiver
                .setOnNetworkChangeListener(new NetworkBroadcastReceiver.OnNetworkChangeListener() {

                    @Override
                    public void networkChange(boolean isNetwork) {
                        networkMonitor(isNetwork);
                    }
                });
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        try {
            registerReceiver(mNetworkReceiver, filter);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /** 网络变化监控 **/
    public void networkMonitor(boolean isNetwork) {
    }

    @Override
    public void showProgress(boolean cancelable, String message) {
        try {
            if (progressDialog == null && !isFinishing()) {
                progressDialog = CustomProgressDialog.createDialog(this);
                progressDialog.setCanceledOnTouchOutside(cancelable);
                progressDialog.setMessage("");
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        } catch (Exception e) {
            LogUtils.error(e);
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog == null || !progressDialog.isShowing())
            return;
        progressDialog.dismiss();
    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showNetError() {
        showToast("网络异常,请稍后重试");
    }

    @Override
    public void showParseError() {
        showToast("数据解析异常");
    }

    @Override
    public void tokenInvalid() {
        showToast("用户授权过期，请重新登陆");
        hideProgress();
    }
}
