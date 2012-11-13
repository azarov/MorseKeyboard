package ru.spbau.morsekeyboard;

import ru.spbau.morsekeyboard.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

public class MyActivity extends FragmentActivity {
	
	
	private EditText mEditText;
	private String mText = "";
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(MorseKeyBoardFragment.READY_ACTION)){
				String symbol = intent.getExtras().getString("key");
				if (symbol != null){
					mText+=symbol;
					mEditText.setText(mText);
				}
			}
		}
	};
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mEditText = (EditText) findViewById(R.id.editText);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	IntentFilter f = new IntentFilter();
		f.addAction(MorseKeyBoardFragment.READY_ACTION);
		registerReceiver(mReceiver, f);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	unregisterReceiver(mReceiver);
    }
}
