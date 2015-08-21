package com.llt.superlibs.Net;

import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

/**
 * Volley自定义POST请求
 * 
 * @author lyc
 * 
 */
public class StringPostRequest extends StringRequest {
	private static final String TAG = StringPostRequest.class.getSimpleName();
	private Map<String, String> params;
	private final String ENCODEING = "UTF-8";

	public StringPostRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(Method.POST, url, listener, errorListener);
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params == null ? super.getParams() : params;
	}

	@Override
	protected String getParamsEncoding() {
		return ENCODEING;
	}

}
