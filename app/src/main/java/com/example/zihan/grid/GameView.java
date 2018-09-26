package com.example.zihan.grid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;

public class GameView extends GridLayout{
    Cell [][] cells;
    Cell startCell;
    AlertDialog.Builder dialog;
    Context context;
    int cellColor;
    int defaultColor;
    int selectedColor;
    public int numRows;
    public int numCols;
    private ArrayList<Cell> arrayList;
    private ArrayList<Cell> stack;
    String TAG="";

    public GameView(Context context) {
        super(context);
        initGame(context,null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGame(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGame(context, attrs);
    }

    private void initGame(Context context, AttributeSet attrs) {
        this.context =context;
        this.numRows=MainActivity.getWidth();
        this.numCols=MainActivity.getWidth();
        if(attrs==null) {
//            cellColor = 0xffeee4da;
//            defaultColor = 0xffbbadc0;
//            selectedColor = 0xffccc4da;
        }else{
            //Log.d(TAG, "initGame: "+attrs.getAttributeResourceValue());
            cellColor = getResources().getColor(R.color.cellColor);
            defaultColor = getResources().getColor(R.color.defaultColor);
            selectedColor = getResources().getColor(R.color.selectedColor);
        }
        setBackgroundGrid();
        startGame();
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
                            stack.add(cells[iindex][jindex]);
                            if(checkComplete()){
                                complete();
                            }
                            addAdjacent(iindex,jindex);
                        }else if(stack.contains(cells[iindex][jindex])){
                            for(int i=stack.size()-1; i>=0;i--){
                                if(stack.get(i).equals(cells[iindex][jindex])){
                                    break;
                                }else{
                                    cells[stack.get(i).i][stack.get(i).j].visited=false;
                                    cells[stack.get(i).i][stack.get(i).j].view.setBackgroundColor(cellColor);
                                    stack.remove(i);
                                }
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
                        if(arrayList.contains(cells[iindex][jindex])) {
                            cells[iindex][jindex].view.setBackgroundColor(selectedColor);
                            cells[iindex][jindex].visited = true;
                            stack.add(cells[iindex][jindex]);
                            if(checkComplete()){
                                complete();
                            }
                            addAdjacent(iindex,jindex);
                        }else if(stack.contains(cells[iindex][jindex])){
                            for(int i=stack.size()-1; i>=0;i--){
                                if(stack.get(i).equals(cells[iindex][jindex])){
                                    break;
                                }else{
                                    cells[stack.get(i).i][stack.get(i).j].visited=false;
                                    cells[stack.get(i).i][stack.get(i).j].view.setBackgroundColor(cellColor);
                                    stack.remove(i);
                                }
                            }
                            addAdjacent(iindex,jindex);
                        }
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

    private void complete() {
        Log.d(TAG, "onTouch: 过关！");
        dialog = new AlertDialog.Builder(context);
        dialog.setTitle("标题");
        dialog.setMessage("具体信息");
        dialog.setCancelable(false);
        dialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startGame();
            }
        });
        dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
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

    private boolean checkComplete() {
        for (int i=0; i<numRows;i++){
            for (int j=0; j<numCols; j++){
                if(!cells[i][j].block && !cells[i][j].visited ){
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
                if(!cells[i][j].visited && !cells[i][j].block) {
                    return true;
                }
            }
        }
        return false;
    }

    public void startGame() {
        removeAllViews();
        GridGenerator gg = new GridGenerator(getContext(), this.numRows, this.numCols);
        this.cells = gg.grid;
        this.startCell = gg.start;
        setColumnCount(cells[0].length);
        for(int i=0; i<cells.length; i++){
            for(int j=0; j<cells[0].length; j++){
                addView(cells[i][j], GetCellWidth(), GetCellWidth());
            }
        }
        stack=new ArrayList<>();
        stack.add(cells[startCell.i][startCell.j]);
        cells[startCell.i][startCell.j].view.setBackgroundColor(selectedColor);
        cells[startCell.i][startCell.j].visited=true;
        addAdjacent(startCell.i, startCell.j);
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
