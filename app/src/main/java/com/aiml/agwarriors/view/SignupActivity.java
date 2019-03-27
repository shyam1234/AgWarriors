package com.aiml.agwarriors.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aiml.agwarriors.IActivity;
import com.aiml.agwarriors.R;

public class SignupActivity extends BaseActivity implements IActivity, View.OnClickListener {


    private Button mButton_signup_sign_up;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_signup);
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        mButton_signup_sign_up = (Button) findViewById(R.id.button_signup_sign_up);
        mButton_signup_sign_up.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_signup_sign_up:
                break;
        }
    }
}
