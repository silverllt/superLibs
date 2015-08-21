package com.llt.superlibs.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ClipboardManager;
import android.content.Context;


/**
 * 字体串操作相关工具类
 * 
 */
public final class Utils {

	static DecimalFormat df = null;

	/** 判断字符串是否为空 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str) /* || "null".equals(str) */;
	}

	public static boolean isNull(Object obj) {
		if (null == obj || obj == "" || obj.equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isNumber(String str) {
		String reg = "\\d+(\\.\\d+)?";
		// java.util.regex.Pattern
		// pattern=java.util.regex.Pattern.compile("[0-9]*");
		Pattern pattern = Pattern.compile(reg);

		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isInviteCode(String str) {
		if (str != null) {
			str = str.trim();
			Pattern p = Pattern.compile("[0-9a-zA-Z\u4e00-\u9fa5]+",
					Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(str);
			return m.matches();
		}
		return false;

	}

	public static int ceil(float f) {
		int i = 0;
		if (f > 0 && f <= 1) {
			i = 1;
		} else if (f > 1 && f <= 2) {
			i = 2;
		} else if (f > 2 && f <= 3) {
			i = 3;
		} else if (f > 3 && f <= 4) {
			i = 4;
		} else if (f > 4 && f <= 5) {
			i = 5;
		}
		return i;
	}

	/** 判断字符串是否为邮箱 */
	public static boolean isEmail(String str) {
		Pattern pattern = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher mc = pattern.matcher(str);
		return mc.matches();
	}

	/**
	 * 根据正则表达式 判断号码是否符合手机号码规范
	 * 
	 * @param mobiles
	 *            手机号码
	 * @return boolean 正规的手机格式返回true 不正规模式返回false
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,3,5-9])|(17[6-8]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断字符串是否都是字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isStrAndLetter(String str) {
		boolean flag = true;
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i + 1).matches("[\\u4e00-\\u9fbf]+")
					|| str.substring(i, i + 1).matches("[a-zA-Z]")) {
			} else {
				return false;
			}
		}
		return flag;
	}

	/**
	 * <字符串截取方法　截取掉字符串最后一个字符>
	 * 
	 * @author christineRuan
	 * @date 2013-12-17 下午5:24:24
	 * @param intercept
	 * @return
	 * @returnType String
	 */
	public static String stringIntercept(String intercept) {
		return intercept.substring(0, intercept.length() - 1);
	}

	/**
	 * 
	 * <格式化double数据>
	 * 
	 * @author christineRuan
	 * @date 2014-1-20 下午8:06:10
	 * @param value
	 * @param doubleoFrmat
	 * @return
	 * @returnType String
	 */
	public static String doubleFormat(double value, String doubleoFrmat) {
		if (df == null) {
			df = new DecimalFormat(doubleoFrmat);
		}
		return df.format(value);
	}

	/**
	 * Float转String,获取2小数位字符串
	 * 
	 * @return
	 */
	public static String FloatToString(float value) {

		DecimalFormat fnum = new DecimalFormat("###,###,##0.00");
		String dd = fnum.format(value);

		return dd;

	}

	public static String toKM(String distance) {
		if (distance != null && !"".equals(distance)) {
			if (Double.valueOf(distance).doubleValue() >= 1000) {
				NumberFormat nf = new DecimalFormat("0.0");
				return Double.parseDouble(nf.format(Double.valueOf(distance)
						.doubleValue() / 1000)) + "公里";
			} else {
				return (int) Math.ceil(Double.valueOf(distance).doubleValue())
						+ "米";
			}
		} else {
			return "未设置";
		}
	}

	/**
	 * 
	 * TODO 判断是否为URL
	 * 
	 * @FileName AppUtil.java
	 * @author Simon.xin
	 */
	public static boolean isUrl(String url) {
		if (url.indexOf("http") > -1) {
			return true;
		}
		if (url.indexOf("www") > -1) {
			return true;
		}
		if (url.indexOf("com") > -1) {
			return true;
		}
		if (url.indexOf("cn") > -1) {
			return true;
		}
		return false;
	}

	public static ArrayList<String> getParamFromUrl(String url) {
		ArrayList<String> list = null;
		if (isNull(url)) {
			return list;
		}
		try {
			String[] item = url.split("\\?");
			if (isNull(item)) {
				return list;
			}
			String[] params = item[1].split("&");
			if (isNull(params)) {
				return list;
			}
			list = new ArrayList<String>();
			for (int i = 0; i < params.length; i++) {
				list.add(params[i].split("=")[1]);
			}
		} catch (Exception e) {
			// MonitorManager.getExceptionManager().onException(e, null);
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 实现文本复制功能 add by lvshixin
	 * 
	 * @param content
	 */
	@SuppressWarnings("deprecation")
	public static void copy(String content, Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}

	/**
	 * 实现粘贴功能 add by lvshixin
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String paste(Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return cmb.getText().toString().trim();
	}

	static String URL_CONTACT = "URL_CONTACT";

	/**
	 * 处理textview文字换行
	 * 
	 * @author Litten
	 * @date 2015-7-30下午5:04:46
	 * @param tx
	 * @param length
	 *            长度
	 * @return
	 */
	public static String wrapText(String tx, int length) {
		char[] chars = tx.toCharArray();
		StringBuilder sbStr = new StringBuilder();
		for (int i = 1; i <= chars.length; i++) {
			sbStr.append(chars[i - 1]);
			if (i % length == 0 && i > 1 && i != length) {
				sbStr.append("\n");
			}
		}
		return sbStr.toString();
	}


}
