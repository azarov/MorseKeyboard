package ru.spbau.morsekeyboard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.android.softkeyboard.R;

import java.util.ArrayList;
import java.util.Map;

public class MorseTableItemAdapter extends BaseAdapter{
	
	private ArrayList<String> codes;
	private ArrayList<String> symbols;
	private final LayoutInflater inflater;

	public MorseTableItemAdapter(Context context, Map<String, String> map) {
		codes = new ArrayList<String>(map.size());
		symbols = new ArrayList<String>(map.size());

		for (Map.Entry<String, String> entry : map.entrySet()) {
			codes.add(entry.getKey());
			symbols.add(entry.getValue());
		}

		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return codes.size();
	}

	@Override
	public Object getItem(int i) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public long getItemId(int i) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		View row = view;

		if (row != null) {
			row = inflater.inflate(R.layout.table_item, null, false);
			TextView code = (TextView)row.findViewById(R.id.morse_code);
			TextView symbol = (TextView)row.findViewById(R.id.morse_symbol);
			code.setText(codes.get(i));
			symbol.setText(symbols.get(i));
		}
		else
		{
			//do nothing
		}

		return row;
	}
}
