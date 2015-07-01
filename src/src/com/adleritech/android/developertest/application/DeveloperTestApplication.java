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
	
	static final int MSG_OUT_REGISTER = 1;
	static final int MSG_OUT_ON_BROADCAST = 2;
	static final int MSG_OUT_GET_STATE = 3;

	static final int MSG_IN_OK = 999;
	static final int MSG_IN_ERROR = 1000;
	static final int MSG_IN_ON_RIDE_START = 1001;
	static final int MSG_IN_ON_RIDE_FINISH = 1002;
	static final int MSG_IN_STATE = 1003;

	static final int STATE_WAITING = 1;
	static final int STATE_BROADCASTING = 2;
	static final int STATE_RIDE = 3;
	
	public static final String SERVICE_NAME = "com.adleritech.android.developertest.SimulatorService";
    public ServiceConnection mConnection = new ServiceConnection() {
    
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
	      Intent intent = new Intent(SERVICE_NAME);
	      bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

	}

}
