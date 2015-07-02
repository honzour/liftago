package com.adleritech.android.developertest.application;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class DeveloperTestApplication extends Application {

	public static DeveloperTestApplication sInstance;

	protected static final int MSG_OUT_REGISTER = 1;
	protected static final int MSG_OUT_ON_BROADCAST = 2;
	protected static final int MSG_OUT_GET_STATE = 3;

	protected static final int MSG_IN_OK = 999;
	protected static final int MSG_IN_ERROR = 1000;
	protected static final int MSG_IN_ON_RIDE_START = 1001;
	protected static final int MSG_IN_ON_RIDE_FINISH = 1002;
	protected static final int MSG_IN_STATE = 1003;

	protected static final int STATE_UNDEFINED = 0;
	protected static final int STATE_WAITING = 1;
	protected static final int STATE_BROADCASTING = 2;
	protected static final int STATE_RIDE = 3;

	protected static final String SERVICE_NAME = "com.adleritech.android.developertest.SimulatorService";

	protected int mCurrentState = STATE_UNDEFINED;
	public Activity mCurrentActivity = null;
	protected Messenger mService = null;
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	protected boolean mBound = false;
	
	protected ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = new Messenger(service);
			sendMessage(MSG_OUT_REGISTER);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	};
	
	class IncomingHandler extends Handler {
		
		protected void handleInState(Message msg) {
			switch (msg.arg1) {
			case STATE_WAITING:
				startActivityIfNeeded(WaitingActivity.class, msg.arg1);
				break;
			case STATE_BROADCASTING:
				startActivityIfNeeded(BroadcastingActivity.class, msg.arg1);
				break;
			case STATE_RIDE:
				startActivityIfNeeded(RideActivity.class, msg.arg1);
				break;
			default:
				DeveloperTestApplication.this.sendMessage(MSG_OUT_GET_STATE);
				break;
			}
			mCurrentState = msg.arg1;
		}
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_IN_STATE:
				handleInState(msg);
				break;
			case MSG_IN_OK:
				if (getCurrentState() == STATE_WAITING)
				{
					startActivityIfNeeded(BroadcastingActivity.class, STATE_BROADCASTING);
					mCurrentState = STATE_BROADCASTING;
				}
				break;
			case MSG_IN_ERROR:
				handleInState(msg);
				break;
			case MSG_IN_ON_RIDE_FINISH:
				startActivityIfNeeded(WaitingActivity.class, STATE_WAITING);
				mCurrentState = STATE_WAITING;
				break;
			case MSG_IN_ON_RIDE_START:
				startActivityIfNeeded(RideActivity.class, STATE_RIDE);
				mCurrentState = STATE_RIDE;
				break;
			default:
				DeveloperTestApplication.this.sendMessage(MSG_OUT_GET_STATE);
				break;
			}
		}
	}

	public int getCurrentState()
	{
		return mCurrentState;
	}
	
	protected void startActivityIfNeeded(Class<?> activity, int newState)
	{
		if (mCurrentState == newState)
			return;
		
		if (mCurrentActivity != null) {
			mCurrentActivity.finish();
			mCurrentActivity = null;
		}
		Intent intent = new Intent(sInstance, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sInstance.startActivity(intent);
	}


	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}
	
	protected void sendMessage(int message)
	{
		try {
			Message msg = Message.obtain(null, message);
			msg.replyTo = mMessenger;
			mService.send(msg);
		} catch (RemoteException e) {
			Toast.makeText(this, R.string.service_send_error, Toast.LENGTH_LONG).show();
		}
	}
	
	public void bindService()
	{
		if (!mBound)
		{
			Intent intent = new Intent(SERVICE_NAME);
			bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
			mBound = true;
		}
	}

	public void unbindService() {
		if (mBound)
		{
			mBound = false;
			unbindService(mConnection);
		}
	}
	
	public void broadcast()
	{
		sendMessage(MSG_OUT_ON_BROADCAST);
	}
}
