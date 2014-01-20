package com.steve.mathlete;

import android.os.Handler;
import android.widget.TextView;

public class Sync {
	static public Handler timeHandler = new Handler();
	Runnable task;
	
	
	public Sync(Runnable task, long time) {
			this.task = task;
			timeHandler.removeCallbacks(task);
			timeHandler.postDelayed(task, time);
			
			
		return;
		}

	
	
}
