package com.aiml.agwarriors.model;

public class MainScreenModel {
    private String label;

    public MainScreenModel(String pStr) {
        label = pStr;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
