package ru.spbau.morsekeyboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.*;

public class MorseTableItemAdapter extends BaseAdapter{
	
	private ArrayList<Map.Entry<String, String>> letters;
	private ArrayList<Map.Entry<String, String>> digits;
	private ArrayList<Map.Entry<String, String>> symbols;
	private Context context;
	private final LayoutInflater inflater;

	public MorseTableItemAdapter(Context context, Map<String, String> map) {
		this.context = context;
		letters = new ArrayList<Map.Entry<String, String>>();
		digits = new ArrayList<Map.Entry<String, String>>();
		symbols = new ArrayList<Map.Entry<String, String>>();
		
		for (Map.Entry<String, String> entry : map.entrySet())
		{
			String value = entry.getValue();
			if(isStringContainsOnlyLetters(value))
			{
				letters.add(entry);
			}
			else if(isStringContainsOnlyDigits(value))
			{
				digits.add(entry);
			}
			else
			{
				symbols.add(entry);
			}
		}
		
		EntriesByValuesComparator comp = new EntriesByValuesComparator();

		Collections.sort(letters, comp);
		Collections.sort(digits, comp);
		Collections.sort(symbols, comp);

		inflater = LayoutInflater.from(context);
	}

	private boolean isStringContainsOnlyDigits(String s) {
		if(s == null)
		{
			return false;
		}

		int length = s.length();
		for (int i = 0; i < length; i++)
		{
			if(!Character.isDigit(s.charAt(i)))
			{
				return false;
			}
		}
		return true;

	}

	private boolean isStringContainsOnlyLetters(String s)
	{
		if(s == null)
		{
			return false;
		}

		int length = s.length();
		for (int i = 0; i < length; i++)
		{
			if(!Character.isLetter(s.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	public int getCount() {
		return 3 + letters.size() + symbols.size() + digits.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		if(position == 0 || position == 1+letters.size() || position == 2+ letters.size() + digits.size())
		{
			return 0;
		}

		return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	private String getHeaderFromPosition(int position)
	{
		if(position == 0)
		{
			return context.getResources().getString(R.string.morse_table_header_letters);
		}
		else if (position == 1 + letters.size())
		{
			return context.getResources().getString(R.string.morse_table_header_digits);
		}
		else
		{
			return context.getResources().getString(R.string.morse_table_header_symbols);
		}
	}

	private Map.Entry<String, String> getEntryFromPosition(int position)
	{
		if(position < 1 + letters.size())
		{
			return letters.get(position - 1);
		}
		else if (position < 2 + letters.size() + digits.size())
		{
			return digits.get(position - 2 - letters.size());
		}
		else
		{
			return symbols.get(position - 3 - letters.size() - digits.size());
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

				if(getItemViewType(position) == 0)
		{
			if (row == null) {
				row = inflater.inflate(R.layout.table_item_header, null, false);
			}
			
			TextView header = (TextView)row.findViewById(R.id.morse_table_group_header);
			header.setText(getHeaderFromPosition(position));
		}
		else
		{
			if (row == null) {
				row = inflater.inflate(R.layout.table_item, null, false);
			}
	
			TextView code = (TextView)row.findViewById(R.id.morse_code);
			TextView symbol = (TextView)row.findViewById(R.id.morse_symbol);
	
			code.setText(getEntryFromPosition(position).getKey());
			symbol.setText(getEntryFromPosition(position).getValue());
		}
		return row;
	}

	
	class EntriesByValuesComparator implements Comparator<Map.Entry<String, String>>
	{

		@Override
		public int compare(Map.Entry<String, String> entry1, Map.Entry<String, String> entry2) {
			return entry1.getValue().compareTo(entry2.getValue());
		}
	}
}
