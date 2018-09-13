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

import java.util.ArrayList;

public class GameView extends GridLayout{
    Cell [][] cells = new Cell[3][4];
    Cell currentCell;
    int defaultColor = 0xffbbadc0;
    int selectedColor = 0xffccc4da;
    private int numRows;
    private int numCols;
    private ArrayList<Cell> arrayList;
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
    private int getIndexI(float n){
        int cellWidth = GetCellWidth();
        int index=0;
        for(int i=0; i<numRows; i++){
            if(n>i*cellWidth){
                index = i;
            }else{
                break;
            }
        }
        return index;
    }
    private int getIndexJ(float n){
        int cellWidth = GetCellWidth();
        int index=0;
        for(int i=0; i<numCols; i++){
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
                        jindex= getIndexJ(startX);
                        iindex= getIndexI(startY);
                        if(arrayList.contains(cells[iindex][jindex])) {
                            cells[iindex][jindex].view.setBackgroundColor(selectedColor);
                            cells[iindex][jindex].visited = true;
                            if(checkComplete()){
                                Log.d(TAG, "onTouch: 过关！");
                            }
                            addAdjacent(iindex,jindex);
                        }
//                        Log.d(TAG, "onClick: "+startX+" "+startY);
//                        Log.d(TAG, "onClick: "+iindex+" "+jindex);
//                        Log.d(TAG, "onClick: "+v.getTop()+" "+v.getLeft()+" "+v.getRight()+" "+v.getBottom());
                        break;

                    case MotionEvent.ACTION_MOVE:
                        offsetX = event.getX();
                        offsetY = event.getY();
                        jindex= getIndexJ(offsetX);
                        iindex= getIndexI(offsetY);

                        //cells[iindex][jindex].view.setBackgroundColor(selectedColor);
                        //cells[iindex][jindex].visited = true;
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

    private boolean checkComplete() {
        for (int i=0; i<numRows;i++){
            for (int j=0; j<numCols; j++){
                if(!cells[i][j].visited){
                    return false;
//                    Log.d(TAG, "checkComplete: false");
                }
            }
        }
        return true;
//        Log.d(TAG, "checkComplete: true");
    }

    private void addAdjacent(int i, int j){
        arrayList = new ArrayList<>();
        if(isValidCell(i-1,j)){
            arrayList.add(cells[i-1][j]);
        }
        if(isValidCell(i,j-1)){
            arrayList.add(cells[i][j-1]);
        }
        if(isValidCell(i,j+1)){
            arrayList.add(cells[i][j+1]);
        }
        if(isValidCell(i+1,j)){
            arrayList.add(cells[i+1][j]);
        }
    }
    private boolean isValidCell(int i, int j){
        if(i>=0 && i<numRows){
            if(j>=0 && j<numCols){
                if(!cells[i][j].visited) {
                    return true;
                }
            }
        }
        return false;
    }
    private void startGame() {
        numRows=3;
        numCols=4;
        cells[0][0].view.setBackgroundColor(selectedColor);
        cells[2][3].view.setBackgroundColor(selectedColor);
        cells[0][0].visited=true;
        cells[2][3].visited=true;
        currentCell = cells[0][0];
        addAdjacent(currentCell.i, currentCell.j);
        /*
        for(int i=0; i<arrayList.size();i++) {
            Log.d(TAG, "startGame: " + arrayList.get(i).i+" "+arrayList.get(i).j);
        }*/
        //Log.d("", "startGame: "+cells[1][1].rect.left);
        getGesture();
    }

    private void setBackgroundGrid() {
        setBackgroundColor(defaultColor);
    }
    private int GetCellWidth() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cellWidth;
        cellWidth = displayMetrics.widthPixels;
        return ( cellWidth - 10 ) / cells[0].length;
    }
}
