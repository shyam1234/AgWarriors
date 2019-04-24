package com.aiml.agwarriors.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.constant.Constant;
import com.aiml.agwarriors.utils.CustomDialogbox;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public void navigateTo(Context pContext, Class pClass) {
        startActivity(new Intent(pContext, pClass));
    }

    public void navigateTo(Context pContext, Class pClass, boolean isPreviousActivityExit) {
        startActivity(new Intent(pContext, pClass));
        if (isPreviousActivityExit) {
            finish();
        }
    }

    public void navigateTo(Context pContext, Class pClass, Bundle pBundle, boolean isPreviousActivityExit) {
        Intent intent = new Intent(pContext, pClass);
        intent.putExtras(pBundle);
        startActivity(intent);
        if (isPreviousActivityExit) {
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.rel_header_notification:
                navigateTo(BaseActivity.this,NotificationActivity.class,false);
                break;
            case R.id.textview_loogout:
                popupLogoutAppAcknowledge();
                break;
        }
    }
    private void popupLogoutAppAcknowledge() {
        final CustomDialogbox dialogbox = new CustomDialogbox(this, CustomDialogbox.TYPE_YES_NO);
        dialogbox.setTitle("Do you want to logout?");
        dialogbox.show();
        dialogbox.getBtnYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLoginDetails();
                finish();
            }
        });
        dialogbox.getBtnNo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogbox.dismiss();
            }
        });
        dialogbox.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface mDialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mDialog.dismiss();
                }
                return true;
            }
        });
    }

    private void clearLoginDetails() {
        SharedPreferences sharePref = getSharedPreferences(Constant.KEY_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor data = sharePref.edit();
        data.putString("id", null);
        data.putString("pass", null);
        data.commit();
    }
}
