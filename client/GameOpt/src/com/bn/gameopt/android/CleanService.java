package com.bn.gameopt.android;

import com.bn.gameopt.android.utils.GameOptUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CleanService extends Service {

	private static CleanService _instance;

	public static CleanService getInstance() {
		return _instance;
	}

	boolean stopped = false;

	@Override
	public void onCreate() {
		super.onCreate();
		_instance = this;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void doCleanInBackground() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (stopped) {
						break;
					}
					GameOptUtils.cleanAll(getApplicationContext());
					try {
						// TODO: sleep time
						Thread.sleep(600000);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		stopped = false;
		doCleanInBackground();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		stopped = true;
		super.onDestroy();
	}
}
