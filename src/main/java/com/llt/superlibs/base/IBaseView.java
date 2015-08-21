package com.llt.superlibs.base;

public interface IBaseView {
    /**
     * 显示进度条
     * @param cancelable 是否可取消
     * @param message 要显示的信息
     */
    void showProgress(boolean cancelable, String message);

    /** 隐藏进度条 */
    void hideProgress();

    /**
     * 根据资源文件id弹出toast
     *
     * @param resId
     *            资源文件id
     */
    void showToast(int resId);

    /**
     * 根据字符串弹出toast
     *
     * @param msg
     *            提示内容
     */
    void showToast(String msg);

    /** 通用网络异常提示 */
    void showNetError();

    /** 通用解析异常提示 */
    void showParseError();

    /** token失效 */
    void tokenInvalid();
}
