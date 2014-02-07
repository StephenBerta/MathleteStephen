package com.steve.mathlete;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NumberOne extends Activity {
	private boolean isDisplayOperation = true;
	private boolean userInput = false;
	private int slideTimer = 3;
	private int slideCounter = 0;
	private int checkpointThreshold = 4;		//slide number when we allow input(checkpoint)
	
	private boolean isPaused = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_one);
		TextView displayNumbers = (TextView) findViewById(R.id.displayHere);
        displayNumbers.setText("1");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.number_one, menu);
		return true;
	}
	
	protected void onStart() {
		super.onStart();
		runGame();
		
	}
	
	protected void onRestart() {
		super.onRestart();
	}
	
	protected void onResume() {
		super.onResume();
	}
	
	protected void onPaused() {
		super.onPause();
		isPaused = true;
	}
	
	protected void onStop() {
		super.onStop();
		isPaused = true;
	}
	
	protected void onDestroy() {
		super.onDestroy();
	}
	
	//reset variables at start or whenever needed
	public void resetVariables() {
		userInput = false;
		slideCounter = 0;
		
		return;
	}

	public void runGame() {
		gameTimer();
		//if the slide
		if(userInput) {
//			DisplayRan.startKeypad();
		} else {
//			mKeypadGrid.setVisibility(View.GONE);
		}
		
	}
	
	public void gameTimer() {
		
		final Handler timeHandler = new Handler();
		final Runnable timer = new Runnable() {
			@Override
			public void run() {
				if(slideTimer > 0 && !isPaused)  {
					try{
					slideTimer -= slideTimer;
//					timeHandler.postDelayed(this, 1000);
					}
					catch(Exception e) {
						
					}
					finally{
						if(slideTimer > 0) {
							timeHandler.postDelayed(this, 1000);
						}
					}
				}
				if(slideTimer == 0 && !isPaused) {
					slideCounter += slideCounter;
					
					Toast correctToast = Toast.makeText(getApplicationContext(), slideCounter, Toast.LENGTH_SHORT);
					correctToast.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);
					correctToast.show();
					
					if(slideCounter == checkpointThreshold) {
						userInput = true;
					}
					runGame();
					
				}
				
			}
		};
		
		if(slideTimer > 1) {
			timeHandler.postDelayed(timer, 1000);
		}
		if(slideTimer == 0) {
			timeHandler.removeCallbacksAndMessages(null);
		}
		
	}
	
	
}
