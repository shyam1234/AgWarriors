package com.aiml.agwarriors.utils;

import com.aiml.agwarriors.model.YieldListModel;

import java.util.ArrayList;

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

    public static boolean isNotificationForSeller(ArrayList<YieldListModel> pList) {
        if (pList != null && pList.size() > 0) {
            for (YieldListModel model : pList) {
                if (model.getStatusValue() == YieldListModel.STATUS_NOTIFY_TO_SELLER) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getNotificationCountForSeller(ArrayList<YieldListModel> pList) {
        int counter = 0;
        if (pList != null && pList.size() > 0) {
            for (YieldListModel model : pList) {
                if (model.getStatusValue() == YieldListModel.STATUS_NOTIFY_TO_SELLER) {
                    counter++;
                }
            }
        }
        return "" + counter;
    }
}
