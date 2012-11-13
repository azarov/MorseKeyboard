package ru.spbau.morsekeyboard;

import android.app.Dialog;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.*;
import android.widget.TextView;

public class DotDashKeyboardView extends KeyboardView implements View.OnTouchListener{

	private DotDashIMEService service;
	private Dialog cheatsheetDialog;
	private View cheatsheet1;
	private View cheatsheet2;
	private int mSwipeThreshold;

	private long startTime;
	private long endTime;
	private MorseDurationResolver morseDurationResolver;
	
	private char code;

	public static final int KBD_NONE = 0;
	public static final int KBD_DOTDASH = 1;
	public static final int KBD_UTILITY = 2;

	public boolean mEnableUtilityKeyboard = false;

	public void setService(DotDashIMEService service) {
		this.service = service;
	}

	public DotDashKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);

		startTime = 0;
		endTime = 0;
		morseDurationResolver = new MorseDurationResolver(300L);

		setEverythingUp();
	}

	public DotDashKeyboardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setEverythingUp();
	}

	private void setEverythingUp() {
		mSwipeThreshold = (int) (100 * getResources().getDisplayMetrics().density);
		setPreviewEnabled(false);
		final GestureDetector gestureDetector = new GestureDetector(
				new GestureDetector.SimpleOnGestureListener() {

					/**
					 * This function mostly copied from LatinKeyboardBaseView in
					 * the Hacker's Keyboard project
					 * http://code.google.com/p/hackerskeyboard/
					 */
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {

						// If they swip up off the keyboard, launch the cheat
						// sheet. This was originally a check for e2.getY() < 0,
						// but that didn't work in ICS. Possibly ICS stops
						// sending you events after you go past the edge of the
						// window. So I changed it to 10 instead.
						if (e2.getY() <= 10) {
							// If they swipe up off the keyboard, launch the
							// cheat sheet
							showCheatSheet();
							return true;
						}
						else  {
							final float absX = Math.abs(velocityX);
							final float absY = Math.abs(velocityY);
							float deltaX = e2.getX() - e1.getX();
							int travelMin = Math.min((getWidth() / 3),
									(getHeight() / 3));

							if (velocityX > mSwipeThreshold && absY < absX
									&& deltaX > travelMin) {
								service.updateKeyboard();
								return true;
							} else if (velocityX < -mSwipeThreshold
									&& absY < absX && deltaX < -travelMin) {
								service.updateKeyboard();
								return true;
							}
						}
//						else if (mEnableUtilityKeyboard) {
//							final float absX = Math.abs(velocityX);
//							final float absY = Math.abs(velocityY);
//							float deltaX = e2.getX() - e1.getX();
//							int travelMin = Math.min((getWidth() / 3),
//									(getHeight() / 3));
//
//							if (velocityX > mSwipeThreshold && absY < absX
//									&& deltaX > travelMin) {
//								toggleKeyboard();
//								return true;
//							} else if (velocityX < -mSwipeThreshold
//									&& absY < absX && deltaX < -travelMin) {
//								toggleKeyboard();
//								return true;
//							}
//						}
						return false;
					}
				});
		OnTouchListener gestureListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				switch (action)
				{
					case MotionEvent.ACTION_DOWN:
						startTime = System.currentTimeMillis();
						endTime = startTime;
						break;
					case MotionEvent.ACTION_UP:
						endTime = System.currentTimeMillis();
						code = morseDurationResolver.onRelease(endTime - startTime);
						break;
					default:
						code = morseDurationResolver.getSymbolFromDuration(endTime - startTime);
						break;
				}

				return gestureDetector.onTouchEvent(event);
			}
		};
		setOnTouchListener(gestureListener);
	}
	
	public char getCode(){
		return code;
	}
	private void toggleKeyboard() {
		if (getKeyboard() == service.dotDashKeyboard) {
			setKeyboard(service.utilityKeyboard);
			setPreviewEnabled(true);
		} else {
			setKeyboard(service.dotDashKeyboard);
			setPreviewEnabled(false);
		}
	}

	public void createCheatSheet() {
		if (this.cheatsheet1 == null) {
			this.cheatsheet1 = this.service.getLayoutInflater().inflate(
					R.layout.cheatsheet1, null);
		}
		if (this.cheatsheet2 == null) {
			this.cheatsheet2 = this.service.getLayoutInflater().inflate(
					R.layout.cheatsheet2, null);
			updateNewlineCode();
		}
		if (this.cheatsheetDialog == null) {
			this.cheatsheetDialog = new Dialog(this.service);

			cheatsheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

			cheatsheetDialog.setCancelable(true);
			cheatsheetDialog.setCanceledOnTouchOutside(true);
			cheatsheetDialog.setContentView(cheatsheet1);
			cheatsheet1.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					FixedSizeView fsv = (FixedSizeView) cheatsheet2;
					fsv.fixedHeight = cheatsheet1.getMeasuredHeight();
					fsv.fixedWidth = cheatsheet1.getMeasuredWidth();
					cheatsheetDialog.setContentView(cheatsheet2);
					return true;
				}
			});
			cheatsheet2.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					cheatsheetDialog.setContentView(cheatsheet1);
					return true;
				}
			});
			Window window = this.cheatsheetDialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.token = this.getWindowToken();
			lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
			window.setAttributes(lp);
			window.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		}
	}

	public void showCheatSheet() {
		createCheatSheet();
		this.cheatsheetDialog.show();
	}

	public void closeCheatSheet() {
		if (cheatsheetDialog != null) {
			cheatsheetDialog.dismiss();
		}
	}

	/**
	 * Updates the newline code printed in the cheat sheet, based on the user's
	 * current preference.
	 */
	public void updateNewlineCode() {
		if (cheatsheet2 == null) {
			return;
		}

		String newCode = "disabled";
		if (service.newlineGroups != null && service.newlineGroups.length > 0) {
			newCode = service.newlineGroups[0].replaceAll("(.)", "$1 ").trim();
		}
		((TextView) cheatsheet2.findViewById(R.id.newline_code))
				.setText(newCode);
	}

	public int whichKeyboard() {
		Keyboard kbd = getKeyboard();
		if (kbd == service.dotDashKeyboard || kbd == service.oneButtonKeyboard) {
			return KBD_DOTDASH;
		} else if (kbd == service.utilityKeyboard) {
			return KBD_UTILITY;
		} else
			return KBD_NONE;
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
