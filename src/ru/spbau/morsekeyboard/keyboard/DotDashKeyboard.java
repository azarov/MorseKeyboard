package ru.spbau.morsekeyboard.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

public class DotDashKeyboard extends Keyboard {

	private Key spaceKey;
	private Key capsLockKey;
	
	public DotDashKeyboard(Context context, int xmlLayoutResId) {
		super(context, xmlLayoutResId);
	}

	public DotDashKeyboard(Context context, int layoutTemplateResId,
			CharSequence characters, int columns, int horizontalPadding) {
		super(context, layoutTemplateResId, characters, columns,
				horizontalPadding);
	}

		@Override
	protected Key createKeyFromXml(Resources res, Row parent, int x, int y,
			XmlResourceParser parser) {
		Key k = super.createKeyFromXml(res, parent, x, y, parser);
		if (k.codes[0] == 62) {
			spaceKey = k;
		} else if (k.codes[0] == 59) {
			capsLockKey = k;
		}
		return k;
	}

	public Key getSpaceKey() {
		return this.spaceKey;
	}

	public Key getCapsLockKey() {
		return this.capsLockKey;
	}
}
