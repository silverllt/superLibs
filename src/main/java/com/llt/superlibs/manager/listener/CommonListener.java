package com.llt.superlibs.manager.listener;


public interface CommonListener<T> {

    /** 成功回调 */
    void onSuccess(T t);

    /** 失败回调 */
    void onFailure(String result);
}
