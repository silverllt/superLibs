package com.llt.superlibs.manager.presenter;


import com.llt.superlibs.manager.bean.LoginBean;
import com.llt.superlibs.manager.bean.User;
import com.llt.superlibs.manager.listener.CommonListener;
import com.llt.superlibs.manager.model.ILoginModel;
import com.llt.superlibs.manager.model.impl.LoginModelImpl;
import com.llt.superlibs.ui.iview.ILoginView;
import com.llt.superlibs.utils.LogUtils;
import com.llt.superlibs.utils.Utils;

public class LoginPresenter {
	private static final String TAG = LoginPresenter.class.getSimpleName();
	private ILoginView mILoginView;
	private ILoginModel mLoginModel;

	public LoginPresenter(ILoginView view) {
		mILoginView = view;

		mLoginModel = new LoginModelImpl();
	}



	/**
	 * 调用Model层进行数据逻辑处理,传入回调
	 */
	public void login(String params) {
		mLoginModel.loginModel(params, new CommonListener<LoginBean>() {

			@Override
			public void onSuccess(LoginBean loginBean) {
				if (!Utils.isNull(loginBean) && !Utils.isNull(loginBean.tmessage)) {
					mILoginView.showTMessage(loginBean.tmessage);
				}
				if (Utils.isNull(loginBean)) {
					mILoginView.loginFailure("登录失败", false);
					return;
				}
				if (loginBean.code.equals("0000")) {
					User user = new User();
					user.accessToken = loginBean.data.accessToken;
					user.memberId = loginBean.data.memberId;
					user.secret = loginBean.data.secret;
					// 根据不同结果对view进行通知
					mILoginView.loginSuccess(user);
				} else if (loginBean.code.equals("52003")) {
					if (loginBean.data != null) {
						mILoginView.loginFailure(loginBean.msg,
								loginBean.data.requireCaptcha);
					} else {
						mILoginView.loginFailure("账号已冻结", false);
					}
				} else if (loginBean.code.equals("52004") || loginBean.code.equals("52008")) {
					if (loginBean.data != null) {
						mILoginView.loginFailure(loginBean.msg,
								loginBean.data.requireCaptcha);
					} else {
						mILoginView.loginFailure("您输入的账号或密码不正确 ，请核对后重新输入。", false);
					}
				} else if (loginBean.code.equals("52006")) {
					if (loginBean.data != null) {
						mILoginView.loginFailure(loginBean.msg,
								loginBean.data.requireCaptcha);
					} else {
						mILoginView.loginFailure("验证码已经失效", false);
					}
				} else if (loginBean.code.equals("52007")) {
					if (loginBean.data != null) {
						mILoginView.loginFailure(loginBean.msg,
								loginBean.data.requireCaptcha);
					} else {
						mILoginView.loginFailure("您输入的验证码有误", false);
					}
				} else if (loginBean.code.equals("52005")) {
					if (loginBean.data != null) {
						mILoginView.loginFailure(loginBean.msg,
								loginBean.data.requireCaptcha);
					} else {
						mILoginView.loginFailure("登录失败", false);
					}
				} else {
					if (loginBean.data != null) {
						mILoginView.loginFailure(loginBean.msg,
								loginBean.data.requireCaptcha);
					} else {
						mILoginView.loginFailure("登录失败", false);
					}
				}
			}

			@Override
			public void onFailure(String result) {
				// 根据不同结果对view进行通知
				LogUtils.debug(TAG, "" + result);
				mILoginView.loginFailure("网络不给力，请重试！", false);
			}
		});
	}



}
