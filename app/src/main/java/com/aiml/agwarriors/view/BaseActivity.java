package com.aiml.agwarriors.view;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public void navigateTo(Context pContext, Class pClass) {
        startActivity(new Intent(pContext, pClass));
    }

    public void navigateTo(Context pContext, Class pClass, boolean isPreviousActivityExit) {
        startActivity(new Intent(pContext, pClass));
        if (isPreviousActivityExit) {
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
