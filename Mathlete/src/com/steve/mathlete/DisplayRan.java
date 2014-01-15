package com.steve.mathlete;


import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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
	int numberCorrect = 0;
	boolean isFirst = true;
	boolean isCorrect = false;
	//numbers to pass and get a return of the ranselect
	int answerSet[] = null;
	CharSequence firstText = "0";
	CharSequence value = null; 
	
	
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

	
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_ran, menu);
		return true;
	}
	
	public void run() {
		answerSet = ranSelect();
		String operator = "0";
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
		String number = answerSet[1] + operator + answerSet[2];
		
		
		
		final TextView displayNumbers = (TextView) findViewById(R.id.displayHere);
        displayNumbers.setText(number);
        
        
        
       
        	
        	
        	
		return;
		
	}
	
	
	public void InputHandling(KeypadValue keypadValue) {
//		double numberToCheck = Double.parseDouble(numbers);
		
		String text = keypadValue.getText().toString();
		TextView userInputText = (TextView) findViewById(R.id.userAnswer);
		String currentInput = userInputText.getText().toString();
		
//		int inputLength = currentInput.length();
//		String answerCorrect = null;
		
		switch(keypadValue) {
		case BCK:
			userInputText.setText("");
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

		
		
		
	
	
//	private void determineCorrect() {
//		int input = Int.parseInt(currentInput);
//		int answer = Int.parseInt(number);
//		
//		if(answer == input) {
//			MainActivity.ranSelect();
//		}
//	}
	
public int[] ranSelect() {
	 	
	 	
    	Random rand = new Random();
        Integer min = 0;
        Integer max = 10;
        int randomNum1 = 1;
        int randomNum2 = 0;
        int numAnswer[] = new int[10];
        
     
        
        randomNum1 = rand.nextInt((max - min) + 1) + min;
        randomNum2 = rand.nextInt((max - min) + 1) + min;
        numAnswer[1] = randomNum1;
        numAnswer[2] = randomNum2;
        //addition answer
        numAnswer[5] = randomNum1 + randomNum2;
        //subtraction answer
        numAnswer[6] = randomNum1 - randomNum2;
        //multiplication answer
        //division answer

        return numAnswer;
      
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
	
			}
		}
	return;
	}
	
}
