package com.steve.mathlete;


import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class KeypadAdapter extends BaseAdapter {
	public static String buttonTextColor;
	private Context mContext;
//	private Context viewContext;
	private OnClickListener mOnButtonClick;
	
	  public KeypadAdapter(Context c) {
	    mContext = c;
	  }

	  public void setOnButtonClickListener(OnClickListener listener) {
			mOnButtonClick = listener;
		}
	  
	  public int getCount() {
	    return mButtons.length;
	  }

	  public Object getItem(int position) {
	    return mButtons[position];
	  }

	  public long getItemId(int position) {
	    return 0;
	  }

	// create a new ButtonView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
	  Button btn;
	  if (convertView == null) { // if it's not recycled, initialize some attributes
	    btn = new Button(mContext);
	    btn.setTextSize(25);
	    KeypadValue keypadButton = mButtons[position];
		btn.setOnClickListener(mOnButtonClick);
		if(DisplayRan.numberCorrect < DisplayRan.answerChangeFlag[1]){
//			btn.setTextColor(viewContext.getResources().getColor(R.color.Black));
			
			
			btn.setBackgroundResource(R.drawable.keypadstyle1);
		}
		if(DisplayRan.numberCorrect >= DisplayRan.answerChangeFlag[1]){
			btn.setTextColor(17170443);
			btn.setBackgroundResource(R.drawable.keypadstyle2);
		}
	    // Set CalculatorButton enumeration as tag of the button so that we
	    // will use this information from our main view to identify what to do
	    btn.setTag(keypadButton);
	  } 
	  else {
	    btn = (Button) convertView;
	  }

	  btn.setText(mButtons[position].getText());
	  return btn;
	}

	// Create and populate keypad buttons array with CalculatorButton values
	private KeypadValue[] mButtons = { KeypadValue.ONE, KeypadValue.TWO, KeypadValue.THREE, 
									   KeypadValue.FOUR, KeypadValue.FIVE, KeypadValue.SIX, 
									   KeypadValue.SEVEN, KeypadValue.EIGHT, KeypadValue.NINE,
									   KeypadValue.CLR, KeypadValue.ZERO, KeypadValue.BCK};
	
}
