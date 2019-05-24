package com.aiml.agwarriors.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewItemDecorator extends RecyclerView.ItemDecoration {
    private int spaceInPixels;

    public RecyclerViewItemDecorator(int spaceInPixels) {
        this.spaceInPixels = spaceInPixels;
    }


    @Override

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,

                               RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);

        outRect.set(spaceInPixels, spaceInPixels, spaceInPixels, spaceInPixels);

    }

}
