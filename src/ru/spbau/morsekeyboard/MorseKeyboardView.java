package ru.spbau.morsekeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 *
 *
 */
public class MorseKeyboardView extends LinearLayout {

    private Button mDot;
    private Button mDashe;
    private Button mSpace;
    private Button mSettings;
    private Button mClear;
    private EditText mMorseState;
    private EditText mSymbolState;
    private LinearLayout mStateLayout;
    private LinearLayout mMorseLayout;
    private LinearLayout mAdditionalLayout;
    private Context mCtx;

    public MorseKeyboardView(Context context) {
        super(context);
        mCtx = context;
        init(context);
    }

    public MorseKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCtx = context;
        init(context);
    }


        private void init(Context ctx){
        setOrientation(VERTICAL);
        mStateLayout = new LinearLayout(ctx);
        mStateLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,30));
        mStateLayout.setOrientation(LinearLayout.HORIZONTAL);

        mMorseLayout = new LinearLayout(ctx);
        mStateLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,130));
        mMorseLayout.setOrientation(LinearLayout.HORIZONTAL);

        mAdditionalLayout = new LinearLayout(ctx);
        mStateLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,40));
        mAdditionalLayout.setOrientation(LinearLayout.HORIZONTAL);

        mMorseState = new EditText(ctx);

        mMorseState.setLayoutParams(new TableLayout.LayoutParams(
                0,
                30,
                3f));
        mMorseState.setGravity(Gravity.CENTER_HORIZONTAL);

        mSymbolState = new EditText(ctx);
        mSymbolState.setLayoutParams(new TableLayout.LayoutParams(
                0,
                30,
                1f));
        mSymbolState.setGravity(Gravity.CENTER_HORIZONTAL);

        mStateLayout.addView(mMorseState);
        mStateLayout.addView(mSymbolState);

        mDot = new Button(ctx);
        mDot.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        mDot.setText(".");
        mDot.setTextSize(50f);
        mDot.setGravity(Gravity.CENTER);

        mDashe = new Button(ctx);
        mDashe.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,1f));
        mDashe.setText("-");
        mDashe.setTextSize(50f);
        mDashe.setGravity(Gravity.CENTER);

        mMorseLayout.addView(mDot);
        mMorseLayout.addView(mDashe);

        mSpace = new Button(ctx);
        mSpace.setLayoutParams(new TableLayout.LayoutParams(0,LayoutParams.FILL_PARENT,5f));
        
        mSettings = new Button(ctx);
        mSettings.setLayoutParams(new TableLayout.LayoutParams(0,LayoutParams.FILL_PARENT,1f));
        mSettings.setText("S");

        mClear = new Button(ctx);
        mClear.setLayoutParams(new TableLayout.LayoutParams(0,LayoutParams.FILL_PARENT,1f));
        mClear.setText("C");

        mAdditionalLayout.addView(mSettings);
        mAdditionalLayout.addView(mSpace);
        mAdditionalLayout.addView(mClear);
        this.addView(mStateLayout);
        this.addView(mMorseLayout);
        this.addView(mAdditionalLayout);
    }
}

