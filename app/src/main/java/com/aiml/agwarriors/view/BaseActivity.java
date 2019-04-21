package com.aiml.agwarriors.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aiml.agwarriors.R;

import androidx.appcompat.app.AppCompatActivity;

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
        }
    }
}
