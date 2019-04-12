package com.aiml.agwarriors.application;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication instance;

    private MyApplication() {
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
