package ru.spbau.morsekeyboard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import ru.spbau.morsekeyboard.R;

public class AboutActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.about);

		MorseTranslator translator = new MorseTranslator(this);
		ListView listView = (ListView)findViewById(R.id.translate_table);
		listView.setAdapter(new MorseTableItemAdapter(this, translator.getTable()));

	}
}
