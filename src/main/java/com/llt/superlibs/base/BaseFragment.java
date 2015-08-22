package com.llt.superlibs.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.llt.superlibs.Net.NetCenter;
import com.llt.superlibs.app.NetworkBroadcastReceiver;
import com.llt.superlibs.utils.ToastUtil;


public abstract class BaseFragment extends Fragment implements IBaseView {

	private NetworkBroadcastReceiver mNetworkReceiver;

	/** 上下文 **/
	protected Context mContext;

	/** 初始化控件 */
	public abstract void initView(View view, Bundle savedInstanceState);

	/** 初始化控制中心 */
	public abstract void initPresenter();

	/** 初始化监听事件 */
	public void initListener() {
	};


	public void networkMonitor(boolean isNetwork) {

	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	@Override
	public void onResume() {
		super.onResume();
		// initNetwork();

	}

	@Override
	public void onPause() {
		super.onPause();
		// 清除网络请求队列
		NetCenter.getInstance().clearRequestQueue();
		if (mNetworkReceiver != null) {
			try {
				getActivity().unregisterReceiver(mNetworkReceiver);
			} catch (Exception e) {
			}
			mNetworkReceiver = null;
		}
	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view, savedInstanceState);

		initListener();
		initPresenter();
	}


	@Override
	public void onDestroy() {
		if (mNetworkReceiver != null) {
			try {
				getActivity().unregisterReceiver(mNetworkReceiver);
			} catch (Exception e) {
			}
			mNetworkReceiver = null;
		}
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		System.gc();
		super.onDestroyView();
	}


	@Override
	public void showProgress(boolean flag, String message) {
		try {
			((BaseFragmentActivity) getActivity()).showProgress(flag, message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void hideProgress() {
		try {
			((IBaseView) getActivity()).hideProgress();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showToast(int resId) {
		showToast(getString(resId));
	}

	@Override
	public void showToast(String msg) {
		if (!isDetached()) {
			Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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
