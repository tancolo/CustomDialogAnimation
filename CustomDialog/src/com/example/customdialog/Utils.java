/**
 *	
 * 项目名称: CustomDialog
 * 类名称: Utils
 * 类描述: 
 * 创建人: shrimpcolo  
 * 创建时间: 
 * 修改人: 
 * 修改时间: 
 * 备注: 
 *
 * @version 
 */
package com.example.customdialog;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;



public class Utils {
	
	//从 dp 的单位 转成为 px(像素)
	public static int dip2px(Context context, float dpValue) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dpValue * metrics.density + 0.5f;
		return (int) (px);
	}

	//从 px(像素) 的单位 转成为 dp
	public static int px2dip(Context context, int pxValue) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = pxValue / metrics.density + 0.5f;
		return (int) dp;
	}
}
