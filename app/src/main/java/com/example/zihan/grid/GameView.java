package com.example.zihan.grid;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.GridLayout;

public class GameView extends GridLayout{

    public GameView(Context context) {
        super(context);
        initGame();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGame();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGame();
    }

    private void initGame() {
        setBackgroundGrid();
        setColumnCount(3);
        addView(new Cell(getContext()),GetCellWidth(), GetCellWidth());
    }
    private void setBackgroundGrid() {
        setBackgroundColor(0xffbbadc0);
    }
    private int GetCellWidth() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cellWidth;
        cellWidth = displayMetrics.widthPixels;
        return ( cellWidth - 10 ) / 3;
    }

}
