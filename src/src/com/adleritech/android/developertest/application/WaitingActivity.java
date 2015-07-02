package com.adleritech.android.developertest.application;

import android.os.Bundle;
import android.view.View;


public class WaitingActivity extends BaseActivity {
	
	protected View mProgressBar;
	protected View mBroadcastButton;
	
	protected void setGui()
	{
		mBroadcastButton.setEnabled(!DeveloperTestApplication.sInstance.mBroadcastButtonPressed);
		mProgressBar.setVisibility(DeveloperTestApplication.sInstance.mBroadcastButtonPressed ?
				View.VISIBLE : View.INVISIBLE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting);
		mProgressBar = findViewById(R.id.waiting_progress);
		mBroadcastButton = findViewById(R.id.waiting_broadcast);
		setGui();
		mBroadcastButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DeveloperTestApplication.sInstance.broadcast();
				DeveloperTestApplication.sInstance.mBroadcastButtonPressed = true;
				setGui();
			}
		});
		
	}
}
