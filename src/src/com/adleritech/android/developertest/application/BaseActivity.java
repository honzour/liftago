package com.adleritech.android.developertest.application;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	@Override
	public void onBackPressed() {
		DeveloperTestApplication.sInstance.unbindService();
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DeveloperTestApplication.sInstance.mCurrentActivity = this;
	}

	
}
