package com.example.customdialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private CustomDlg customDlg;
	private TextView tv_popUp;
	private CustomDlgFullScreenAnimation customDlgFullScreenAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_popUp = (TextView) findViewById(R.id.tv_popup_dlg);
	}

	public void onClick(View view) {
		Log.d(this, "onClick ....");
		// showCustomDailog(true);
		showFullCustomDailog(true);
		// Toast.makeText(this, getResources().getString(R.string.tv_pop_dlg),
		// Toast.LENGTH_SHORT).show();
	}

	private void showCustomDailog(boolean canAble) {
		if (customDlg == null) {
			customDlg = CustomDlg.createDialog(this);
		}

		customDlg.setCancelable(canAble);
		// customDlg.setCanceledOnTouchOutside(canAble);
		customDlg.show();
	}

	private void showFullCustomDailog(boolean canAble) {
		if (customDlgFullScreenAnim == null) {
			customDlgFullScreenAnim = CustomDlgFullScreenAnimation.createDialog(this);
		}

		customDlgFullScreenAnim.setCancelable(canAble);
		// customDlg.setCanceledOnTouchOutside(canAble);
		customDlgFullScreenAnim.show();
	}

}
