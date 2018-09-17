package com.example.zihan.grid;

import android.content.Context;
import java.util.ArrayList;
import java.util.Random;

public class GridGenerator {
    public Cell [][] grid;
    public ArrayList<Node> arrayList;
    public int [][] map;
    public Cell start;
    public int width;
    public int height;

    public GridGenerator(Context context){
        grid=new Cell[3][4];
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                Cell c = new Cell(context, i, j);
                grid[i][j] = c;
            }
        }
        start = new Cell(context, 0, 0);
    }

    public GridGenerator(Context context, int height, int width){
        this.height=height;
        this.width=width;
        grid=new Cell[height][width];
        map = new int[height][width];
        arrayList =new ArrayList<>();
        generate2(context);
    }

    private void generate2(Context context) {
        start = new Cell(context, 0, 0);
        Random random = new Random();
        int curI = 0;
        int curJ = 0;
        while(true) {
            map[curI][curJ]=1;
            addAdjacent(curI, curJ);
            if(arrayList.size()==0){
                break;
            }else{
                int index= random.nextInt(arrayList.size());
                curI = arrayList.get(index).i;
                curJ = arrayList.get(index).j;
            }
        }
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                Cell c = new Cell(context, i, j);
                if(map[i][j]==1) {
                    grid[i][j] = c;
                }else{
                    c.block=true;
                    c.backgroundColor();
                    grid[i][j] = c;
                }
            }
        }
        start = new Cell(context, 0, 0);
    }
    private void addAdjacent(int i, int j){
        arrayList = new ArrayList<>();
        if(isValidCell(i-1,j)){
            arrayList.add(new Node(i-1, j));
        }
        if(isValidCell(i,j-1)){
            arrayList.add(new Node(i, j-1));
        }
        if(isValidCell(i,j+1)){
            arrayList.add(new Node(i, j+1));
        }
        if(isValidCell(i+1,j)){
            arrayList.add(new Node(i+1, j));
        }
    }
    private boolean isValidCell(int i, int j){
        if(i>=0 && i<height){
            if(j>=0 && j<width){
                if(map[i][j]==0) {
                    return true;
                }
            }
        }
        return false;
    }
    private void generate(Context context) {
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                Cell c = new Cell(context, i, j);
                grid[i][j] = c;
            }
        }
        start = new Cell(context, 0, 0);
    }
}
