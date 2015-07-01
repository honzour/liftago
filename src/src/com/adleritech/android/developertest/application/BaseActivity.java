package com.adleritech.android.developertest.application;

import android.app.Activity;

public class BaseActivity extends Activity {

	@Override
	public void onBackPressed() {
		DeveloperTestApplication.sInstance.unbindService();
		super.onBackPressed();
	}

}
