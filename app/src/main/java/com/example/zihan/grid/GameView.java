package com.example.zihan.grid;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.GridLayout;

public class GameView extends FrameLayout {
    private GridLayout mGridLayout;
    private Cell[][] cells;
    private Context mContext;
    private int difficulty;
    private int cellWidth;

    public GameView(@NonNull Context context) {
        super(context);
        initialize(context);
    }

    public GameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public GameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    /**
     * Initialize variables
     * @param context
     */
    private void initialize(Context context) {
        this.mContext = context;
        this.cellWidth = MainActivity.getCellWidth();
        this.difficulty = MainActivity.getDifficulty();
        int size = this.difficulty;
        this.cells = new Cell[size][size];
        this.mGridLayout = new GridLayout(context);
        this.mGridLayout.setColumnCount(size);
        setBackgroundGrid();
        startGame();
    }

    public int getDifficulty(){
        return this.difficulty;
    }
    private void setBackgroundGrid() {
        setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * Start game!
     */
    private void startGame(){
        if(mGridLayout!=null) {
            mGridLayout.removeAllViews();
            this.removeAllViews();
        }
        int size = this.difficulty;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                this.cells[i][j] = new Cell(mContext);
            }
        }
        for(int i=0; i<cells.length; i++){
            for(int j=0; j<cells[0].length; j++){
                mGridLayout.addView(cells[i][j], this.cellWidth, this.cellWidth);
            }
        }
        addView(mGridLayout);
    }
}