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
    private boolean isTutorial;
    private int size;
    private GridGenerator gg;

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
        this.isTutorial = MainActivity.getIsTutorial();
        this.difficulty = MainActivity.getDifficulty();
        this.mGridLayout = new GridLayout(context);
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
        checkIsTutorial();
        addCellsToView();
    }

    private void checkIsTutorial() {
        if(isTutorial){
            this.difficulty = 3;
            this.size = this.difficulty;
            this.cells = new Cell[size][size];
            this.mGridLayout.setColumnCount(size);
            gg = new GridGenerator(getContext());
            isTutorial=false;
        }else {
            this.size = this.difficulty;
            this.cells = new Cell[size][size];
            this.mGridLayout.setColumnCount(size);
            gg = new GridGenerator(getContext(), size, size);
        }
    }

    private void addCellsToView() {
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                this.cells[i][j] = new Cell(mContext, gg.grid[i][j]);
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