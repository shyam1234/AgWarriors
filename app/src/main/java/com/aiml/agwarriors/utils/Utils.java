package com.aiml.agwarriors.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
                if (model.getStatusValue() == YieldListModel.STATUS_NOTIFY_TO_SELLER ||
                        model.getStatusValue() == YieldListModel.STATUS_DISMISS_BY_BUYER) {
                    counter++;
                }
            }
        }
        return "" + counter;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
