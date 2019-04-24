package com.aiml.agwarriors.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.constant.Constant;
import com.aiml.agwarriors.database.TableUserInfo;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.TableUserInfoDataModel;
import com.aiml.agwarriors.utils.AppLog;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity implements IActivity {

    private EditText mEdititext_login_username;
    private EditText mEdititext_login_password;
    private Button mButton_login_log_in;
    private TextView mTextview_login_forgot;
    private TextView mTextview_login_signup;
    private RadioGroup mRadiogroup_login_holder;

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
        mRadiogroup_login_holder = (RadioGroup) findViewById(R.id.radiogroup_login_holder);
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
                doLogin();
                break;
            case R.id.textview_login_forgot:
                break;
            case R.id.textview_login_signup:
                navigateTo(LoginActivity.this, SignupActivity.class);
                break;
        }
    }

    private void doLogin() {
        RadioButton radioBtn = (RadioButton) findViewById(mRadiogroup_login_holder.getCheckedRadioButtonId());
        if(radioBtn != null && radioBtn.getText() != null ) {
            String type = radioBtn.getText().toString();
            ArrayList<TableUserInfoDataModel> list = isLoggedIn(type, mEdititext_login_username.getText().toString(), mEdititext_login_password.getText().toString());
            if (list != null && list.size() > 0) {
                Constant.USER_INFO_LIST = list;
                Toast.makeText(LoginActivity.this, list.get(0).getUSERNAME() + " has successfully logged in.", Toast.LENGTH_LONG).show();
                navigateTo(LoginActivity.this, MainActivity.class, true);
            } else {
                Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<TableUserInfoDataModel> isLoggedIn(String pType, String pUserName, String pPassword) {
        if (pType != null && pUserName != null && pPassword != null && !pUserName.isEmpty() && !pPassword.isEmpty()) {
            ArrayList<TableUserInfoDataModel> list = null;
            try {
                TableUserInfo table = new TableUserInfo();
                table.openDB(this);
                list = table.checkLogin(pType.toUpperCase() + "_" + pUserName.toUpperCase(),pPassword);
                table.closeDB();
                //--------------------------------------------
                if(list != null && list.size() > 0){
                    saveIntoSharePref(list.get(0));
                }
                return list;
            } catch (Exception e) {
                AppLog.errLog("SignUp", "isLoggedIn: "+ e.getMessage());
                return  null;
            }
        }
        return null;
    }

    private void saveIntoSharePref(TableUserInfoDataModel model) {
        SharedPreferences sharePref = getSharedPreferences(Constant.KEY_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor data = sharePref.edit();
        data.putString("id", model.getID());
        data.putString("pass", model.getPASS());
        data.commit();
    }
}
