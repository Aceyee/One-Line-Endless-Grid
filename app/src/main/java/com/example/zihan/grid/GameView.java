package com.example.zihan.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import java.util.ArrayList;

public class GameView extends FrameLayout{
    Cell [][] cells;
    boolean[][] hintCells;
    Cell startCell;
    private CustomDialog.Builder builder;
    GridLayout gridLayout;
    private CustomDialog mDialog;
    Context context;
    int cellColor;
    int defaultColor;
    int selectedColor;
    int hintColor;
    int colorLine;
    int transparent;
    public int numRows;
    public int numCols;
    private ArrayList<Cell> arrayList;
    private ArrayList<Cell> stack;
    ArrayList<Cell> hintArrayList;
    public ArrayList<Node> track;
    public int hintCount=0;
    private boolean isTutorial=false;

    GridGenerator gg;
    LineView lineView;
    LineView hintLineView;
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
        gridLayout = new GridLayout(context);
        if(numRows==3 && numCols==3){
            isTutorial=true;
        }
        if(attrs==null) {
            cellColor = 0xffeee4da;
            defaultColor = 0xffbbadc0;
            selectedColor = 0xffccc4da;
        }else{
            //Log.d(TAG, "initGame: "+attrs.getAttributeResourceValue());
            cellColor = getResources().getColor(R.color.cellColor);
            defaultColor = getResources().getColor(R.color.defaultColor);
            selectedColor = getResources().getColor(R.color.colorBlue);
            hintColor = getResources().getColor(R.color.hintColor);
            transparent = getResources().getColor(R.color.transparent);
            colorLine = getResources().getColor(R.color.colorLine);
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
                        }
                        else{
                            int i=stack.size()-2;
                            if(i>=0 && stack.get(i).equals(cells[iindex][jindex])){
                                cells[stack.get(i+1).i][stack.get(i+1).j].visited=false;
                                cells[stack.get(i+1).i][stack.get(i+1).j].view.setBackgroundColor(cellColor);
                                stack.remove(i+1);
                                addAdjacent(stack.get(i).i,stack.get(i).j);
                            }
                        }
                        /*
                        else if(stack.contains(cells[iindex][jindex])){
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
                        }*/
                        if(lineView!=null){
                            removeView(lineView);
                        }
                        lineView = new LineView(context, stack, GetCellWidth(), colorLine);
                        addView(lineView, getWidth(),getHeight());
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }

    private void complete() {
        mDialog = MainActivity.mDialog;
        mDialog.show();
    }
    private void showTwoButtonDialog(String alertText, String confirmText, String cancelText, View.OnClickListener conFirmListener, View.OnClickListener cancelListener) {
        mDialog = builder.setMessage(alertText)
                .setPositiveButton(confirmText, conFirmListener)
                .setNegativeButton(cancelText, cancelListener)
                .createTwoButtonDialog();
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
                }
            }
        }
        return true;
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

        if(gridLayout!=null) {
//            removeView(gridLayout);
//            removeView(lineView);
            gridLayout.removeAllViews();
            this.removeAllViews();
        }
        if(isTutorial){
            gg = new GridGenerator(getContext());
            isTutorial=false;
        }else {
            gg = new GridGenerator(getContext(), this.numRows, this.numCols);
        }
        this.track = new ArrayList<>();
        this.cells = new Cell[numRows][numCols];
        this.hintCells = new boolean[numRows][numCols];
        for(int i=0; i<gg.track.size(); i++){
            Node n = gg.track.get(i);
            this.track.add(new Node(n.i, n.j));
        }
        for(int i=0; i<numRows; i++){
            for(int j=0; j<numCols; j++){
                this.cells[i][j] = new Cell(context, gg.grid[i][j]);
            }
        }
        this.startCell = gg.start;
        gridLayout.setColumnCount(numCols);
        for (Cell[] cell : cells) {
            for (int j = 0; j < cells[0].length; j++) {
                gridLayout.addView(cell[j], GetCellWidth(), GetCellWidth());
            }
        }
        stack=new ArrayList<>();
        hintArrayList = new ArrayList<>();
        stack.add(cells[startCell.i][startCell.j]);
        hintArrayList.add(cells[startCell.i][startCell.j]);
        cells[startCell.i][startCell.j].view.setBackgroundColor(selectedColor);
        cells[startCell.i][startCell.j].visited=true;
        addAdjacent(startCell.i, startCell.j);
        addView(gridLayout);
        getGesture();
    }

    private void setBackgroundGrid() {
        setBackgroundColor(transparent);
    }

    private int GetCellWidth() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cellWidth;
        cellWidth = displayMetrics.widthPixels;
        return ( cellWidth - 10 ) / cells[0].length;
    }

    public void restart(){
        if(gridLayout!=null) {
            gridLayout.removeAllViews();
            this.removeAllViews();
        }

        for(int i=0; i<numRows; i++){
            for(int j=0; j<numCols; j++){
                this.cells[i][j] = new Cell(context, gg.grid[i][j]);
            }
        }
        for (Cell[] cell : cells) {
            for (int j = 0; j < cells[0].length; j++) {
                gridLayout.addView(cell[j], GetCellWidth(), GetCellWidth());
            }
        }

        stack=new ArrayList<>();
        stack.add(cells[startCell.i][startCell.j]);
        cells[startCell.i][startCell.j].view.setBackgroundColor(selectedColor);
        cells[startCell.i][startCell.j].visited=true;
        addView(gridLayout);
        hintLineView = new LineView(context, hintArrayList, GetCellWidth(), hintColor);
        this.addView(hintLineView, getWidth(), getHeight());
        addAdjacent(startCell.i, startCell.j);
    }

    public int hint(int numHint) {
        //restarty有问题
        if(track.size()==0){
            return numHint;
        }
        if(stack.size()>0){
            restart();
        }
        int count =0;

        while(count<6){
            if(track.size()>0) {
                Node n = track.get(0);
                hintCells[n.i][n.j] =true;
                track.remove(0);
                hintArrayList.add(cells[n.i][n.j]);
                hintCount++;
            }
            count++;
        }
        if(hintLineView!=null){
            this.removeView(hintLineView);
        }
        hintLineView = new LineView(context, hintArrayList, GetCellWidth(), hintColor);
        this.addView(hintLineView, getWidth(), getHeight());
        numHint--;
        return numHint;
    }
}