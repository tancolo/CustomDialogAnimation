/**
 *	
 * 项目名称: CustomDialog
 * 类名称: Log
 * 类描述: 
 * 创建人: shrimpcolo  
 * 创建时间: 
 * 修改人: 
 * 修改时间: 
 * 备注: 参考InCallUI
 *
 * @version 
 */
package com.example.customdialog;

public class Log {
	public static final String TAG = "CustomDialog";

	public static final boolean DEBUG = true;
	public static final String TAG_DELIMETER = " - ";

	public static void d(String tag, String msg) {
		if (DEBUG) {
			android.util.Log.d(TAG, delimit(tag) + msg);
		}
	}

	public static void d(Object obj, String msg) {
		if (DEBUG) {
			android.util.Log.d(TAG, getPrefix(obj) + msg);
		}
	}

	private static String delimit(String tag) {
		return tag + TAG_DELIMETER;
	}

	private static String getPrefix(Object obj) {
		return (obj == null ? "" : (obj.getClass().getSimpleName() + TAG_DELIMETER));
	}

}
