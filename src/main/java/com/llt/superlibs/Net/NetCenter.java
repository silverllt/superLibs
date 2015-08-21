package com.llt.superlibs.Net;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.llt.superlibs.app.AppManager;
import com.llt.superlibs.utils.LogUtils;
import com.llt.superlibs.utils.NetUtils;


import java.util.HashMap;
import java.util.Map;

/**
 * 网络访问控制中心 用于统一管理网络访问接口及相关配置
 */
public class NetCenter {

    private static NetCenter mNetCenter;
    private static Map<Context, RequestQueue> mRequestMap;
    private static String timestamp = "";

    private NetCenter() {
        mRequestMap = new HashMap<Context, RequestQueue>();
    }

    public synchronized static NetCenter getInstance() {
        if (mNetCenter == null) {
            mNetCenter = new NetCenter();
        }

        return mNetCenter;
    }


    /** 清除当前Activity的请求队列 */
    public void clearRequestQueue() {
        Context context = AppManager.getAppManager().currentActivity();
        if (context != null) {
            VolleyRequestManager.cancelAll(context.getClass().getSimpleName());
        } else {
            VolleyRequestManager.cancelAll("");
        }
    }

    /**
     * 发起一个带tag的post请求
     *
     * @param params
     *            请求参数
     * @param url
     *            请求url地址
     * @param listener
     *            请求成功回调
     * @param errorListener
     *            请求失败回调
     */
    public void post(Map<String, String> params, String url,
                     Response.Listener<String> listener, Response.ErrorListener errorListener) {
        Context context = AppManager.getAppManager().currentActivity();
        // 判断网络是否可用
        NetUtils netUtils = new NetUtils(context);
        if (!netUtils.isNetworkConnected()) {
            errorListener.onErrorResponse(new VolleyError("网络不可用，请检查网络设置."));
            return;
        }

        StringPostRequest mRequest = new StringPostRequest(url, listener,
                errorListener);
        mRequest.setParams(params);
        VolleyRequestManager.addRequest(mRequest, "");
    }



    /**
     * 发起一个带tag的get请求
     *
     * @param url
     *            请求url地址
     * @param listener
     *            请求成功回调
     * @param errorListener
     *            请求失败回调
     */
    public void get(String url, Response.Listener<String> listener,
                    Response.ErrorListener errorListener) {
        Context context = AppManager.getAppManager().currentActivity();
        // 判断网络是否可用
        NetUtils netUtils = new NetUtils(context);
        if (!netUtils.isNetworkConnected()) {
            return;
        }

        StringRequest mRequest = new StringRequest(url, listener, errorListener);
        // RequestQueue mRequestQueue = mRequestMap.get(context);
        VolleyRequestManager.addRequest(mRequest, "");
    }

    /**
     * 发起一个带tag的自定义请求
     *
     * @param <T>
     *
     * @param url
     *            请求url地址
     * @param listener
     *            请求成功回调
     * @param errorListener
     *            请求失败回调
     */
    public <T> void gson(int method, String url, Class<T> clazz,
                         Map<String, String> headers, Map<String, String> params,
                         Response.Listener<T> listener, Response.ErrorListener errorListener) {

        Context context = AppManager.getAppManager().currentActivity();
        // // 判断网络是否可用
        NetUtils netUtils = new NetUtils(context);
        if (!netUtils.isNetworkConnected()) {
            errorListener.onErrorResponse(new VolleyError());
            return;
        }
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        // 添加系统参数
//        addSystemParams(params);
        LogUtils.debug("params===>" + params.toString());

        Request<T> mRequest = new GsonRequest<T>(method, url, clazz, headers,
                params, listener, errorListener);

        mRequest = setRetryPolicy(mRequest);

        if (context != null) {
            LogUtils.debug("putRequestQueue :: "
                    + context.getClass().getSimpleName());
            VolleyRequestManager.addRequest(mRequest, context.getClass()
                    .getSimpleName());
        } else {
            LogUtils.debug("putRequestQueue :: " + " null ");
            VolleyRequestManager.addRequest(mRequest, "");
        }
    }

    private Request setRetryPolicy(Request request) {
        int timeout = 5 * 1000;

        request.setRetryPolicy(new DefaultRetryPolicy(timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));// 修改默认超时时间
        return request;

    }

}
