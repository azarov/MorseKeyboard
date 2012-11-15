package ru.spbau.morsekeyboard;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import ru.spbau.morsekeyboard.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: DimaTWL
 * Date: 11/12/12
 * Time: 3:36 PM
 *
 */
public class MorseTranslator {
    private static final Hashtable<String, String> table = new Hashtable<String, String>();
    private int mMaxSize = 0;
    
    public int getMax(){
    	return mMaxSize;
    }
    
    public Hashtable<String, String> getTable() {
        return table;
    }

    public String getSymbol(String code) throws BadCodeException {
        String symbol = table.get(code);
        if (null == symbol) {
            throw new BadCodeException("Unknown morse code: <<<" + code + ">>>");
        } else {
            return symbol;
        }
    }

    public List<String> getHintSymbolList(String code) {
        List<String> symbolList = new ArrayList<String>();

        for (Enumeration<String> keys = table.keys(); keys.hasMoreElements();) {
            String nextElement = keys.nextElement();
            if(nextElement.startsWith(code)){
                symbolList.add(table.get(nextElement));
            }
        }
        return symbolList;
    }

    public MorseTranslator(Context context) {
        Field[] fields = R.string.class.getDeclaredFields();
        for (Field field : fields) {
            String code = field.getName();
            
            Boolean valid = true;
            for (Character character : code.toCharArray()) {
                if (!character.equals('p') && !character.equals('d')) {
                	valid = false;
                    break;
                }
            }
            if (valid) {
                try {
                	table.put(code.replaceAll("p", ".").replaceAll("d", "-"), context.getResources().getString((Integer)field.get(null)));
                	mMaxSize = Math.max(mMaxSize, code.length());
                }catch(NotFoundException e){
                	Log.d("MorseKeyboard","symbol is unsupported in this locale");
                }catch (IllegalAccessException e) {
                	Log.d("MorseKeyboard","fail access to resourse");
                }
            }
        }
    }

}
