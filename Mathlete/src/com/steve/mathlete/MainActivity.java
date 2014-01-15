package com.steve.mathlete;

import java.util.Random;

import com.steve.mathlete.R.color;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	static boolean isAddition = false;
	static boolean isSubtraction = false;
	
	public final static String NUMBER_OUT = "com.steve.mathlete.NUMBER";
	//public final static String number = "com.steve.mathlete.number";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
    
    
    public void runAddition(View view){
    	Intent startAddition = new Intent(this, DisplayRan.class);
    	startActivity(startAddition);
    	isAddition = true;
    	isSubtraction = false;
    }
    
    public void runSubtraction(View view){
    	Intent startSubtraction = new Intent(this, DisplayRan.class);
    	startActivity(startSubtraction);
    	isAddition = false;
    	isSubtraction = true;
    }
    
         
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
