package com.aiml.agwarriors.view;

import android.os.Bundle;

import com.aiml.agwarriors.IActivity;
import com.aiml.agwarriors.R;

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
