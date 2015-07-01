package com.adleritech.android.developertest.application;

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

	protected static final int STATE_WAITING = 1;
	protected static final int STATE_BROADCASTING = 2;
	protected static final int STATE_RIDE = 3;
	
	protected static final String SERVICE_NAME = "com.adleritech.android.developertest.SimulatorService";
	
	protected int mCurrentState  = STATE_WAITING;
	
	
    protected ServiceConnection mConnection = new ServiceConnection() {
    
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  MSG_IN_OK:
                    break;
                default:
                	break;
            }
        }
    }
    
    Messenger mService = null;
    final Messenger mMessenger = new Messenger(new IncomingHandler());

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
        	mService = new Messenger(service);
        	try
        	{
        		Message msg = Message.obtain(null, MSG_OUT_REGISTER);
        		msg.replyTo = mMessenger;
        		mService.send(msg);
        	}
        	catch (RemoteException e)
        	{
        		// TODO
        	}
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		Intent intent = new Intent(SERVICE_NAME);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	public void unbindService() {
		unbindService(mConnection);
	}
}
