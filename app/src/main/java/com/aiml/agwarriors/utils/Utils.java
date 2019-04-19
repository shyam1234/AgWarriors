package com.aiml.agwarriors.utils;

public class Utils {

    public static int getSpinnerPosition(String[] array, String pString) {
        if (array != null && array.length > 0) {
            for (int position = 0; position < array.length; position++) {
                if (array[position].equalsIgnoreCase(pString)) {
                    return position;
                }
            }
        }
        return 0;
    }
}
