package ru.spbau.morsekeyboard.keyboard;

import ru.spbau.morsekeyboard.MorseDurationResolver;
import ru.spbau.morsekeyboard.MorseTableItemAdapter;
import ru.spbau.morsekeyboard.MorseTranslator;
import ru.spbau.morsekeyboard.R;
import android.app.Dialog;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.*;
import android.widget.ListView;

public class DotDashKeyboardView extends KeyboardView{

	private DotDashIMEService mService;
	private Dialog mAboutDialog;
	private View mAbout;
	private int mSwipeThreshold;

	private long mStartTime;
	private long mEndTime;
	private MorseDurationResolver mMorseDurationResolver;
	
	private char mCode;

	public void setService(DotDashIMEService service) {
		this.mService = service;
	}

	public DotDashKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mStartTime = 0;
		mEndTime = 0;
		mMorseDurationResolver = new MorseDurationResolver(300L);

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

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {

						if (e2.getY() <= 10) {
							showAboutDialog();
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
								mService.updateKeyboard();
								return true;
							} else if (velocityX < -mSwipeThreshold
									&& absY < absX && deltaX < -travelMin) {
								mService.updateKeyboard();
								return true;
							}
						}
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
						mStartTime = System.currentTimeMillis();
						mEndTime = mStartTime;
						break;
					case MotionEvent.ACTION_UP:
						mEndTime = System.currentTimeMillis();
						mCode = mMorseDurationResolver.onRelease(mEndTime - mStartTime);
						break;
					default:
						mCode = mMorseDurationResolver.getSymbolFromDuration(mEndTime - mStartTime);
						break;
				}

				return gestureDetector.onTouchEvent(event);
			}
		};
		setOnTouchListener(gestureListener);
	}
	
	public char getCode(){
		return mCode;
	}

	public void initAbout(){
		if (mAbout == null){
			mAbout = mService.getLayoutInflater().inflate(R.layout.about, null);
			((ListView)mAbout.findViewById(R.id.translate_table)).
				setAdapter(new MorseTableItemAdapter(getContext(),
						new MorseTranslator(getContext()).getTable()));
			mAbout.findViewById(R.id.descriptionLayout)
				.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					closeAboutDialog();
				}
			});
			mAboutDialog = new Dialog(mService);
			mAboutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			//TODO
			mAboutDialog.setCancelable(true);
			mAboutDialog.setCanceledOnTouchOutside(true);
			mAboutDialog.setContentView(mAbout);
			Window window = mAboutDialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.token = getWindowToken();
			lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
			window.setAttributes(lp);
			window.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		}
		
	}
	
	public void showAboutDialog() {
		if (mAboutDialog == null){
			initAbout();
		}
		this.mAboutDialog.show();
	}

	public void closeAboutDialog() {
		if (mAboutDialog != null) {
			mAboutDialog.dismiss();
		}
	}

}
