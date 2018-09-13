package com.example.zihan.grid;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout{
    Cell [][] cells = new Cell[3][4];
    Cell currentCell;
    String TAG="";
    private int maxIndex=10;

    public GameView(Context context) {
        super(context);
        initGame(null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGame(attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGame(attrs);
    }

    private void initGame(AttributeSet attrs) {
        setBackgroundGrid();
        if(attrs==null) {
        }else{
        }
        setColumnCount(cells[0].length);
        for(int i=0; i<cells.length; i++){
            for(int j=0; j<cells[0].length; j++){
                Cell c = new Cell(getContext(), i, j, GetCellWidth());
                cells[i][j] = c;
                addView(c, GetCellWidth(), GetCellWidth());
            }
        }
        startGame();
    }
    private int getIndex(float n){
        int cellWidth = GetCellWidth();
        int index=0;
        for(int i=0; i<maxIndex; i++){
            if(n>i*cellWidth){
                index = i;
            }else{
                break;
            }
        }
        return index;
    }
    private void getGesture(){
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;
            int jindex;
            int iindex;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        jindex= getIndex(startX);
                        iindex= getIndex(startY);
//                        Log.d(TAG, "onClick: "+startX+" "+startY);
//                        Log.d(TAG, "onClick: "+iindex+" "+jindex);
//                        Log.d(TAG, "onClick: "+v.getTop()+" "+v.getLeft()+" "+v.getRight()+" "+v.getBottom());
                        break;

                    case MotionEvent.ACTION_MOVE:
                        offsetX = event.getX();
                        offsetY = event.getY();
                        jindex= getIndex(offsetX);
                        iindex= getIndex(offsetY);
                        cells[iindex][jindex].view.setBackgroundColor(0xffccc4da);
//                        Log.d(TAG, "onClick: "+startX+" "+startY);
//                        Log.d(TAG, "onClick: "+iindex+" "+jindex);
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;
            }
        });
    }
    private void startGame() {
        cells[0][0].view.setBackgroundColor(0xffccc4da);
        cells[2][3].view.setBackgroundColor(0xffccc4da);
        currentCell = cells[0][0];
        //Log.d("", "startGame: "+cells[1][1].rect.left);
        getGesture();
    }

    private void setBackgroundGrid() {
        setBackgroundColor(0xffbbadc0);
    }
    private int GetCellWidth() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cellWidth;
        cellWidth = displayMetrics.widthPixels;
        return ( cellWidth - 10 ) / cells[0].length;
    }
}
