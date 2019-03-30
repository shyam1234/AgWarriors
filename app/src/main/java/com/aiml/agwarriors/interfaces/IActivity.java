package com.aiml.agwarriors.interfaces;

import android.os.Bundle;

public interface IActivity {
    void onCreate(Bundle bundle);

    void init();

    void initView();

    void onStart();

    void onStop();

    void onDestroy();
}