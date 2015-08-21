package com.llt.superlibs.manager.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * 请求返回基类
 * 
 */
public class ResponseBean implements Serializable {

	/**
	 * 返回消息字符串
	 */
	@SerializedName("tmessage")
	public String tmessage;
	/**
	 * 返回消息字符串
	 */
	@SerializedName("msg")
	public String msg;

	/**
	 * 返回消息code
	 */
	@SerializedName("code")
	public String code;
	/**
	 * 成功返回标志
	 */
	public boolean success = false;

	/**
	 * token丢失
	 */
	public boolean tokenMiss = false;

	/**
	 * 错误信息
	 */
	@SerializedName("_error")
	public ResponseError error;

	public class ResponseError implements Serializable {
		/**
		 * app标志
		 */
		@SerializedName("appcode")
		public String appcode;
		/**
		 * 错误描述
		 */
		@SerializedName("msg")
		public String msg;
		/**
		 * 错误内容
		 */
		@SerializedName("data")
		public String data;

	}
}
