package ru.spbau.morsekeyboard;

import android.content.Context;
import com.example.android.softkeyboard.R;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: DimaTWL
 * Date: 11/12/12
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MorseTranslator {
    private Map<String, String> table = new TreeMap<String, String>();

    public Map<String, String> getTable() {
        return table;
    }

    public String getSymbol(String code) throws BadCodeException {
        String symbol = table.get(code);
        if (null == symbol) {
            throw new BadCodeException("Unknown morse code: " + code + ".");
        } else {
            return symbol;
        }
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
                    table.put(code, context.getResources().getString((Integer)field.get(null)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
