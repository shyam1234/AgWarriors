package com.aiml.agwarriors.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.aiml.agwarriors.application.MyApplication;
import com.aiml.agwarriors.model.TableUserInfoDataModel;
import com.aiml.agwarriors.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableUserInfo {
    //--------------------------------------------------------------------------
    public static final String TAG = "TableUserInfo";
    public static final String TABLE_NAME = "table_user_info";
    public static final String ID = "uid";
    public static final String USERNAME = "uname";
    public static final String NAME = "name";
    public static final String PASS = "pass";
    public static final String ADDRESS = "address";
    public static final String LAT = "lat";
    public static final String LONG = "long";
    public static final String TYPE = "type";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE_DIARY = " TRUNCATE TABLE " + TABLE_NAME;
    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + ID + " varchar(255), "
            + USERNAME + " varchar(255), "
            + NAME + " varchar(255), "
            + PASS + " varchar(255), "
            + ADDRESS + " varchar(255), "
            + LAT + " varchar(255), "
            + LONG + " varchar(255), "
            + TYPE + " varchar(255) "
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

    public synchronized boolean insert(TableUserInfoDataModel holder) {
        try {
            if (mDB != null) {
                //deleteDataIfExist(holder.getUniversityId(), holder.getConversionId());
                ContentValues contentValues = new ContentValues();
                contentValues.put(ID, holder.getID());
                contentValues.put(USERNAME, holder.getUSERNAME());
                contentValues.put(PASS, holder.getPASS());
                contentValues.put(NAME, holder.getNAME());
                contentValues.put(ADDRESS, holder.getADDRESS());
                contentValues.put(LAT, holder.getLAT());
                contentValues.put(LONG, holder.getLONG());
                contentValues.put(TYPE, holder.getTYPE());
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


    public synchronized ArrayList<TableUserInfoDataModel> read(int pUserID) {
        try {
            ArrayList<TableUserInfoDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ID + "='" + pUserID + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableUserInfoDataModel model = new TableUserInfoDataModel();
                        model.setID(cursor.getString(cursor.getColumnIndex(ID)));
                        model.setUSERNAME(cursor.getString(cursor.getColumnIndex(USERNAME)));
                        model.setPASS((cursor.getString(cursor.getColumnIndex(PASS))));
                        model.setADDRESS((cursor.getString(cursor.getColumnIndex(ADDRESS))));
                        model.setNAME((cursor.getString(cursor.getColumnIndex(NAME))));
                        model.setLAT((cursor.getString(cursor.getColumnIndex(LAT))));
                        model.setLONG((cursor.getString(cursor.getColumnIndex(LONG))));
                        model.setTYPE((cursor.getString(cursor.getColumnIndex(TYPE))));
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


    public synchronized TableUserInfoDataModel getUserInfo(String pUID) {
        try {
            TableUserInfoDataModel model = new TableUserInfoDataModel();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ID + "='" + pUID + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        model.setID(cursor.getString(cursor.getColumnIndex(ID)));
                        model.setUSERNAME(cursor.getString(cursor.getColumnIndex(USERNAME)));
                        model.setPASS((cursor.getString(cursor.getColumnIndex(PASS))));
                        model.setADDRESS((cursor.getString(cursor.getColumnIndex(ADDRESS))));
                        model.setNAME((cursor.getString(cursor.getColumnIndex(NAME))));
                        model.setLAT((cursor.getString(cursor.getColumnIndex(LAT))));
                        model.setLONG((cursor.getString(cursor.getColumnIndex(LONG))));
                        model.setTYPE((cursor.getString(cursor.getColumnIndex(TYPE))));
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
                mDB.execSQL(TRUNCATE_TABLE_DIARY);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }

}
