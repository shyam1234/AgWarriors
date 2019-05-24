package com.aiml.agwarriors.model;

import android.graphics.drawable.Drawable;

public class MainScreenModel {
    private String label;
    private Drawable drawable;

    public MainScreenModel(String pStr, Drawable imageID) {
        label = pStr;
        drawable = imageID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable id) {
        this.drawable = id;
    }
}
