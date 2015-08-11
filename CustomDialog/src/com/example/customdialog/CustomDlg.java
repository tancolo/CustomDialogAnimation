/**
 *	
 * 项目名称: CustomDialog
 * 类名称: CustomDlg
 * 类描述: 
 * 创建人: shrimpcolo  
 * 创建时间: 
 * 修改人: 
 * 修改时间: 
 * 备注: 自定义对话框中实现属性动画
 *
 * @version 
 */
package com.example.customdialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.PointF;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDlg extends Dialog {

	private static final String TAG = "CustomDlg.java";
	private static CustomDlg customDialog = null;
	private static Context mContext = null;

	//相关控件变量
	private static ImageView mIvPlane = null;
	private static TextView mTvflyUp = null;
	private static TextView mTvflyDown = null;

	private static PointF point = null;
	private static PointF point0 = null;
	private static PointF point1 = null;
	private static PointF point2 = null;
	private static int planeType = 0;//0: up; 1: down
	private static final int TYPE_UP = 0;
	private static final int TYPE_DOWN = 1;

	public CustomDlg(Context context) {
		super(context);
		mContext = context;
	}

	public CustomDlg(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	protected CustomDlg(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
	}

	public static CustomDlg createDialog(Context context) {
		Log.d(TAG, "customDialog.");
		customDialog = new CustomDlg(context, R.style.CustomDialog);
		customDialog.setContentView(R.layout.dlg_custom_dialog);
		customDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		initWidget();

		return customDialog;
	}

	public static CustomDlg createDialog(Context context, int height) {
		return customDialog;
	}

	private static void initWidget() {

		//动画路径, 屏幕适配
		width_up = Utils.dip2px(mContext, WIDTH_UP_DP);
		width_down = Utils.dip2px(mContext, WIDTH_DOWN_DP);
		height = Utils.dip2px(mContext, HEIGHT_DP);

		//new PointF, point0, point2 不需要分配
		point = new PointF();
		point1 = new PointF();

		//初始化TextView
		mTvflyUp = (TextView) customDialog.findViewById(R.id.tv_dlg_flyup);
		mTvflyDown = (TextView) customDialog.findViewById(R.id.tv_dlg_flydown);
		setTVEnabled(true);

		//飞机动画载体ImageView
		mIvPlane = (ImageView) customDialog.findViewById(R.id.iv_small_plane);

		try {
			mTvflyUp.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					//准备并启动动画
					initValueAnimator(TYPE_UP);//up animation
				}
			});

			mTvflyDown.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					//准备并启动动画
					initValueAnimator(TYPE_DOWN);//down animation
				}
			});

		} catch (NullPointerException exp) {
			exp.printStackTrace();
		}
	}

	////////////////////////////////plane flying animation//////////////////////////
	private static ValueAnimator valueAnimator;
	private static final float WIDTH_UP_DP = 400.0f;//dp
	private static final float WIDTH_DOWN_DP = 310.0f;//dp
	private static final float HEIGHT_DP = 150.0f;//dp	

	//1080 x 1920分辨率,  Density = 3  理想的动画参数如下， 就可以根据屏幕特点做适配
	//private static int width_down = 900, height = 450; //down
	//private static int width_up = 1200; //up
	private static int width_down = 0, height = 0; //down
	private static int width_up = 0; //up	

	static class PlaneEvaluator implements TypeEvaluator<PointF> {

		private int type = 0;

		public PlaneEvaluator(int type) {
			//0: 飞机往上飞; 1: 往下飞
			this.type = type;
		}

		@Override
		public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

			final float t = fraction;
			float oneMinusT = 1.0f - t;

			point0 = (PointF) startValue;
			point2 = (PointF) endValue;
			if (this.type == 0) {//up
				point1.set(width_up, 0);
			} else {//down
				point1.set(width_down, 0);
			}

			point.x = oneMinusT * oneMinusT * (point0.x) + 2 * oneMinusT * t * (point1.x) + t * t * (point2.x);
			point.y = oneMinusT * oneMinusT * (point0.y) + 2 * oneMinusT * t * (point1.y) + t * t * (point2.y);

			if (this.type == 0) {
				point.y = -point.y;
			}
			//Log.d("TAG""fraction = " + fraction + ": " + endValue.toString() + ": point(x,y): " + point.toString());

			return point;
		}
	}

	private static void initValueAnimator(int type) {

		planeType = type;
		int width = 0;
		if (type == 0) {//up
			width = width_up;
		} else {//down
			width = width_down;
		}
		setTVEnabled(false);
		Log.d("TAG", "width = " + width + ", height = " + height + ", type = " + type);

		valueAnimator = ValueAnimator.ofObject(new PlaneEvaluator(type), new PointF(0, 0), new PointF(width, height));

		valueAnimator.setDuration(2000);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				PointF pointF = (PointF) animation.getAnimatedValue();
				//动画超出屏幕后需要取消动画，然后在cancel监听中做相关处理
				if (valueAnimator.getCurrentPlayTime() > 1500 && valueAnimator.isRunning()) {
					valueAnimator.cancel();
				}
				mIvPlane.setX(pointF.x);
				mIvPlane.setY(pointF.y);
			}
		});

		valueAnimator.addListener(new AnimatorListenerAdapter() {
			public void onAnimationCancel(Animator animation) {
				Log.d("TAG", "Cancel Animation!!!");
				setTVEnabled(true);

				//飞机不可见
				mIvPlane.setVisibility(View.INVISIBLE);
				customDialog.dismiss();

				//do something 
			}
		});

		valueAnimator.setTarget(mIvPlane);
		valueAnimator.setRepeatCount(1);
		valueAnimator.setRepeatMode(ValueAnimator.REVERSE);

		//开始动画
		mIvPlane.setVisibility(View.VISIBLE);
		valueAnimator.start();
	}

	private static void setTVEnabled(boolean enabled) {
		mTvflyUp.setEnabled(enabled);
		mTvflyDown.setEnabled(enabled);

	}

}
