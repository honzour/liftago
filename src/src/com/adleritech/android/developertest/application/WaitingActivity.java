package com.adleritech.android.developertest.application;

import android.os.Bundle;
import android.view.View;


public class WaitingActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting);
		final View broadcastButton = findViewById(R.id.waiting_broadcast);
		broadcastButton.setEnabled(
				DeveloperTestApplication.sInstance.getCurrentState() == DeveloperTestApplication.STATE_WAITING);
		broadcastButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DeveloperTestApplication.sInstance.broadcast();
				broadcastButton.setEnabled(false);
			}
		});
		
	}
}
