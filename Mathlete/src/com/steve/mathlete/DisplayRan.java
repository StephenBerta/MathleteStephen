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
	static int timerTime = 15;
	int answerSet[] = null;
	static int numberCorrect = 0;
	//increase the difficulty [0] is a place holder, [1] handles the third number, [2] handles keypad number delete
	static int answerChangeFlag[] = {0, 5, 10};
	
	private boolean isFirst = true;
	private boolean isCorrect = false;
	private boolean isPaused = false;
	
	static String timerTimeString = null;
	
	CharSequence firstText = "0";
	CharSequence value = null; 
	
	private boolean isDisplayOperation = true;
	private boolean userInput = false;
	private int slideTimer = 5;				//time each slide appears in number one
	
	private int slideCounter = 0;
	private int checkpointThreshold = 6;
	private int rollingAnswer = 1;
	private int numberOfAnswerDigits = 0;
	private int checkpointNumber = 0;
	private boolean keypadHide = false;
	
	
	@SuppressLint("NewApi")
	@Override
	//create and initialize the main screen
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//initialize display
		setContentView(R.layout.activity_display_ran);
		
		//initialize the variables
		resetVariables();

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
             // Show the Up button in the action bar.
//             getActionBar().setDisplayHomeAsUpEnabled(true);
         }  
	}
	

	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_ran, menu);
		return true;
	}
	
	
	//when the app comes into the foreground
	protected void onStart() {
		super.onStart();
		resetVariables();
		if(!MainActivity.isNumberOne) {
			runTradMath();
			sync();
		}
		
		if(MainActivity.isNumberOne) {
			runNumberOneGame();
		}
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
	
	
	//initialize the keypad first and reinitialize on the answer flag (for difficulty)
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
	
	//initialize variables and manipulate for this game instance
	public void resetVariables() {
		//reset trad variables
		if(!MainActivity.isNumberOne) {
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
			//initialize the timer time
			TextView timerView = (TextView) findViewById(R.id.timerDisplay);
			timerView.setText(timerTimeString);
		}
		
		//reset numberone variables
		if(MainActivity.isNumberOne) {
			isDisplayOperation = true;
			userInput = false;
			slideTimer = 3;
			slideCounter = 0;
			checkpointThreshold = 5;
			checkpointNumber = 0;
		}
		
		isPaused = false;
				
	}
				

	//parent function that runs each time
	public void runTradMath() {
		if(numberCorrect == 0 || numberCorrect == answerChangeFlag[2]) {
			startKeypad();
		}
		
		String operator = "0";
		answerSet = ranSelect();
		
		while(MainActivity.isDivision && answerSet[1] % answerSet[2] != 0) {
			answerSet = ranSelect();
		}
		
//		if(numberCorrect >= answerChangeFlag[1]){
//			int checkForModulus = (answerSet[1] % answerSet[2]);
//			
//			while(MainActivity.isDivision && (answerSet[1] % answerSet[2]) % answerSet[3] != 0) {
//				answerSet = ranSelect();
//			}
//		}
		String number = "0";

		if(MainActivity.isAddition) {
			operator = "+";
		}
		if(MainActivity.isSubtraction) {
			operator = "-";
		}
		if(MainActivity.isMultiplication) {
			operator = "×";
		}
		
		if(MainActivity.isDivision) {
			operator = "÷";
		}
		
//		if(MainActivity.isSubtraction && answerSet[2] > answerSet[1]) {
//			while(answerSet[2] > answerSet[1]) {
//				answerSet = ranSelect();
//			}
//			
//		}
		
		if(numberCorrect < answerChangeFlag[1] || MainActivity.isDivision) {
			number = answerSet[1] + " " + operator + " " + answerSet[2];
		}
		if(numberCorrect >= answerChangeFlag[1] && !MainActivity.isDivision) {
			number = answerSet[1] + " " + operator + " " + answerSet[2] + " " + operator + " " + answerSet[3];
		}
		
		
		final TextView displayNumbers = (TextView) findViewById(R.id.displayHere);
        displayNumbers.setText(number);
   	
		return;
		
	}
	
	//handle keypad inputs (does not include the click event for negatives)
	public void InputHandling(KeypadValue keypadValue) {

		
		String text = keypadValue.getText().toString();
		TextView userInputText = (TextView) findViewById(R.id.userAnswer);
		String currentInput = userInputText.getText().toString();
		
		int inputLength = currentInput.length();

		
		switch(keypadValue) {
		case BCK:
			int endIndex = inputLength - 1;
			numberOfAnswerDigits -= 1;
			if(endIndex < 1) {
				userInputText.setText("0");
			} else {
				userInputText.setText(currentInput.subSequence(0, endIndex));
			}
			
			break;
			
		case CLR:
			userInputText.setText("");
			numberOfAnswerDigits = 0;
			break;
			
		default:
				if(Character.isDigit(text.charAt(0))) {
					if(currentInput.equals("0")) {
						userInputText.setText(text);
						value = userInputText.getText();
						isFirst = false;
						numberOfAnswerDigits += 1;
						checkCorrect();
					} else {
						userInputText.append(text);
						value = userInputText.getText();
						isFirst = false;
						numberOfAnswerDigits += 1;
						checkCorrect();
												
					}
				}
				break;
		}
		
		return;
	}

//select our random numbers and find the answer to the questions
public int[] ranSelect() {

    	Random rand = new Random();
    	
    	
        Integer min1 = 0;
        Integer max1 = 10;
        Integer min2 = 1;
        Integer max2 = 12;
        Integer min3 = 0;
        Integer max3 = 15;
        
        
        	//set max/min values for multiplication
	        if(MainActivity.isMultiplication) {
	        	min1 = 0;
	            max1 = 10;
	            min2 = 1;
	            max2 = 8;
	            min3 = 0;
	            max3 = 5;
	        }
	    	
	        //set max/min values for division
	        if(MainActivity.isDivision) {
	        	min1 = 1;
	            max1 = 30;
	            min2 = 1;
	            max2 = 15;
	            min3 = 1;
	            max3 = 3;
	        }
	    	
	    	
	        int numAnswer[] = new int[11];
	        
	     
	        
	        numAnswer[1] = rand.nextInt((max1 - min1) + 1) + min1;
	        numAnswer[2] = rand.nextInt((max2 - min2) + 1) + min2;
	        numAnswer[3] = rand.nextInt((max3 - min3) + 1) + min3;
	        
	        
	        //addition answer
	        if(MainActivity.isAddition) {
		        if(numberCorrect < answerChangeFlag[1]){
		        numAnswer[10] = numAnswer[1] + numAnswer[2];
		        }
		        if(numberCorrect >= answerChangeFlag[1]){
		        numAnswer[10] = numAnswer[1] + numAnswer[2] + numAnswer[3];
		        }
	        }
	        
	        //subtraction answer
	        if(MainActivity.isSubtraction) {
		        if(numberCorrect < answerChangeFlag[1]) {
		        numAnswer[10] = numAnswer[1] - numAnswer[2];
		        }
		        if(numberCorrect >= answerChangeFlag[1]) {
		        numAnswer[10] = numAnswer[1] - numAnswer[2] - numAnswer[3];
		        }
	        }
	        //multiplication answer
	        if(MainActivity.isMultiplication) {
		        if(numberCorrect < answerChangeFlag[1]) {
		        numAnswer[10] = numAnswer[1] * numAnswer[2];
		        }
		        if(numberCorrect >= answerChangeFlag[1]) {
		        numAnswer[10] = numAnswer[1] * numAnswer[2] * numAnswer[3];
		        }
	        }
	        //division answer
	        if(MainActivity.isDivision) {	
				if(numberCorrect < answerChangeFlag[1]) {
					numAnswer[10] = numAnswer[1] / numAnswer[2];     	
				}	
			    if(numberCorrect >= answerChangeFlag[1]) {
			    	numAnswer[10] = numAnswer[1] / numAnswer[2];
			    }
	        }
        
        
	        
        
        return numAnswer;
      
 }

//handles textview click and changes to negative
public void textViewClick(View view) {
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
		
		if (!MainActivity.isNumberOne) {
			if(answerSet[10] == numCheck) {
				//do i need this?? below
				isCorrect = true;
				
				Toast correctToast = Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_SHORT);
				correctToast.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);
				correctToast.show();
				numberCorrect += 1;
				//run main again
				runTradMath();
				//change number correct
				TextView numberCorrectView = (TextView) findViewById(R.id.numberCorrect);
				numberCorrectView.setText("Correct:" + numberCorrect);
				TextView userInputText = (TextView) findViewById(R.id.userAnswer);
				userInputText.setText("");
				timerTime += 1;
		
				} else {
					if(MainActivity.isDebug) {
					Toast correctToast = Toast.makeText(getApplicationContext(), "correct answer is = " + answerSet[10], Toast.LENGTH_SHORT);
					correctToast.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);
					correctToast.show();
				}
			}
		}
		
		if(MainActivity.isNumberOne) {
			if(rollingAnswer == numCheck) {
			slideCounter = 0;
			keypadHide = true;
			GridView keypadGridHide;
			keypadGridHide = (GridView) findViewById(R.id.grdButtons);
			keypadGridHide.setVisibility(View.INVISIBLE);
			runNumberOneGame();
			
			} else {
				if(MainActivity.isDebug) {
					Toast correctToast = Toast.makeText(getApplicationContext(), "correct answer is = " + rollingAnswer, Toast.LENGTH_SHORT);
					correctToast.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);
					correctToast.show();
				}
			}
			
		}
	
	}
	
	return;
	}
	
//runs the handlers
public void sync() {
	
	final Handler timeHandler = new Handler();
	final Runnable timer = new Runnable() {
		@Override
		public void run() {
			if(timerTime > 0 && !isPaused)  {
				try{
				timerTime = timerTime - 1;
				timerTimeString = "" + timerTime;
				TextView timerView = (TextView) findViewById(R.id.timerDisplay);
				timerView.setText(timerTimeString);
//				timeHandler.postDelayed(this, 1000);
				}
				catch(Exception e) {
					
				}
				finally{
					if(timerTime > 0) {
						timeHandler.postDelayed(this, 1000);
					}
				}
			}
			if(timerTime == 0 && !isPaused) {
				gameOver();
				
			}
			
		}
	};
	
	if(timerTime > 1) {
		timeHandler.postDelayed(timer, 1000);
	}
	if(timerTime == 0) {
		timeHandler.removeCallbacksAndMessages(null);
	}
	
}

//change screens
public void gameOver(){
	if(!isPaused) {
	Intent gameOver = new Intent(this, GameOverScreen.class);
	startActivity(gameOver);
	}
}

//main method to run the number one style game
public void runNumberOneGame() {
	
	int slideTimerReset = 3;
	
	if(isFirst) {
	TextView displayNumber = (TextView) findViewById(R.id.displayHere);
	displayNumber.setText("" + rollingAnswer);
	}
	
    if(slideCounter < checkpointThreshold) {
    	slideTimer = slideTimerReset;
    	numberOneTimer();	
    	
    }
    if(slideCounter == checkpointThreshold) {
    	keypadHide = false;
    	checkpointNumber +=1;
    	if(checkpointNumber > 1) {
	    	GridView keypadGridHide;
			keypadGridHide = (GridView) findViewById(R.id.grdButtons);
			keypadGridHide.setVisibility(View.VISIBLE);
    	}
    	if(checkpointNumber == 1) {
    		startKeypad();
    	}
    }
    if(!isFirst) {
		screenOutput();
	}
}

public void screenOutput() {
	int numberOneValues[] = null;
	String operator = null;
	TextView userInputText = (TextView) findViewById(R.id.userAnswer);
	TextView displayNumber = (TextView) findViewById(R.id.displayHere);
	TextView numberCorrectView = (TextView) findViewById(R.id.numberCorrect);
	
	userInputText.setText("");
	numberCorrectView.setText("");
	if(slideCounter != checkpointThreshold) {
		numberOneValues = numberOperationSelection();
		
		if(numberOneValues[1] == 1) {
			operator = "+";
			rollingAnswer += numberOneValues[0];
		}
		if(numberOneValues[1] == 2) {
			operator = "-";
			rollingAnswer -= numberOneValues[0];
		}
		if(numberOneValues[1] == 3) {
			operator = "÷";
			rollingAnswer = rollingAnswer / numberOneValues[0];
		}
		if(numberOneValues[1] == 4) {
			operator = "×";
			rollingAnswer = rollingAnswer * numberOneValues[0];
		}
		displayNumber.setText(operator + "" + numberOneValues[0]);
	}
	if(slideCounter == checkpointThreshold) {
		
		
		displayNumber.setText("Answer?");
		userInputText.setText("0");
		numberCorrectView.setText("Checkpoint " + checkpointNumber);
	
	}
}

public void numberOneTimer() {
	
	final Handler timeHandler = new Handler();
	final Runnable timer = new Runnable() {
		@Override
		public void run() {
			if(slideTimer > 0 && !isPaused && slideCounter < checkpointThreshold)  {
				try{
				isFirst = false;
				slideTimer -= 1;
//				timeHandler.postDelayed(this, 1000);
				
				
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
				slideCounter += 1;
				
//				Toast slideCount = Toast.makeText(getApplicationContext(), slideCounter, Toast.LENGTH_SHORT);
//				slideCount.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);
//				slideCount.show();
				
				if(slideCounter == checkpointThreshold) {
					userInput = true;
					timeHandler.removeCallbacksAndMessages(null);
				}
				runNumberOneGame();
				
				
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

public int[] numberOperationSelection() {
	Random rand = new Random();
	int numOneAnswer[] = new int[11];
	
	Integer min1 = 0;
    Integer max1 = 0;
    
    //select operation
    //1 = addition
    //2 - subtraction
    //3 - division
    //4 - multiplication
    Integer minOperator = 1;
    Integer maxOperator = 4;
    numOneAnswer[1] = rand.nextInt((maxOperator - minOperator) + 1) + minOperator;
    
    //division
  	if(numOneAnswer[1] == 3) {
  		min1 = 2;
  	    max1 = 10;
  	    for(int i=min1;i<max1;i++){
  			if(rollingAnswer / i == 0) {
  				numOneAnswer[0]=i;
  			}		
  		}
  	    if(numOneAnswer[0] == 0) {
  	  		numOneAnswer[1] = 1;
  	  	}
  	}
  	
	//addition
	if(numOneAnswer[1] == 1) {
		min1 = 2;
	    max1 = 10;
	}
	//subtraction
	if(numOneAnswer[1] == 2) {
		min1 = 2;
	    max1 = 10;	
	}
	
	//multi
	if(numOneAnswer[1] == 4) {
		min1 = 2;
	    max1 = 10;
	}
	
    if(numOneAnswer[1] == 1  || numOneAnswer[1] == 2 || numOneAnswer[1] == 4) {
    	numOneAnswer[0] = rand.nextInt((max1 - min1) + 1) + min1;
    }
 
  	
    if(MainActivity.isDebug) {
		Toast correctToast = Toast.makeText(getApplicationContext(), "rollingAnswer=   " +  rollingAnswer + "   " + numOneAnswer[0], Toast.LENGTH_SHORT);
		correctToast.setGravity(Gravity.CENTER_HORIZONTAL, 0 ,0);
		correctToast.show();
	}
    return numOneAnswer;   
}

}


