package com.steve.mathlete;

import android.os.Handler;
import android.widget.TextView;

public class Sync {
	static private Handler handler = new Handler();
	Runnable task;
	
	public Sync(Runnable task, long time) {
			this.task = task;
			handler.removeCallbacks(task);
			handler.postDelayed(task, time);
			DisplayRan.timerTime =- 1;
			
		return;
		}

	
	
}
