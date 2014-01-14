package com.steve.mathlete;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


public class DisplayRan extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		GridView mKeypadGrid;
		KeypadAdapter mKeypadAdapter;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_ran);
		
		Intent intent = getIntent();
		String number = intent.getStringExtra(MainActivity.NUMBER_OUT);
		final String numberToPass = number;
		
		mKeypadGrid = (GridView) findViewById(R.id.grdButtons);
		mKeypadAdapter = new KeypadAdapter(this);
		mKeypadGrid.setAdapter(mKeypadAdapter);
		
		mKeypadAdapter.setOnButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button btn = (Button) v;
				KeypadValue keypadValue = (KeypadValue) btn.getTag();
				InputHandling(keypadValue, numberToPass);
			}
		});
		
		mKeypadGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {	}
		});
		
		
			
		
		final TextView displayNumbers = (TextView) findViewById(R.id.displayHere);
        displayNumbers.setText(number);

       
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
	
	
	
	public void InputHandling(KeypadValue keypadValue, String numbers) {
//		double numberToCheck = Double.parseDouble(numbers);
		
		String text = keypadValue.getText().toString();
		TextView userInputText = (TextView) findViewById(R.id.userAnswer);
		String currentInput = userInputText.getText().toString();
		
		int inputLength = currentInput.length();
		String answerCorrect = null;
		
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
					} else {
						userInputText.append(text);
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
	
	
}
