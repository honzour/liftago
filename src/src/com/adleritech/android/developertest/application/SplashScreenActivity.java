package com.adleritech.android.developertest.application;

import android.os.Bundle;


public class SplashScreenActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		if (DeveloperTestApplication.sInstance.mCurrentState == DeveloperTestApplication.STATE_UNDEFINED)
		{
			// Expected, this is a commont app start
			DeveloperTestApplication.sInstance.bindService();
		}
		else
		{
			// Still in memory but on background and now being relaunched
			final int oldState = DeveloperTestApplication.sInstance.mCurrentState;
			DeveloperTestApplication.sInstance.mCurrentState = DeveloperTestApplication.STATE_UNDEFINED;
			DeveloperTestApplication.sInstance.handleInState(oldState);
		}
	}
}
