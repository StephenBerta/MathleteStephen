package com.steve.mathlete;





import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

	static boolean isAddition = false;
	static boolean isSubtraction = false;
	static boolean isMultiplication = false;
	static boolean isDivision = false;
	static boolean isNumberOne = false;
	static boolean isDebug = false;
	

	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        
        
    }
    
    
    public void runAddition(View view){
    	Intent startAddition = new Intent(this, DisplayRan.class);
    	startActivity(startAddition);
    	isAddition = true;
    	isSubtraction = false;
    	isMultiplication = false;
    	isDivision = false;
    	isNumberOne = false;
    }
    
    public void runSubtraction(View view){
    	Intent startSubtraction = new Intent(this, DisplayRan.class);
    	startActivity(startSubtraction);
    	isAddition = false;
    	isSubtraction = true;
    	isMultiplication = false;
    	isDivision = false;
    	isNumberOne = false;
    }
    
    public void runMultiplication(View view){
    	Intent startMultiplication = new Intent(this, DisplayRan.class);
    	startActivity(startMultiplication);
    	isAddition = false;
    	isSubtraction = false;
    	isMultiplication = true;
    	isDivision = false;
    	isNumberOne = false;
    }
    
    public void runDivision(View view){
    	Intent startDivision = new Intent(this, DisplayRan.class);
    	startActivity(startDivision);
    	isAddition = false;
    	isSubtraction = false;
    	isMultiplication = false;
    	isDivision = true;
    	isNumberOne = false;
    }
    
    public void runNumberOne(View view){
    	Intent startNumberOne = new Intent(this, DisplayRan.class);
    	startActivity(startNumberOne);
    	isAddition = false;
    	isSubtraction = false;
    	isMultiplication = false;
    	isDivision = false;
    	isNumberOne = true;
    }
         
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuSettings:
                
                return true;
                
            case R.id.actionSettings:
                
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}
