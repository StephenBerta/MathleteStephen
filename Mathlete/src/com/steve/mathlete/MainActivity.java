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

	public final static String NUMBER_OUT = "com.steve.mathlete.NUMBER";
	//public final static String number = "com.steve.mathlete.number";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
    
    
    
    public void ranSelect(View view) {
	 	
	 	
    	Random rand = new Random();
        Integer min = 0;
        Integer max = 10;
        int randomNum1 = 0;
        int randomNum2 = 0;
     

        randomNum1 = rand.nextInt((max - min) + 1) + min;
        randomNum2 = rand.nextInt((max - min) + 1) + min;
        int numAnswer = randomNum1 + randomNum2;
        
        
        Intent intent = new Intent(this, DisplayRan.class);
        String number = "" + randomNum1 + "+" + randomNum2;
        intent.putExtra(NUMBER_OUT, number);
        startActivity(intent);
        
        
//        if(displayNow)
                
                
               
                
                         
        
 }
      
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
