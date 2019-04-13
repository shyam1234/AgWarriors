package com.aiml.agwarriors.view;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.interfaces.IActivity;

public class LoginActivity extends BaseActivity implements IActivity {

    private EditText mEdititext_login_username;
    private EditText mEdititext_login_password;
    private Button mButton_login_log_in;
    private TextView mTextview_login_forgot;
    private TextView mTextview_login_signup;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        init();
        initView();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        initHeader();
        mEdititext_login_username = findViewById(R.id.edititext_login_username);
        mEdititext_login_password = findViewById(R.id.edititext_login_password);
        mButton_login_log_in = findViewById(R.id.button_login_log_in);
        mTextview_login_forgot = findViewById(R.id.textview_login_forgot);
        mTextview_login_signup = findViewById(R.id.textview_login_signup);

        mButton_login_log_in.setOnClickListener(this);
        mTextview_login_forgot.setOnClickListener(this);
        mTextview_login_signup.setOnClickListener(this);
        mTextview_login_signup.setText(Html.fromHtml("<p>Don't have an account? <b><u>Sign up</u></b></p>"));
    }

    private void initHeader() {
        ImageView back = (ImageView) findViewById(R.id.imageview_back);
        TextView title = (TextView) findViewById(R.id.textview_title);
        ImageView notification = (ImageView) findViewById(R.id.imageview_header_notification);
        title.setText("Login");
        back.setOnClickListener(this);
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
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button_login_log_in:
                Toast.makeText(LoginActivity.this, "You have successfully login", Toast.LENGTH_LONG).show();
                navigateTo(LoginActivity.this, MainActivity.class, true);
                break;
            case R.id.textview_login_forgot:
                break;
            case R.id.textview_login_signup:
                navigateTo(LoginActivity.this, SignupActivity.class);
                break;
        }
    }
}
