package com.aiml.agwarriors;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends BaseActivity implements IActivity {


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        init();
        initView();
    }

    @Override
    public void init() {

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
