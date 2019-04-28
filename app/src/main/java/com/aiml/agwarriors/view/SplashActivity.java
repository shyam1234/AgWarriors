package com.aiml.agwarriors.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.constant.Constant;
import com.aiml.agwarriors.database.DatabaseHelper;
import com.aiml.agwarriors.database.TableUserInfo;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.TableUserInfoDataModel;
import com.aiml.agwarriors.utils.AppLog;

import java.util.ArrayList;

public class SplashActivity extends BaseActivity implements IActivity {

    private static final long DELAY_IN_MISEC = 4000;
    private Handler mHandler;
    private Runnable mRunnable;
    private Runnable mRunnable1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        initView();
    }


    @Override
    public void init() {
        initDB();
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                navigateTo(SplashActivity.this, LoginActivity.class, true);
            }
        };

        mRunnable1 = new Runnable() {
            @Override
            public void run() {
                navigateTo(SplashActivity.this, MainActivity.class, true);
            }
        };
        getLoginDetail();
    }

    private void initDB() {
        //needUpgrade forcefully call to onUpgrade method to check is db need to upgrade.
        DatabaseHelper.getInstance(this);

        //DatabaseHelper.getInstance(getApplicationContext()).getReadableDatabase().needUpgrade(DatabaseHelper.DB_VERSION);

    }

    private void getLoginDetail() {
        SharedPreferences sharePref = getSharedPreferences(Constant.KEY_SHARED_PREF, Context.MODE_PRIVATE);
        if (sharePref != null) {
            String id = sharePref.getString("id", null);
            String pass = sharePref.getString("pass", null);
            try {
                if (id != null && pass != null) {
                    TableUserInfo table = new TableUserInfo();
                    table.openDB(this);
                    ArrayList<TableUserInfoDataModel> list = table.checkLogin(id, pass);
                    table.closeDB();
                    //--------------------------------------------
                    if (list == null || list.size() == 0) {
                        mHandler.postDelayed(mRunnable, DELAY_IN_MISEC);
                    } else {
                        Constant.USER_INFO_LIST = list;
                        mHandler.postDelayed(mRunnable1, DELAY_IN_MISEC);
                    }
                } else {
                    mHandler.postDelayed(mRunnable, DELAY_IN_MISEC);
                }
            } catch (Exception e) {
                AppLog.errLog("SignUp", "isLoggedIn: " + e.getMessage());
                mHandler.postDelayed(mRunnable, DELAY_IN_MISEC);
            }
        }else{
            mHandler.postDelayed(mRunnable, DELAY_IN_MISEC);
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mRunnable);
        mHandler.removeCallbacks(mRunnable1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
