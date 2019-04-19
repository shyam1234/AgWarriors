package com.aiml.agwarriors.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.aiml.agwarriors.application.MyApplication;
import com.aiml.agwarriors.model.TableYieldDataModel;
import com.aiml.agwarriors.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableYield {
    //--------------------------------------------------------------------------
    public static final String TAG = "TableYield";
    public static final String TABLE_NAME = "table_yield";
    public static final String ID = "id";
    public static final String UID = "uid";
    public static final String YIELD = "yield_name";
    public static final String YIELD_TYPE = "yield_type";
    public static final String QTY = "qty";
    public static final String DURATION = "duration";
    public static final String PLACE_TO_SELL = "place_to_sell";
    public static final String COST = "cost";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = " TRUNCATE TABLE " + TABLE_NAME;
    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + ID + " varchar(255), "
            + UID + " varchar(255), "
            + YIELD + " varchar(255), "
            + YIELD_TYPE + " varchar(255), "
            + QTY + " varchar(255), "
            + DURATION + " varchar(255), "
            + PLACE_TO_SELL + " varchar(255), "
            + COST + " varchar(255) "
            + " ) ";
    private SQLiteDatabase mDB;
    //For Foreign key
    //  + " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";

    public synchronized void openDB(Context pContext) {
        DatabaseHelper helper = DatabaseHelper.getInstance(pContext);
        mDB = helper.getWritableDatabase();
    }

    public synchronized void closeDB() {
        if (mDB != null) {
            mDB = null;
        }
    }

    //--------------------------------------------------------------------------------------------------------------------

    public synchronized boolean insert(TableYieldDataModel holder) {
        try {
            if (mDB != null) {
                //deleteDataIfExist(holder.getUniversityId(), holder.getConversionId());
                ContentValues contentValues = new ContentValues();
                contentValues.put(ID, holder.getLotId());
                contentValues.put(UID, holder.getUID());
                contentValues.put(YIELD, holder.getYIELD());
                contentValues.put(YIELD_TYPE, holder.getYIELD_TYPE());
                contentValues.put(QTY, holder.getQTY());
                contentValues.put(DURATION, holder.getDURATION());
                contentValues.put(PLACE_TO_SELL, holder.getPLACE_TO_SELL());
                contentValues.put(COST, holder.getCOST());
                mDB.insert(TABLE_NAME, null, contentValues);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private synchronized void deleteDataIfExist(int pUserID) {
        try {
            String selectQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=" + pUserID;//+ " AND " + CONVERSION_ID + "=" + pConversionId;
            mDB.execSQL(selectQuery);
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from deleteDataIfExist " + e.getMessage());
        }
    }


    public synchronized ArrayList<TableYieldDataModel> read(int pYield) {
        try {
            ArrayList<TableYieldDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ID + "='" + pYield + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableYieldDataModel model = new TableYieldDataModel();
                        model.setLotId(cursor.getString(cursor.getColumnIndex(ID)));
                        model.setUID(cursor.getString(cursor.getColumnIndex(UID)));
                        model.setCOST((cursor.getString(cursor.getColumnIndex(COST))));
                        model.setDURATION((cursor.getString(cursor.getColumnIndex(DURATION))));
                        model.setPLACE_TO_SELL((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setYIELD((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYIELD_TYPE((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    public synchronized TableYieldDataModel getUserInfo(String pYield) {
        try {
            TableYieldDataModel model = new TableYieldDataModel();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ID + "='" + pYield + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        model.setLotId(cursor.getString(cursor.getColumnIndex(ID)));
                        model.setUID(cursor.getString(cursor.getColumnIndex(UID)));
                        model.setCOST((cursor.getString(cursor.getColumnIndex(COST))));
                        model.setDURATION((cursor.getString(cursor.getColumnIndex(DURATION))));
                        model.setPLACE_TO_SELL((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setYIELD((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYIELD_TYPE((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return model;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    public synchronized void dropTable() {
        try {
            if (mDB != null) {
                mDB.execSQL(DROP_TABLE);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }

    public synchronized void reset() {
        try {
            if (mDB != null) {
                mDB.execSQL(TRUNCATE_TABLE);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }

}
