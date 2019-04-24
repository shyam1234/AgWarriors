package com.aiml.agwarriors.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aiml.agwarriors.R;

/**
 * Created by Admin on 26-11-2016.
 * a. Create table in database package
 * b. Create tableDataModel in model package
 * c. Parse table in ParseResponse *
 * We can access same parse model by ModelFactory
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    private static DatabaseHelper mInstance;

    private DatabaseHelper(Context context) {
        super(context, context.getString(R.string.app_name) + "_db", null, DB_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL(TableUserInfo.CREATE_TABLE);
        db.execSQL(TableYield.CREATE_TABLE);
        db.execSQL(TableNotification.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        //modify table if version changes
        if (old_version < new_version) {
            db.execSQL(TableUserInfo.DROP_TABLE);
            db.execSQL(TableYield.DROP_TABLE);
            db.execSQL(TableNotification.DROP_TABLE);
            onCreate(db);
            //UserInfo.clearUSerInfo();
            //SharedPreferencesApp.getInstance().removeAllAlongWithLang();
        }
    }
}
