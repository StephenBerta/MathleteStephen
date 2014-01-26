package com.steve.mathlete;





import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {

	static boolean isAddition = false;
	static boolean isSubtraction = false;
	static boolean isMultiplication = false;
	static boolean isDivision = false;
	

	
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
    	isMultiplication = false;
    	isDivision = false;
    }
    
    public void runSubtraction(View view){
    	Intent startSubtraction = new Intent(this, DisplayRan.class);
    	startActivity(startSubtraction);
    	isAddition = false;
    	isSubtraction = true;
    	isMultiplication = false;
    	isDivision = false;
    }
    
    public void runMultiplication(View view){
    	Intent startMultiplication = new Intent(this, DisplayRan.class);
    	startActivity(startMultiplication);
    	isAddition = false;
    	isSubtraction = false;
    	isMultiplication = true;
    	isDivision = false;
    }
    
    public void runDivision(View view){
    	Intent startDivision = new Intent(this, DisplayRan.class);
    	startActivity(startDivision);
    	isAddition = false;
    	isSubtraction = false;
    	isMultiplication = false;
    	isDivision = true;
    }
    
         
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
