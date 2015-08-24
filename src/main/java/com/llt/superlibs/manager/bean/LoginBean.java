package com.llt.superlibs.manager.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;


public class LoginBean extends ResponseBean implements Serializable {
	/**
	 * 登录返回的memberId
	 */
	@SerializedName("data")
	public Data data;
	public class Data implements Serializable{
		/**
		 * 登录返回的memberId
		 */
		@SerializedName("memberId")
		public long memberId;
		/**
		 * accessToken
		 */
		@SerializedName("accessToken")
		public String accessToken;
		/**
		 * secret
		 */
		@SerializedName("secret")
		public String secret;
		/**
		 * 是否需要验证码
		 */
		@SerializedName("requireCaptcha")
		public boolean requireCaptcha;
	}
}
