package com.steve.mathlete;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class GameOverScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over_screen);
		
		
	}

	protected void onStart() {
		super.onStart();
		
		TextView timerView = (TextView) findViewById(R.id.gameOverScoreView);
		timerView.setText("Score = " + DisplayRan.numberCorrect);
	}
	
	public void runRestart(View view){
    	Intent runRestart = new Intent(this, DisplayRan.class);
    	startActivity(runRestart);
    	
    }
	
	public void runHomeRestart(View view){
    	Intent runHomeRestart = new Intent(this, MainActivity.class);
    	startActivity(runHomeRestart);
    	
    }
	
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.game_over_screen, menu);
//		return true;
//	}

}
