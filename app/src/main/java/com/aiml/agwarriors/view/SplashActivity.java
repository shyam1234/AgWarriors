package com.aiml.agwarriors.view;

import android.os.Bundle;
import android.os.Handler;

import com.aiml.agwarriors.IActivity;
import com.aiml.agwarriors.R;

public class SplashActivity extends BaseActivity implements IActivity {

    private static final long DELAY_IN_MISEC = 4000;
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        initView();
    }


    @Override
    public void init() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                navigateTo(SplashActivity.this, LoginActivity.class, true);
            }
        };
        mHandler.postDelayed(mRunnable, DELAY_IN_MISEC);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
