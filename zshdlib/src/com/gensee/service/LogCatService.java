package com.gensee.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LogCatService extends Service {

	@Override
	public void onCreate() {
//		LogcatHelper.getInstance(this).start();
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
//		LogcatHelper.getInstance(this).stop();
		super.onDestroy();
	}
}
