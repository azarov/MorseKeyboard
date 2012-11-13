package ru.spbau.morsekeyboard;

import android.view.MotionEvent;
import android.view.View;

class MorseButtonTouchListener implements View.OnTouchListener {
	private long startTime;
	private long endTime;
	private MorseDurationResolver morseDurationResolver;

	MorseButtonTouchListener() {
		startTime = 0;
		endTime = 0;
		morseDurationResolver = new MorseDurationResolver(300L);
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		int action = motionEvent.getAction();
		Character c = 0;

		switch (action)
		{
			case MotionEvent.ACTION_DOWN:
				startTime = System.currentTimeMillis();
				endTime = startTime;
				break;
			case MotionEvent.ACTION_UP:
				endTime = System.currentTimeMillis();
				c = morseDurationResolver.getSymbolFromDuration(endTime - startTime); //TODO: change method
				//TODO: handle this
				break;
			default:
				c = morseDurationResolver.getSymbolFromDuration(endTime - startTime);
				//TODO: handle this
				break;
		}

		return true;
	}
}
