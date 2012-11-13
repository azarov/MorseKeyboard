package ru.spbau.morsekeyboard;

import ru.spbau.morsekeyboard.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class MorseKeyBoardFragment extends Fragment {

	public static final String READY_ACTION = "MorseKeyboard.READY_ACTION";
	private static final long SHOW_LIMIT = 500;
	
	private EditText mSymbol;
	private EditText mMorse;
	private String mCode = "";
	private Handler mHandler;
	private MorseTranslator mTranslator;
	
	private Runnable mShowSymbolRunnable = new Runnable() {
		
		public void run() {
			Intent intent = new Intent(READY_ACTION);
			String symbol = "";
			try {
				symbol = mTranslator.getSymbol(mCode);
				intent.putExtra("key", symbol);
			} catch (BadCodeException e) {
				
			}
			mCode = "";
			mSymbol.setText("");
			mMorse.setText(mCode);
			getActivity().sendBroadcast(intent);
			mCode = "";
		}
	};
	
	OnClickListener mClick = new OnClickListener() {
		
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.dot:
				updateSymbol(".");
				break;
			case R.id.dashe:
				updateSymbol("-");
				break;
			case R.id.space:
				//TODO
				break;
			case R.id.clear:
				mCode = "";
				mMorse.setText("");
				mSymbol.setText("");
				break;
			case R.id.settings:
				Intent intent = new Intent(getActivity(),AboutActivity.class);
				getActivity().startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
	
	
	
	public MorseKeyBoardFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.keyboard, container,false); 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHandler = new Handler();
		mMorse = (EditText) getView().findViewById(R.id.morseState);
		mSymbol = (EditText) getView().findViewById(R.id.symbolState);
		mTranslator = new MorseTranslator(getActivity());
		getView().findViewById(R.id.clear).setOnClickListener(mClick);
		getView().findViewById(R.id.dot).setOnClickListener(mClick);
		getView().findViewById(R.id.dashe).setOnClickListener(mClick);
		getView().findViewById(R.id.settings).setOnClickListener(mClick);
		
	}
	
	private void updateSymbol(String sym){
		String symbol = "";
		mHandler.removeCallbacks(mShowSymbolRunnable);
		mCode+=sym;
		mMorse.setText(mCode);
		try {
			symbol = mTranslator.getSymbol(mCode);
			mSymbol.setText(symbol);
			mHandler.postDelayed(mShowSymbolRunnable, SHOW_LIMIT);
		} catch (BadCodeException e) {
			mCode = "";
			mSymbol.setText("");
			e.printStackTrace();
			mMorse.setText(mCode);
		}
		
		
	}
	
}
