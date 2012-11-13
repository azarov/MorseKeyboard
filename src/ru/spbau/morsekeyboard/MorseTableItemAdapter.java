package ru.spbau.morsekeyboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

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

	public int getCount() {
		return codes.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		
		if (row == null) {
			row = inflater.inflate(R.layout.table_item, null, false);
		}

		TextView code = (TextView)row.findViewById(R.id.morse_code);
		TextView symbol = (TextView)row.findViewById(R.id.morse_symbol);

		code.setText(codes.get(position));
		symbol.setText(symbols.get(position));


		return row;
	}

}
