package com.llt.superlibs.ui.iview;

import com.llt.superlibs.base.IBaseView;
import com.llt.superlibs.manager.bean.User;


public interface ILoginView extends IBaseView{
    void loginSuccess(User user);
    // 登陆失败 提示回调
    void loginFailure(String result,boolean requireCaptcha);
}
