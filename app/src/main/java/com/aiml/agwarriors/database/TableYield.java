package com.aiml.agwarriors.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.aiml.agwarriors.application.MyApplication;
import com.aiml.agwarriors.model.YieldListModel;
import com.aiml.agwarriors.utils.AppLog;

import java.util.ArrayList;

/**1`
 * Created by Admin on 26-11-2016.
 */
public class TableYield {
    //--------------------------------------------------------------------------
    public static final String TAG = "TableYield";
    public static final String TABLE_NAME = "table_yield";
    public static final String LOTID = "lotnumber";
    public static final String USER_ID = "user_id";
    public static final String YIELD = "yield_name";
    public static final String YIELD_TYPE = "yield_type";
    public static final String QTY = "qty";
    public static final String DATE = "date";
    public static final String PLACE_TO_SELL = "place_to_sell";
    public static final String COST_PER_UNIT = "cost";
    public static final String BID_COST_PER_UNIT = "bid_cost";
    public static final String COST_UNIT = "cost_unit";
    public static final String STATUS = "status";
    public static final String QTY_TYPE = "qty_type";
    public static final String STATUS_VALUE = "status_value";
    public static final String MESSAGE_TO="message_to";
    public static final String MESSAGE_FROM="message_from";
    private static final String IMAGE_NAME = "image_name";
    private static final String IMAGE_DATA = "image_data";


    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = " TRUNCATE TABLE " + TABLE_NAME;
    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + LOTID + " varchar(255), "
            + USER_ID + " varchar(255), "
            + YIELD + " varchar(255), "
            + YIELD_TYPE + " varchar(255), "
            + QTY + " varchar(255), "
            + DATE + " varchar(255), "
            + MESSAGE_TO + " varchar(255), "
            + MESSAGE_FROM + " varchar(255), "
            + PLACE_TO_SELL + " varchar(255), "
            + BID_COST_PER_UNIT + " varchar(255), "
            + COST_UNIT + " varchar(255), "
            + STATUS + " varchar(255), "
            + QTY_TYPE + " varchar(255), "
            + STATUS_VALUE + " int, "
            + IMAGE_NAME + " varchar(255), "
            + IMAGE_DATA + " BLOB, "
            + COST_PER_UNIT + " varchar(255) "
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

    public synchronized long insert(YieldListModel holder) {
        long row = 0;
        try {
            if (mDB != null) {
                //deleteDataIfExist(holder.getUniversityId(), holder.getConversionId());
                ContentValues contentValues = new ContentValues();
                contentValues.put(LOTID, holder.getLotnumber());
                contentValues.put(USER_ID, holder.getUserID());
                contentValues.put(MESSAGE_TO, holder.getMessageTo());
                contentValues.put(MESSAGE_FROM, holder.getMessageFrom());
                contentValues.put(YIELD, holder.getYield());
                contentValues.put(YIELD_TYPE, holder.getYieldType());
                contentValues.put(QTY, holder.getQTY());
                contentValues.put(QTY_TYPE, holder.getQTYType());
                contentValues.put(DATE, holder.getDate());
                contentValues.put(PLACE_TO_SELL, holder.getPlaceToSell());
                contentValues.put(COST_PER_UNIT, holder.getCostPerUnit());
                contentValues.put(COST_UNIT, holder.getCostUnit());
                contentValues.put(BID_COST_PER_UNIT, holder.getBidCostPerUnit());
                contentValues.put(STATUS, holder.getStatus());
                contentValues.put(STATUS_VALUE, holder.getStatusValue());
                contentValues.put(IMAGE_NAME, holder.getImageName());
                contentValues.put(IMAGE_DATA, holder.getImageArray());

                row = mDB.insert(TABLE_NAME, null, contentValues);
                return row;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            return row;
        }
        return row;
    }


    public synchronized long update(YieldListModel holder) {
        long row = 0;
        try {
            if (mDB != null) {
                //deleteDataIfExist(holder.getUniversityId(), holder.getConversionId());
                ContentValues contentValues = new ContentValues();
                contentValues.put(LOTID, holder.getLotnumber());
                contentValues.put(USER_ID, holder.getUserID());
                contentValues.put(MESSAGE_TO, holder.getMessageTo());
                contentValues.put(MESSAGE_FROM, holder.getMessageFrom());
                contentValues.put(YIELD, holder.getYield());
                contentValues.put(YIELD_TYPE, holder.getYieldType());
                contentValues.put(QTY, holder.getQTY());
                contentValues.put(QTY_TYPE, holder.getQTYType());
                contentValues.put(DATE, holder.getDate());
                contentValues.put(PLACE_TO_SELL, holder.getPlaceToSell());
                contentValues.put(COST_PER_UNIT, holder.getCostPerUnit());
                contentValues.put(COST_UNIT, holder.getCostUnit());
                contentValues.put(BID_COST_PER_UNIT, holder.getBidCostPerUnit());
                contentValues.put(STATUS, holder.getStatus());
                contentValues.put(STATUS_VALUE, holder.getStatusValue());
                contentValues.put(IMAGE_NAME, holder.getImageName());
                contentValues.put(IMAGE_DATA, holder.getImageArray());
                //long row = mDB.update(TABLE_NAME, value, COL_REFERENCEID + "=? and " + COL_STUDENTID + "=?", new String[]{"" + pRefId, "" + UserInfo.studentId});
                row = mDB.update(TABLE_NAME, contentValues, LOTID + "=? ", new String[]{"" + holder.getLotnumber()});
                return row;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            return row;
        }
        return row;
    }


    public synchronized void deleteDataIfExist(int pLotID) {
        try {
            String selectQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + LOTID + "=" + pLotID;//+ " AND " + CONVERSION_ID + "=" + pConversionId;
            mDB.execSQL(selectQuery);
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from deleteDataIfExist " + e.getMessage());
        }
    }

    public synchronized void deleteData(String pLotID, String pUserID) {
        try {
            String selectQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + LOTID + "=" + pLotID + " AND " + USER_ID + "=" + pUserID;
            mDB.execSQL(selectQuery);
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from deleteDataByBuyer " + e.getMessage());
        }
    }

    public synchronized ArrayList<YieldListModel> getUserIDForLotAndBuyer(String pLOTID, String pBuyerID) {
        try {
            ArrayList<YieldListModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + LOTID + "='" + pLOTID + "' AND "
                        + MESSAGE_TO + "='" + pBuyerID + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {

                        // get the data into array, or class variable
                        YieldListModel model = new YieldListModel();
                        model.setLotnumber(cursor.getString(cursor.getColumnIndex(LOTID)));
                        model.setUserID(cursor.getString(cursor.getColumnIndex(USER_ID)));
                        model.setMessageTo(cursor.getString(cursor.getColumnIndex(MESSAGE_TO)));
                        model.setYield((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYieldType((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        model.setDate((cursor.getString(cursor.getColumnIndex(DATE))));
                        model.setPlaceToSell((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setBidCostPerUnit((cursor.getString(cursor.getColumnIndex(BID_COST_PER_UNIT))));
                        model.setCostUnit((cursor.getString(cursor.getColumnIndex(COST_UNIT))));
                        model.setStatus((cursor.getString(cursor.getColumnIndex(STATUS))));
                        model.setQTYType((cursor.getString(cursor.getColumnIndex(QTY_TYPE))));
                        model.setStatusValue((cursor.getInt(cursor.getColumnIndex(STATUS_VALUE))));
                        model.setCostPerUnit((cursor.getString(cursor.getColumnIndex(COST_PER_UNIT))));
                        model.setImageName((cursor.getString(cursor.getColumnIndex(IMAGE_NAME))));
                        model.setImageArray((cursor.getBlob(cursor.getColumnIndex(IMAGE_DATA))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getUserIDForLotAndBuyer() " + e.getMessage());
            return null;
        }
    }

    public synchronized ArrayList<YieldListModel> getYieldInfoBasedOnFarmerID(String pFarmerID) {
        try {
            ArrayList<YieldListModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + USER_ID + "='" + pFarmerID + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {

                        // get the data into array, or class variable
                        YieldListModel model = new YieldListModel();
                        model.setLotnumber(cursor.getString(cursor.getColumnIndex(LOTID)));
                        model.setUserID(cursor.getString(cursor.getColumnIndex(USER_ID)));
                        model.setMessageTo(cursor.getString(cursor.getColumnIndex(MESSAGE_TO)));
                        model.setYield((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYieldType((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        model.setDate((cursor.getString(cursor.getColumnIndex(DATE))));
                        model.setPlaceToSell((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setBidCostPerUnit((cursor.getString(cursor.getColumnIndex(BID_COST_PER_UNIT))));
                        model.setCostUnit((cursor.getString(cursor.getColumnIndex(COST_UNIT))));
                        model.setStatus((cursor.getString(cursor.getColumnIndex(STATUS))));
                        model.setQTYType((cursor.getString(cursor.getColumnIndex(QTY_TYPE))));
                        model.setStatusValue((cursor.getInt(cursor.getColumnIndex(STATUS_VALUE))));
                        model.setCostPerUnit((cursor.getString(cursor.getColumnIndex(COST_PER_UNIT))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getYieldInfoBasedOnLotID() " + e.getMessage());
            return null;
        }
    }


    public synchronized ArrayList<YieldListModel> getBroadcastForSeller(String pUserID) {
        try {
            ArrayList<YieldListModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + USER_ID + "='" + pUserID + "' AND "
                        + STATUS_VALUE + "='" + YieldListModel.STATUS_SENT_BRAODCAST_TO_BUYER + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {

                        // get the data into array, or class variable
                        YieldListModel model = new YieldListModel();
                        model.setLotnumber(cursor.getString(cursor.getColumnIndex(LOTID)));
                        model.setUserID(cursor.getString(cursor.getColumnIndex(USER_ID)));
                        model.setMessageTo(cursor.getString(cursor.getColumnIndex(MESSAGE_TO)));
                        model.setMessageFrom(cursor.getString(cursor.getColumnIndex(MESSAGE_FROM)));
                        model.setYield((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYieldType((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        model.setDate((cursor.getString(cursor.getColumnIndex(DATE))));
                        model.setPlaceToSell((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setBidCostPerUnit((cursor.getString(cursor.getColumnIndex(BID_COST_PER_UNIT))));
                        model.setCostUnit((cursor.getString(cursor.getColumnIndex(COST_UNIT))));
                        model.setStatus((cursor.getString(cursor.getColumnIndex(STATUS))));
                        model.setQTYType((cursor.getString(cursor.getColumnIndex(QTY_TYPE))));
                        model.setStatusValue((cursor.getInt(cursor.getColumnIndex(STATUS_VALUE))));
                        model.setCostPerUnit((cursor.getString(cursor.getColumnIndex(COST_PER_UNIT))));
                        model.setImageName((cursor.getString(cursor.getColumnIndex(IMAGE_NAME))));
                        model.setImageArray((cursor.getBlob(cursor.getColumnIndex(IMAGE_DATA))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getBroadcastForSeller() " + e.getMessage());
            return null;
        }
    }


    public synchronized ArrayList<YieldListModel> getNotificationListForBuyer(String pUserId) {
        try {

            ArrayList<YieldListModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + MESSAGE_TO + "='" + pUserId + "' AND ( "
                        + STATUS_VALUE + "='" + YieldListModel.STATUS_DISMISS_BY_BUYER + "' OR "
                        /*+ STATUS_VALUE + "='" + YieldListModel.STATUS_SENT_BRAODCAST_TO_BUYER + "' OR "*/
                        + STATUS_VALUE + "='" + YieldListModel.STATUS_ACCEPT_PROPOSAL + "' OR "
                        + STATUS_VALUE + "='" + YieldListModel.STATUS_REJECT_PROPOSAL + "' )";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {

                        // get the data into array, or class variable
                        YieldListModel model = new YieldListModel();
                        model.setLotnumber(cursor.getString(cursor.getColumnIndex(LOTID)));
                        model.setUserID(cursor.getString(cursor.getColumnIndex(USER_ID)));
                        model.setMessageTo(cursor.getString(cursor.getColumnIndex(MESSAGE_TO)));
                        model.setMessageFrom(cursor.getString(cursor.getColumnIndex(MESSAGE_FROM)));
                        model.setYield((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYieldType((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        model.setDate((cursor.getString(cursor.getColumnIndex(DATE))));
                        model.setPlaceToSell((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setBidCostPerUnit((cursor.getString(cursor.getColumnIndex(BID_COST_PER_UNIT))));
                        model.setCostUnit((cursor.getString(cursor.getColumnIndex(COST_UNIT))));
                        model.setStatus((cursor.getString(cursor.getColumnIndex(STATUS))));
                        model.setQTYType((cursor.getString(cursor.getColumnIndex(QTY_TYPE))));
                        model.setStatusValue((cursor.getInt(cursor.getColumnIndex(STATUS_VALUE))));
                        model.setCostPerUnit((cursor.getString(cursor.getColumnIndex(COST_PER_UNIT))));
                        model.setImageName((cursor.getString(cursor.getColumnIndex(IMAGE_NAME))));
                        model.setImageArray((cursor.getBlob(cursor.getColumnIndex(IMAGE_DATA))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getNotificationListForBuyer() " + e.getMessage());
            return null;
        }
    }
    public synchronized ArrayList<YieldListModel> getNotificationList(String pUserId) {
        try {

            ArrayList<YieldListModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + MESSAGE_TO + "='" + pUserId + "' ";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {

                        // get the data into array, or class variable
                        YieldListModel model = new YieldListModel();
                        model.setLotnumber(cursor.getString(cursor.getColumnIndex(LOTID)));
                        model.setUserID(cursor.getString(cursor.getColumnIndex(USER_ID)));
                        model.setMessageTo(cursor.getString(cursor.getColumnIndex(MESSAGE_TO)));
                        model.setMessageFrom(cursor.getString(cursor.getColumnIndex(MESSAGE_FROM)));
                        model.setYield((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYieldType((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        model.setDate((cursor.getString(cursor.getColumnIndex(DATE))));
                        model.setPlaceToSell((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setBidCostPerUnit((cursor.getString(cursor.getColumnIndex(BID_COST_PER_UNIT))));
                        model.setCostUnit((cursor.getString(cursor.getColumnIndex(COST_UNIT))));
                        model.setStatus((cursor.getString(cursor.getColumnIndex(STATUS))));
                        model.setQTYType((cursor.getString(cursor.getColumnIndex(QTY_TYPE))));
                        model.setStatusValue((cursor.getInt(cursor.getColumnIndex(STATUS_VALUE))));
                        model.setCostPerUnit((cursor.getString(cursor.getColumnIndex(COST_PER_UNIT))));
                        model.setImageName((cursor.getString(cursor.getColumnIndex(IMAGE_NAME))));
                        model.setImageArray((cursor.getBlob(cursor.getColumnIndex(IMAGE_DATA))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getNotificationList() " + e.getMessage());
            return null;
        }
    }

    public synchronized ArrayList<YieldListModel> getProposalForBuyer(String pUserId) {
        try {

            ArrayList<YieldListModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + MESSAGE_TO + "='" + pUserId + "' AND ( "
                        + STATUS_VALUE + "='" + YieldListModel.STATUS_SENT_BRAODCAST_TO_BUYER + "' )";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {

                        // get the data into array, or class variable
                        YieldListModel model = new YieldListModel();
                        model.setLotnumber(cursor.getString(cursor.getColumnIndex(LOTID)));
                        model.setUserID(cursor.getString(cursor.getColumnIndex(USER_ID)));
                        model.setMessageTo(cursor.getString(cursor.getColumnIndex(MESSAGE_TO)));
                        model.setMessageFrom(cursor.getString(cursor.getColumnIndex(MESSAGE_FROM)));
                        model.setYield((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYieldType((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        model.setDate((cursor.getString(cursor.getColumnIndex(DATE))));
                        model.setPlaceToSell((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setBidCostPerUnit((cursor.getString(cursor.getColumnIndex(BID_COST_PER_UNIT))));
                        model.setCostUnit((cursor.getString(cursor.getColumnIndex(COST_UNIT))));
                        model.setStatus((cursor.getString(cursor.getColumnIndex(STATUS))));
                        model.setQTYType((cursor.getString(cursor.getColumnIndex(QTY_TYPE))));
                        model.setStatusValue((cursor.getInt(cursor.getColumnIndex(STATUS_VALUE))));
                        model.setCostPerUnit((cursor.getString(cursor.getColumnIndex(COST_PER_UNIT))));
                        model.setImageName((cursor.getString(cursor.getColumnIndex(IMAGE_NAME))));
                        model.setImageArray((cursor.getBlob(cursor.getColumnIndex(IMAGE_DATA))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getProposalForBuyer() " + e.getMessage());
            return null;
        }
    }

    public synchronized ArrayList<YieldListModel> getHistoryList(String pUserId) {
        try {
            ArrayList<YieldListModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + USER_ID + "='" + pUserId + "' AND ( "
                        + STATUS_VALUE + "='" + YieldListModel.STATUS_ACCEPT_PROPOSAL + "' OR "
                        + STATUS_VALUE + "='" + YieldListModel.STATUS_REJECT_PROPOSAL + "' )";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {

                        // get the data into array, or class variable
                        YieldListModel model = new YieldListModel();
                        model.setLotnumber(cursor.getString(cursor.getColumnIndex(LOTID)));
                        model.setUserID(cursor.getString(cursor.getColumnIndex(USER_ID)));
                        model.setMessageTo(cursor.getString(cursor.getColumnIndex(MESSAGE_TO)));
                        model.setMessageFrom(cursor.getString(cursor.getColumnIndex(MESSAGE_FROM)));
                        model.setYield((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYieldType((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        model.setDate((cursor.getString(cursor.getColumnIndex(DATE))));
                        model.setPlaceToSell((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setBidCostPerUnit((cursor.getString(cursor.getColumnIndex(BID_COST_PER_UNIT))));
                        model.setCostUnit((cursor.getString(cursor.getColumnIndex(COST_UNIT))));
                        model.setStatus((cursor.getString(cursor.getColumnIndex(STATUS))));
                        model.setQTYType((cursor.getString(cursor.getColumnIndex(QTY_TYPE))));
                        model.setStatusValue((cursor.getInt(cursor.getColumnIndex(STATUS_VALUE))));
                        model.setCostPerUnit((cursor.getString(cursor.getColumnIndex(COST_PER_UNIT))));
                        model.setImageName((cursor.getString(cursor.getColumnIndex(IMAGE_NAME))));
                        model.setImageArray((cursor.getBlob(cursor.getColumnIndex(IMAGE_DATA))));

                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getYieldInfoBasedOnLotID() " + e.getMessage());
            return null;
        }
    }
    public synchronized ArrayList<YieldListModel> getHistoryListForBuyer(String pUserId) {
        try {
            ArrayList<YieldListModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + MESSAGE_TO + "='" + pUserId + "' AND ( "
                        + STATUS_VALUE + "='" + YieldListModel.STATUS_ACCEPT_PROPOSAL + "' OR "
                        + STATUS_VALUE + "='" + YieldListModel.STATUS_REJECT_PROPOSAL + "' )";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {

                        // get the data into array, or class variable
                        YieldListModel model = new YieldListModel();
                        model.setLotnumber(cursor.getString(cursor.getColumnIndex(LOTID)));
                        model.setUserID(cursor.getString(cursor.getColumnIndex(USER_ID)));
                        model.setMessageTo(cursor.getString(cursor.getColumnIndex(MESSAGE_TO)));
                        model.setMessageFrom(cursor.getString(cursor.getColumnIndex(MESSAGE_FROM)));
                        model.setYield((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYieldType((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        model.setDate((cursor.getString(cursor.getColumnIndex(DATE))));
                        model.setPlaceToSell((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setBidCostPerUnit((cursor.getString(cursor.getColumnIndex(BID_COST_PER_UNIT))));
                        model.setCostUnit((cursor.getString(cursor.getColumnIndex(COST_UNIT))));
                        model.setStatus((cursor.getString(cursor.getColumnIndex(STATUS))));
                        model.setQTYType((cursor.getString(cursor.getColumnIndex(QTY_TYPE))));
                        model.setStatusValue((cursor.getInt(cursor.getColumnIndex(STATUS_VALUE))));
                        model.setCostPerUnit((cursor.getString(cursor.getColumnIndex(COST_PER_UNIT))));
                        model.setImageName((cursor.getString(cursor.getColumnIndex(IMAGE_NAME))));
                        model.setImageArray((cursor.getBlob(cursor.getColumnIndex(IMAGE_DATA))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getHistoryList() " + e.getMessage());
            return null;
        }
    }

    public synchronized ArrayList<YieldListModel> getActiveBidForBuyer(String pUserId) {
        try {
            ArrayList<YieldListModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + MESSAGE_FROM + "='" + pUserId + "' AND  "
                        + STATUS_VALUE + "='" + YieldListModel.STATUS_NOTIFY_TO_SELLER + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {

                        // get the data into array, or class variable
                        YieldListModel model = new YieldListModel();
                        model.setLotnumber(cursor.getString(cursor.getColumnIndex(LOTID)));
                        model.setUserID(cursor.getString(cursor.getColumnIndex(USER_ID)));
                        model.setMessageTo(cursor.getString(cursor.getColumnIndex(MESSAGE_TO)));
                        model.setMessageFrom(cursor.getString(cursor.getColumnIndex(MESSAGE_FROM)));
                        model.setYield((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYieldType((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        model.setDate((cursor.getString(cursor.getColumnIndex(DATE))));
                        model.setPlaceToSell((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setBidCostPerUnit((cursor.getString(cursor.getColumnIndex(BID_COST_PER_UNIT))));
                        model.setCostUnit((cursor.getString(cursor.getColumnIndex(COST_UNIT))));
                        model.setStatus((cursor.getString(cursor.getColumnIndex(STATUS))));
                        model.setQTYType((cursor.getString(cursor.getColumnIndex(QTY_TYPE))));
                        model.setStatusValue((cursor.getInt(cursor.getColumnIndex(STATUS_VALUE))));
                        model.setCostPerUnit((cursor.getString(cursor.getColumnIndex(COST_PER_UNIT))));
                        model.setImageName((cursor.getString(cursor.getColumnIndex(IMAGE_NAME))));
                        model.setImageArray((cursor.getBlob(cursor.getColumnIndex(IMAGE_DATA))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getActiveBidForBuyer() " + e.getMessage());
            return null;
        }
    }

    public synchronized YieldListModel getYieldInfoBasedOnLotID(String pLotId, String pUserID) {
        try {
            YieldListModel model = new YieldListModel();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + LOTID + "='" + pLotId + "' AND "
                        + USER_ID + "='" + pUserID + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        model.setLotnumber(cursor.getString(cursor.getColumnIndex(LOTID)));
                        model.setUserID(cursor.getString(cursor.getColumnIndex(USER_ID)));
                        model.setMessageTo(cursor.getString(cursor.getColumnIndex(MESSAGE_TO)));
                        model.setYield((cursor.getString(cursor.getColumnIndex(YIELD))));
                        model.setYieldType((cursor.getString(cursor.getColumnIndex(YIELD_TYPE))));
                        model.setQTY((cursor.getString(cursor.getColumnIndex(QTY))));
                        model.setDate((cursor.getString(cursor.getColumnIndex(DATE))));
                        model.setPlaceToSell((cursor.getString(cursor.getColumnIndex(PLACE_TO_SELL))));
                        model.setBidCostPerUnit((cursor.getString(cursor.getColumnIndex(BID_COST_PER_UNIT))));
                        model.setCostUnit((cursor.getString(cursor.getColumnIndex(COST_UNIT))));
                        model.setStatus((cursor.getString(cursor.getColumnIndex(STATUS))));
                        model.setQTYType((cursor.getString(cursor.getColumnIndex(QTY_TYPE))));
                        model.setStatusValue((cursor.getInt(cursor.getColumnIndex(STATUS_VALUE))));
                        model.setCostPerUnit((cursor.getString(cursor.getColumnIndex(COST_PER_UNIT))));
                        model.setImageName((cursor.getString(cursor.getColumnIndex(IMAGE_NAME))));
                        model.setImageArray((cursor.getBlob(cursor.getColumnIndex(IMAGE_DATA))));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return model;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getYieldInfoBasedOnLotID() " + e.getMessage());
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
