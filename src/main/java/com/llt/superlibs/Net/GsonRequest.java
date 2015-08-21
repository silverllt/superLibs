package com.llt.superlibs.Net;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.bbg.monitor.MonitorManager;
import com.google.gson.Gson;
import com.llt.superlibs.manager.bean.ResponseBean;
import com.llt.superlibs.utils.LogUtils;
import com.llt.superlibs.utils.VerificationUtil;

/**
 * Volley自定义请求
 * 
 * @author lyc
 * 
 */
public class GsonRequest<T> extends Request<T> {
	private final Gson mGson = new Gson();
	private final Class<T> mClazz;
	private final Listener<T> mListener;
	private final Map<String, String> mHeaders;
	private final Map<String, String> mParams;
	private final String ENCODEING = "UTF-8";
	private String url = "";


	public GsonRequest(int method, String url, Class<T> clazz,
			Map<String, String> headers, Map<String, String> params,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.url = url;
//		if (headers == null) {
//			// 从Application中取出当前的cookie信息
//			Map<String, String> header = new HashMap<String, String>();
//			String cookiesHeader = MyApplication.getCookiesHeader(getUrl());
//			header.put("Cookie", cookiesHeader);
//			this.mHeaders = header;
//		} else {
//			this.mHeaders = headers;
//		}
		this.mHeaders = headers;
		this.mParams = params;
		this.mListener = listener;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return mHeaders != null ? mHeaders : super.getHeaders();
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return mParams != null ? mParams : super.getParams();
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	void invali(String json) {
		try {

			ResponseBean resp = mGson.fromJson(json, ResponseBean.class);
			resp = VerificationUtil.verificationResponse(resp);

			if (!resp.success) {
				MonitorManager.getHttpMonitorManager().onException(
						mParams.get("method"), json, null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		String json = "undefine";
		String method = mParams.get("method");

		try {

			// int i = 5 / 0;

			json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			LogUtils.debug("response:" + json);

			// if (jsonFlag) {
			// JSONObject jsonObject = new JSONObject(json);
			// Class<?>[] argTypes = { JSONObject.class }; // 指明所要调用的构造方法的形参
			// Constructor<?> constructor = mClazz.getConstructor(argTypes);
			// return (Response<T>) Response.success(
			// constructor.newInstance(jsonObject.opt("data")),
			// HttpHeaderParser.parseCacheHeaders(response));
			// }
			return Response.success(mGson.fromJson(json, mClazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (Exception e) {
			MonitorManager.getHttpMonitorManager().onException(method, json,
					null);

			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected String getParamsEncoding() {
		return ENCODEING;
	}

}
