package com.llt.superlibs.manager.model;


import com.llt.superlibs.manager.bean.LoginBean;
import com.llt.superlibs.manager.listener.CommonListener;

public interface ILoginModel {

    void loginModel(String params, CommonListener<LoginBean> listener);

}
