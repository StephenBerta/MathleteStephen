package com.steve.mathlete;


import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class DisplayRan extends Activity {
	public static int numberCorrect = 0;
	boolean isFirst = true;
	boolean isCorrect = false;
	public static int answerChangeFlag[] = {0, 5, 10};
	int answerSet[] = null;
	CharSequence firstText = "0";
	CharSequence value = null; 
	static int timerTime = 15;
	static String timerTimeString = null;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		
		super.onCreate(savedInstanceState);
		
		//get intent
		//Intent intent = getIntent();
				
		
		
		
		setContentView(R.layout.activity_display_ran);
		
		
		
		resetVariables();
		

		
		
		
		
       
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
             // Show the Up button in the action bar.
             getActionBar().setDisplayHomeAsUpEnabled(true);
         }
         
	}
	
//	protected void onRestart() {
//		timerTime = 15;
//		run();
//		sync();
//	}
	
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_ran, menu);
		return true;
	}
	
	protected void onStart() {
		super.onStart();
		run();
		sync();
		resetVariables();
		
//		numberCorrectView.setText("Correct:" + numberCorrect);
	}
	
	public void startKeypad() {
		GridView mKeypadGrid;
		KeypadAdapter mKeypadAdapter;
		
		mKeypadGrid = (GridView) findViewById(R.id.grdButtons);
		mKeypadAdapter = new KeypadAdapter(this);
		mKeypadGrid.setAdapter(mKeypadAdapter);
		
		mKeypadAdapter.setOnButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button btn = (Button) v;
				KeypadValue keypadValue = (KeypadValue) btn.getTag();
				InputHandling(keypadValue);
				
				
			}
		});
		
		mKeypadGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {	}
		});
}
	
	public void resetVariables() {
		numberCorrect = 0;
		isFirst = true;
		isCorrect = false;
		CharSequence firstText = "0";
		timerTime = 15;

		//initialize a zero for user input
		TextView userInputText = (TextView) findViewById(R.id.userAnswer);
		userInputText.setText(firstText);
		//initialize number correct
		TextView numberCorrectView = (TextView) findViewById(R.id.numberCorrect);
		numberCorrectView.setText("Correct:" + "0");
				
	}
				

	
	public void run() {
		if(numberCorrect == 0 || numberCorrect == answerChangeFlag[1]) {
		startKeypad();
		}
		String operator = "0";
		answerSet = ranSelect();
		String number = "0";
		
		
		
		
		
		
		
		if(MainActivity.isAddition) {
			operator = "+";
		}
		if(MainActivity.isSubtraction) {
			operator = "-";
		}
		if(MainActivity.isSubtraction && answerSet[2] > answerSet[1]) {
			while(answerSet[2] > answerSet[1]) {
				answerSet = ranSelect();
			}
			
		}
		if(numberCorrect < answerChangeFlag[1]) {
			number = answerSet[1] + operator + answerSet[2];
		}
		if(numberCorrect >= answerChangeFlag[1]) {
			number = answerSet[1] + operator + answerSet[2] + operator + answerSet[3];
		}
		
		
		final TextView displayNumbers = (TextView) findViewById(R.id.displayHere);
        displayNumbers.setText(number);
        
        
        
       
        	
        	
        	
		return;
		
	}
	
	
	public void InputHandling(KeypadValue keypadValue) {

		
		String text = keypadValue.getText().toString();
		TextView userInputText = (TextView) findViewById(R.id.userAnswer);
		String currentInput = userInputText.getText().toString();
		
		int inputLength = currentInput.length();

		
		switch(keypadValue) {
		case BCK:
			int endIndex = inputLength - 1;
			
			if(endIndex < 1) {
				userInputText.setText("0");
			} else {
				userInputText.setText(currentInput.subSequence(0, endIndex));
			}
			
			break;
			
		case CLR:
			userInputText.setText("");
			break;
			
		default:
				if(Character.isDigit(text.charAt(0))) {
					if(currentInput.equals("0")) {
						userInputText.setText(text);
						value = userInputText.getText();
						isFirst = false;
						checkCorrect();
					} else {
						userInputText.append(text);
						value = userInputText.getText();
						isFirst = false;
						checkCorrect();
												
					}
				}
				break;
		}
		
		return;
	}

		
		
		
	
	

	
public int[] ranSelect() {
	 	
	 	
    	Random rand = new Random();
        Integer min1 = 0;
        Integer max1 = 10;
        Integer min2 = 1;
        Integer max2 = 12;
        Integer min3 = 0;
        Integer max3 = 15;
        int numAnswer[] = new int[10];
        
     
        
        numAnswer[1] = rand.nextInt((max1 - min1) + 1) + min1;
        numAnswer[2] = rand.nextInt((max2 - min2) + 1) + min2;
        numAnswer[3] = rand.nextInt((max3 - min3) + 1) + min3;
        
        //addition answer
        if(numberCorrect < answerChangeFlag[1]){
        numAnswer[5] = numAnswer[1] + numAnswer[2];
        }
        if(numberCorrect >= answerChangeFlag[1]){
        numAnswer[5] = numAnswer[1] + numAnswer[2] + numAnswer[3];
        }
        //subtraction answer
        if(numberCorrect < answerChangeFlag[1]) {
        numAnswer[6] = numAnswer[1] - numAnswer[2];
        }
        if(numberCorrect >= answerChangeFlag[1]) {
        numAnswer[6] = numAnswer[1] - numAnswer[2] - numAnswer[3];
            }
        //multiplication answer
        //division answer

        return numAnswer;
      
 }

public void textViewClick(View view) {
//handles textview click and changes to negative
	int numCheck = 0;
	
	
	
	TextView userInputText = (TextView) findViewById(R.id.userAnswer);
	String currentInput = userInputText.getText().toString();
	
	
	
	
	numCheck = Integer.parseInt(currentInput.toString());
	numCheck = numCheck * -1;
	currentInput = "" + numCheck;
	userInputText.setText(currentInput);
	value = currentInput;
	
	
	checkCorrect();
	
}
	
public void checkCorrect() {
	int numCheck = 0;
	if(!isFirst) {
		
		numCheck = Integer.parseInt(value.toString());
		
		
		if(MainActivity.isAddition && answerSet[5] == numCheck || MainActivity.isSubtraction && answerSet[6] == numCheck) {
			isCorrect = true;
			
			Toast correctToast = Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_SHORT);
			correctToast.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);
			correctToast.show();
			numberCorrect += 1;
			run();
			//initialize number correct
			TextView numberCorrectView = (TextView) findViewById(R.id.numberCorrect);
			numberCorrectView.setText("Correct:" + numberCorrect);
			TextView userInputText = (TextView) findViewById(R.id.userAnswer);
			userInputText.setText("");
			timerTime += 1;
	
			} else {
				if(MainActivity.isAddition){
					Toast correctToast = Toast.makeText(getApplicationContext(), "correct answer is = " + answerSet[5], Toast.LENGTH_SHORT);
					correctToast.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);
					correctToast.show();
				}
				if(MainActivity.isSubtraction){
					Toast correctToast = Toast.makeText(getApplicationContext(), "correct answer is = " + answerSet[6], Toast.LENGTH_SHORT);
					correctToast.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);
					correctToast.show();
				}
			}
		}
	
	
	return;
	}
	
public void sync() {
	
	
	
	
	final Handler timeHandler = new Handler();
	final Runnable timer = new Runnable() {
		@Override
		public void run() {
			if(timerTime >= 0)  {
				try{
				timerTime = timerTime - 1;
				timerTimeString = "" + timerTime;
				TextView timerView = (TextView) findViewById(R.id.timerDisplay);
				timerView.setText(timerTimeString);
	//			timeHandler.postDelayed(this, 1000);
				}
				catch(Exception e) {
					
				}
				finally{
					timeHandler.postDelayed(this, 1000);
					
				}
			}
			if(timerTime < 0) {
				gameOver();
				
			}
			
		}
	};
	
	if(timerTime > 0) {
		timeHandler.postDelayed(timer, 1000);
	}
	if(timerTime < 0) {
		timeHandler.removeCallbacksAndMessages(null);
	}
	
}

public void gameOver(){
	Intent gameOver = new Intent(this, GameOverScreen.class);
	startActivity(gameOver);
	
}


}


