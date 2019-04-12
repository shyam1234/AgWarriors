package com.aiml.agwarriors.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.aiml.agwarriors.application.MyApplication;
import com.aiml.agwarriors.model.TableNotificationDataModel;
import com.aiml.agwarriors.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableNotification {
    //--------------------------------------------------------------------------
    public static final String TAG = "TableNotificationDataModel";
    public static final String TABLE_NAME = "table_notification";
    public static final String ID = "id";
    public static final String FROM_ID = "from_id";
    public static final String TO_ID = "to_id";
    public static final String TIME = "noti_time";
    public static final String STATUS = "status";
    public static final String TITLE = "title";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = " TRUNCATE TABLE " + TABLE_NAME;
    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + ID + " varchar(255), "
            + FROM_ID + " varchar(255), "
            + TO_ID + " varchar(255), "
            + TIME + " varchar(255), "
            + STATUS + " varchar(255), "
            + TITLE + " varchar(255) "
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

    public synchronized boolean insert(TableNotificationDataModel holder) {
        try {
            if (mDB != null) {
                //deleteDataIfExist(holder.getUniversityId(), holder.getConversionId());
                ContentValues contentValues = new ContentValues();
                contentValues.put(ID, holder.getID());
                contentValues.put(FROM_ID, holder.getFROM_ID());
                contentValues.put(TO_ID, holder.getTO_ID());
                contentValues.put(TIME, holder.getTIME());
                contentValues.put(STATUS, holder.getSTATUS());
                contentValues.put(TITLE, holder.getTITLE());
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


    public synchronized ArrayList<TableNotificationDataModel> read(int pID) {
        try {
            ArrayList<TableNotificationDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ID + "='" + pID + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableNotificationDataModel model = new TableNotificationDataModel();
                        model.setID(cursor.getString(cursor.getColumnIndex(ID)));
                        model.setFROM_ID(cursor.getString(cursor.getColumnIndex(FROM_ID)));
                        model.setTO_ID((cursor.getString(cursor.getColumnIndex(TO_ID))));
                        model.setTIME((cursor.getString(cursor.getColumnIndex(TIME))));
                        model.setTITLE((cursor.getString(cursor.getColumnIndex(TITLE))));
                        model.setSTATUS((cursor.getString(cursor.getColumnIndex(STATUS))));
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


    public synchronized TableNotificationDataModel getUserInfo(String pYield) {
        try {
            TableNotificationDataModel model = new TableNotificationDataModel();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ID + "='" + pYield + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        model.setID(cursor.getString(cursor.getColumnIndex(ID)));
                        model.setFROM_ID(cursor.getString(cursor.getColumnIndex(FROM_ID)));
                        model.setTO_ID((cursor.getString(cursor.getColumnIndex(TO_ID))));
                        model.setTIME((cursor.getString(cursor.getColumnIndex(TIME))));
                        model.setTITLE((cursor.getString(cursor.getColumnIndex(TITLE))));
                        model.setSTATUS((cursor.getString(cursor.getColumnIndex(STATUS))));
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
