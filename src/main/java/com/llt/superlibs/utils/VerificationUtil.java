package com.llt.superlibs.utils;


import com.llt.superlibs.manager.bean.ResponseBean;

/**
 * 验证工具类
 * 
 */
public class VerificationUtil {

	/**
	 * 数据返回成功
	 */
	private final static String CODE_SUCEED = "0000";

	/**
	 * token失效状态码
	 */
	public static final String TOKEN_OUT_0099 = "0099";

	public static final String TOKEN_OUT_0098 = "0098";

	
	
	/**
	 * 验证返回结果是否成功
	 * 
	 * @param resp
	 * @return
	 */
	public static ResponseBean verificationResponse(ResponseBean resp) {

		ResponseBean response = new ResponseBean();
		response.msg = resp.msg;
		try {

			if (Utils.isNull(resp)) {
				return response;
			}

			if (resp.code.equals(CODE_SUCEED)) {
				response.success = true;
			} else if (resp.code.equals(TOKEN_OUT_0098)
					|| resp.code.equals(TOKEN_OUT_0099)) {
				response.tokenMiss = true;
			}

			return response;

		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(e);
		}

		return response;
	}


}
