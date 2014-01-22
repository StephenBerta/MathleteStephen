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
		
		GridView mKeypadGrid;
		KeypadAdapter mKeypadAdapter;
		
		super.onCreate(savedInstanceState);
		
		//get intent
		//Intent intent = getIntent();
				
		
		
		
		setContentView(R.layout.activity_display_ran);
		//initialize a zero for user input
		TextView userInputText = (TextView) findViewById(R.id.userAnswer);
		userInputText.setText(firstText);
		//initialize number correct
		TextView numberCorrectView = (TextView) findViewById(R.id.numberCorrect);
		numberCorrectView.setText("Correct:" + "0");
		
		
		
		run();
		sync();
//		final String numberToPass = number;
		
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
	
	public void run() {
		
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
        Integer min = 0;
        Integer max = 10;
        
        int numAnswer[] = new int[10];
        
     
        
        numAnswer[1] = rand.nextInt((max - min) + 1) + min;
        numAnswer[2] = rand.nextInt((max - min) + 1) + min;
        numAnswer[3] = rand.nextInt((max - min) + 1) + min;
        
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

public void textViewClick() {
	//handles textview click and changes to negative
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
				Toast correctToast = Toast.makeText(getApplicationContext(), "correct answer is = " + answerSet[5], Toast.LENGTH_SHORT);
				correctToast.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);
				correctToast.show();
				
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
	
	if(timerTime >= 0) {
	timeHandler.postDelayed(timer, 1000);
	}
	
}

public void gameOver(){
	Intent gameOver = new Intent(this, GameOverScreen.class);
	startActivity(gameOver);
	
}


}


