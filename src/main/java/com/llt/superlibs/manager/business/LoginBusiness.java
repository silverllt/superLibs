package com.llt.superlibs.manager.business;

import java.util.Map;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.llt.superlibs.Net.NetCenter;
import com.llt.superlibs.manager.bean.LoginBean;

public class LoginBusiness {
	public static void Login(String params, Response.Listener<LoginBean> listener,
			ErrorListener errorListener) {
		NetCenter netCenter = NetCenter.getInstance();
//		String url = URLs.getURL(URLs.API_USER_LOGIN);
		 String url = "http://www.acfun.tv";
//		Map<String, String> param = netCenter.getBaseParams();
//		param.put("method", Constants.BUBUGAO_MOBILE_GLOBAL_LOGIN);
//		param.put("params", params);
//		netCenter.gson(Method.POST, url, LoginBean.class, null, param,
//				listener, errorListener);
	}


}
