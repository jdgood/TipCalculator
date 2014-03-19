package com.codepath.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	private SeekBar sbTip;
	private TextView tvPercentage;
	private TextView tvTotal;
	private TextView tvTip;
	private Button btUp;
	private Button btDown;
	private Button btClear;
	private EditText etSubtotal;
	
	public int percentage = 15;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sbTip = (SeekBar) findViewById(R.id.sbTip);
		tvPercentage = (TextView) findViewById(R.id.tvPercentage);
		tvTip = (TextView) findViewById(R.id.tvTip);
		tvTotal = (TextView) findViewById(R.id.tvTotal);
		btUp = (Button) findViewById(R.id.btUp);
		btDown = (Button) findViewById(R.id.btDown);
		btClear = (Button) findViewById(R.id.btClear);
		etSubtotal = (EditText) findViewById(R.id.etSubtotal);
		
		
		registerListeners();
		
		tvPercentage.setText(percentage + "%");
		updateTotals();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void registerListeners() {
		setupDownButtonListener();
		setupUpButtonListener();
		setupClearButtonListener();
		setupSeekbarListener();
		setupSubtotalTextFieldListener();
	}
	
	private void setupDownButtonListener() {
		btDown.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if(percentage > 1) {
						percentage--;
					}
					sbTip.setProgress(percentage);
		        }
				return true;
		    }
		});
	}
	
	private void setupUpButtonListener() {
		btUp.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if(percentage < 100) {
						percentage++;
					}					
					sbTip.setProgress(percentage);
		        }
				return true;
		    }
		});
	}
	
	private void setupClearButtonListener() {
		btClear.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					etSubtotal.setText("");
					updateTotals();
		        }
				return true;
		    }
		});
	}

	
	/** set up listeners for when the seek bar moves */
	private void setupSeekbarListener() {
		sbTip.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// set text to match the progress
				percentage = progress;
				tvPercentage.setText(percentage + "%");
				updateTotals();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void setupSubtotalTextFieldListener() {
		etSubtotal.setFilters(new InputFilter[] { new MoneyFilter() });		
		etSubtotal.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateTotals();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void updateTotals() {
		double subtotal = 0;
		if(!etSubtotal.getText().toString().equals("") && !etSubtotal.getText().toString().equals(".")) {
			subtotal = Double.valueOf(etSubtotal.getText().toString());
		} 
		//update tip
		double tip = subtotal * (percentage * .01);
		tvTip.setText("Tip\t\t: $" + String.format("%.2f", tip));
		//update total
		double total = subtotal + tip;
		tvTotal.setText("Total\t: $" + String.format("%.2f", total));
	}
}
