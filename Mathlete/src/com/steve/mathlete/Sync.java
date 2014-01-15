package com.steve.mathlete;

import android.os.Handler;

public class Sync {
	static private Handler handler = new Handler();
	Runnable task;
	
public void sync(Runnable task, long time) {
		this.task = task;
		handler.removeCallbacks(task);
		handler.postDelayed(task, time);
	
	return;
	}
	
}
