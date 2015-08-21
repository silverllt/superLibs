package com.llt.superlibs.base;


import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

import com.llt.superlibs.Net.NetCenter;
import com.llt.superlibs.app.AppManager;
import com.llt.superlibs.app.NetworkBroadcastReceiver;
import com.llt.superlibs.utils.LogUtils;


public abstract class BaseFragmentActivity extends FragmentActivity implements
		IBaseView {

	private NetworkBroadcastReceiver mNetworkReceiver;

	/** 初始化布局 */
	public abstract void initContentView();

	/** 初始化控件 */
	public abstract void initView();

	/** 初始化控制中心 */
	public abstract void initPresenter();

	/** 初始化监听事件 */
	public void initListener() {
	};

	public void networkMonitor(boolean isNetwork) {
	}


	@Override
	protected void onResume() {
		// 将该Activity加入堆栈
		try {
			AppManager.getAppManager().addActivity(this);
			if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			super.onResume();
			initNetwork();
		} catch (Exception e) {
			LogUtils.error(e);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// 清除网络请求队列
		NetCenter.getInstance().clearRequestQueue();
		if (mNetworkReceiver != null) {
			try {
				unregisterReceiver(mNetworkReceiver);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mNetworkReceiver = null;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initContentView();
		initView();
		initListener();
		initPresenter();

		AppManager.getAppManager().addActivity(this);
	}



	@Override
	protected void onDestroy() {
		// 将该Activity从堆栈移除
		// AppManager.getAppManager().removeActivity();
		if (mNetworkReceiver != null) {
			try {
				unregisterReceiver(mNetworkReceiver);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mNetworkReceiver = null;
		}
		super.onDestroy();
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
//		Intent intent = new Intent(this, LoginActivity.class);
//		intent.putExtra("requestCode", LoginActivity.TOKEN_INVALID_REQUEST_CODE);
//		startActivity(intent);
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

}
