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
        getGesture();
        for(int i=0; i<cells.length; i++){
            for(int j=0; j<cells[0].length; j++){
                Cell c = new Cell(getContext());
                cells[i][j] = c;
                addView(c,GetCellWidth(), GetCellWidth());
            }
        }
        startGame();
    }
    private void getGesture(){
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if(Math.abs(offsetX)>Math.abs(offsetY)){//horizontal
                            if (offsetX<-5){//left
                            }else if(offsetX>5){//rught
                            }
                        }else{//vertical
                            if (offsetY<-5){//up
                            }else if(offsetY>5){//down
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }
    private void startGame() {
        cells[0][0].view.setBackgroundColor(0xffccc4da);
        cells[2][3].view.setBackgroundColor(0xffccc4da);

        //        setStartCell();
//        setEndCell();
    }


    private void setStartCell() {

    }

    private void setEndCell() {
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
