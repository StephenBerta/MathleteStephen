package com.steve.mathlete;

public enum KeypadValue {
	ZERO("0",KeypadValueCategory.Number)
	, ONE("1",KeypadValueCategory.Number)
	, TWO("2",KeypadValueCategory.Number)
	, THREE("3",KeypadValueCategory.Number)
	, FOUR("4",KeypadValueCategory.Number)
	, FIVE("5",KeypadValueCategory.Number)
	, SIX("6",KeypadValueCategory.Number)
	, SEVEN("7",KeypadValueCategory.Number)
	, EIGHT("8",KeypadValueCategory.Number)
	, NINE("9",KeypadValueCategory.Number)
	, CLR("CLEAR", KeypadValueCategory.Clear)
	, BCK("<--", KeypadValueCategory.Misc);
	
	 CharSequence mText; // Display Text
	 KeypadValueCategory mCategory;
	 
	 KeypadValue(CharSequence text,KeypadValueCategory category) {
		    mText = text;
		    mCategory = category;
		  }

		  public CharSequence getText() {
		    return mText;
		  }
}
