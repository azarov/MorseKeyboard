package ru.spbau.morsekeyboard.keyboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import ru.spbau.morsekeyboard.BadCodeException;
import ru.spbau.morsekeyboard.MorseTranslator;
import ru.spbau.morsekeyboard.R;

public class DotDashIMEService extends InputMethodService implements
		KeyboardView.OnKeyboardActionListener{
	
	private String TAG = "DotDashIMEService";
	private DotDashKeyboardView inputView;
	
	private DotDashKeyboard dotDashKeyboard;
	private DotDashKeyboard oneButtonKeyboard;
	
	private Keyboard.Key spaceKey;
	private Keyboard.Key capsLockKey;
	
	private MorseTranslator mMorseTranslator;
	
	private StringBuilder charInProgress;
	private boolean keyboardState = true;

	private static final int CAPS_LOCK_OFF = 0;
	private static final int CAPS_LOCK_NEXT = 1;
	private static final int CAPS_LOCK_ALL = 2;
	private Integer capsLockState = CAPS_LOCK_OFF;

	private static final int AUTO_CAP_MIDSENTENCE = 0;
	private static final int AUTO_CAP_SENTENCE_ENDED = 1;
	private Integer autoCapState = AUTO_CAP_MIDSENTENCE;

	@Override
	public void onInitializeInterface() {
		super.onInitializeInterface();
		dotDashKeyboard = new DotDashKeyboard(this, R.xml.dotdash);
		oneButtonKeyboard = new DotDashKeyboard(this, R.xml.onebutton);
		spaceKey = dotDashKeyboard.getSpaceKey();
		capsLockKey = dotDashKeyboard.getCapsLockKey();
		mMorseTranslator = new MorseTranslator(getApplicationContext());
		charInProgress = new StringBuilder(/*maxCodeLength*/);
	}

	@Override
	public View onCreateInputView() {
		inputView = (DotDashKeyboardView) getLayoutInflater().inflate(
				R.layout.input, null);
		inputView.setOnKeyboardActionListener(this);
		inputView.setKeyboard(dotDashKeyboard);
		inputView.setService(this);
		return inputView;
	}

	public void onKey(int primaryCode, int[] keyCodes) {
		onKeyMorse(primaryCode, keyCodes);
	}

	/**
	 * Handle key input on the Morse Code keyboard. It has 5 keys and each of
	 * them does something different.
	 * 
	 * @param primaryCode
	 * @param keyCodes
	 */
	public void onKeyMorse(int primaryCode, int[] keyCodes) {
		Log.d(TAG, "primaryCode: " + charInProgress.toString());
		String curCharMatch = null;
		try {
			curCharMatch = mMorseTranslator.getSymbol(charInProgress.toString());
		} catch (BadCodeException e) {
			e.printStackTrace();
		}
		switch (primaryCode) {

		case 0:
		case 1:
			if (charInProgress.length() < mMorseTranslator.getMax()) {
				charInProgress.append(primaryCode == 1 ? "-" : ".");
			}
			
			break;
		case 2:
			if (charInProgress.length() < mMorseTranslator.getMax()) {
				charInProgress.append(inputView.getCode());
			}
			Log.d(TAG, "charInProgress: " + charInProgress.toString());
			break;
		// Space button ends the current dotdash sequence
		// Space twice in a row sends through a standard space character
		case KeyEvent.KEYCODE_SPACE:
			if (charInProgress.length() == 0) {
				getCurrentInputConnection().commitText(" ", 1);
				if (autoCapState == AUTO_CAP_SENTENCE_ENDED){
					capsLockState = CAPS_LOCK_NEXT;
					updateCapsLockKey();
				}
			} else {
				if (curCharMatch != null) {
					if (curCharMatch.contentEquals("\n")) {
						sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
					} else if (curCharMatch.contentEquals("END")) {
						requestHideSelf(0);
						inputView.closing();
					} else {
						boolean uppercase = false;
						if (capsLockState == CAPS_LOCK_NEXT) {
							uppercase = true;
							capsLockState = CAPS_LOCK_OFF;
							updateCapsLockKey();
						} else if (capsLockState == CAPS_LOCK_ALL) {
							uppercase = true;
						}
						if (uppercase) {
							curCharMatch = curCharMatch.toUpperCase();
						}
						getCurrentInputConnection().commitText(curCharMatch,
								curCharMatch.length());
					}
				}
			}
			clearCharInProgress();
			break;
		// If there's a character in progress, clear it
		// otherwise, send through a backspace keypress
		case KeyEvent.KEYCODE_DEL:
			if (charInProgress.length() > 0) {
				clearCharInProgress();
			} else {
				sendDownUpKeyEvents(primaryCode);
				clearCharInProgress();
				updateSpaceKey(true);

				if (capsLockState == CAPS_LOCK_NEXT) {
					// If you've hit delete and you were in caps_next state,
					// then caps_off
					capsLockState = CAPS_LOCK_OFF;
					updateCapsLockKey();
				}
			}
			break;
		case KeyEvent.KEYCODE_SHIFT_LEFT:
			switch (capsLockState) {
			case CAPS_LOCK_OFF:
				capsLockState = CAPS_LOCK_NEXT;
				break;
			case CAPS_LOCK_NEXT:
				capsLockState = CAPS_LOCK_ALL;
				break;
			default:
				capsLockState = CAPS_LOCK_OFF;
			}
			updateCapsLockKey();
			break;
		}

		updateSpaceKey(true);
	}

	private void clearCharInProgress() {
		charInProgress.setLength(0);
	}

	public void clearEverything() {
		clearCharInProgress();
		capsLockState = CAPS_LOCK_OFF;
		updateCapsLockKey();
		updateSpaceKey(false);
	}

	public void updateCapsLockKey() {
		Context context = this.getApplicationContext();
		switch (capsLockState) {
		case CAPS_LOCK_OFF:
			capsLockKey.on = false;
			capsLockKey.label = context.getText(R.string.caps_lock_off);
			break;
		case CAPS_LOCK_NEXT:
			capsLockKey.on = false;
			capsLockKey.label = context.getText(R.string.caps_lock_next);
			break;
		case CAPS_LOCK_ALL:
			capsLockKey.on = true;
			capsLockKey.label = context.getText(R.string.caps_lock_all);
			break;
		}
		try {
			inputView.invalidateAllKeys();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		}
	}

	public void updateSpaceKey(boolean refreshScreen) {
		if (!spaceKey.label.toString().equals(charInProgress.toString())) {
			spaceKey.label = charInProgress.toString();
			if (refreshScreen) {
				try {
					inputView.invalidateAllKeys();
				} catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
			}
		}
	}

	public void onStartInputView(EditorInfo info,
			boolean restarting) {
		super.onStartInputView(info, restarting);
		try {
			inputView.invalidateAllKeys();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		}
		updateAutoCap();
		updateCapsLockKey();
	};

	@Override
	public void onFinishInputView(boolean finishingInput) {
		this.inputView.closeAboutDialog();
		super.onFinishInputView(finishingInput);
		clearEverything();
	}


	/**
	 * The cursor position (selection position) has changed
	 */
	@Override
	public void onUpdateSelection(int oldSelStart, int oldSelEnd,
			int newSelStart, int newSelEnd, int candidatesStart,
			int candidatesEnd) {
		super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd,
				candidatesStart, candidatesEnd);
		updateAutoCap();
	}

	/**
	 * Update the shift state if autocap is turned on, based on current cursor
	 * position (using InputConnection.getCursorCapsMode())
	 */
	public void updateAutoCap() {

		// Autocap has no effect if Caps Lock is on
		if (capsLockState == CAPS_LOCK_ALL) {
			return;
		}

		int origCapsLockState = capsLockState;
		int newCapsLockState = CAPS_LOCK_OFF;

		EditorInfo ei = getCurrentInputEditorInfo();
		if (ei != null
				&& ei.inputType != EditorInfo.TYPE_NULL
				&& getCurrentInputConnection().getCursorCapsMode(ei.inputType) > 0) {
			newCapsLockState = CAPS_LOCK_NEXT;
		}
		capsLockState = newCapsLockState;
		if (capsLockState != origCapsLockState) {
			updateCapsLockKey();
		}
	}
	
	/**
	 * swipe change one button keyboard to DotDasheKeyboard
	 */
	public void updateKeyboard(){
		clearCharInProgress();
		if (keyboardState){
			inputView.setKeyboard(oneButtonKeyboard);
			spaceKey = oneButtonKeyboard.getSpaceKey();
			capsLockKey = oneButtonKeyboard.getCapsLockKey();
		}else{
			inputView.setKeyboard(dotDashKeyboard);
			spaceKey = dotDashKeyboard.getSpaceKey();
			capsLockKey = dotDashKeyboard.getCapsLockKey();
			
		}
		keyboardState = !keyboardState;
		updateSpaceKey(true);
	}
	
	public void onPress(int arg0) {}
	public void onRelease(int arg0) {}
	public void onText(CharSequence arg0) {}
	public void swipeDown() {}
	public void swipeLeft() {}
	public void swipeRight() {}
	public void swipeUp() {}
}
