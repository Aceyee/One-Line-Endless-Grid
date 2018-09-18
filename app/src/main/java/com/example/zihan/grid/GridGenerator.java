package com.example.zihan.grid;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class GridGenerator {
    private String TAG="";
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
        setMap();
        generate2(context);
    }

    private void setMap() {
        for(int i=0; i<height; i++){
            for(int j=0; j<width;j++){
                if(i==0 || i==height-1){
                    map[i][j]=3;
                }else if(j==0 || j==width-1){
                    map[i][j]=3;
                }else{
                    map[i][j]=4;
                }
            }
        }
        map[0][0]=2;
        map[0][width-1]=2;
        map[height-1][0]=2;
        map[height-1][width-1]=2;
        for(int i=0; i<height; i++){
            for(int j=0; j<width;j++){
//                System.out.print(map[i][j]+" ");
            }
//            System.out.println();
        }
    }

    private void generate2(Context context) {
        start = new Cell(context, 0, 0);
        Random random = new Random();
        int curI = 0;
        int curJ = 0;
        while(true) {
            map[curI][curJ]=0;
            Log.d(TAG, "generate2: "+curI +" "+curJ);
            updateAdjacent(curI, curJ);
            getAdjacent(curI, curJ);
            if(arrayList.size()==0){
                break;
            }else{
                int index= random.nextInt(arrayList.size());
                curI = arrayList.get(index).i;
                curJ = arrayList.get(index).j;
                /*
                while(map[curI][curJ]<=0){
                    arrayList.remove(index);
                    index= random.nextInt(arrayList.size());
                    curI = arrayList.get(index).i;
                    curJ = arrayList.get(index).j;
                }*/
            }
        }
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                Cell c = new Cell(context, i, j);
                if(map[i][j]==0) {
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

    private void updateAdjacent(int i, int j) {
        /*
        getAdjacent(i, j);
        //copy of arraylist
        ArrayList<Node> arrayList2 = new ArrayList<>();
        for(int n=0; n<arrayList.size();n++){
            arrayList2.add(arrayList.get(n));
        }
        //for each elemnt in arrayList2, find adjacent
        for(int n=0; n<arrayList2.size();n++){
            Node node = arrayList2.get(n);
            getAdjacent(node.i, node.j);
            for(int m=0; m<arrayList.size();m++){

            }
        }*/
        if(isValidCell(i-1,j)){
            map[i-1][j]--;
            if(map[i-1][j]==0){
                map[i-1][j]--;
            }
        }
        if(isValidCell(i,j-1)){
            map[i][j-1]--;
            if(map[i][j-1]==0){
                map[i][j-1]--;
            }
        }
        if(isValidCell(i,j+1)){
            map[i][j+1]--;
            if(map[i][j+1]==0){
                map[i][j+1]--;
            }
        }
        if(isValidCell(i+1,j)){
            map[i+1][j]--;
            if(map[i+1][j]==0){
                map[i+1][j]--;
            }
        }
    }

    private void getAdjacent(int i, int j){
        arrayList = new ArrayList<>();
        if(isValidCell(i-1,j)){
//            map[i-1][j]--;
            arrayList.add(new Node(i-1, j));
        }
        if(isValidCell(i,j-1)){
//            map[i][j-1]--;
            arrayList.add(new Node(i, j-1));
        }
        if(isValidCell(i,j+1)){
//            map[i][j+1]--;
            arrayList.add(new Node(i, j+1));
        }
        if(isValidCell(i+1,j)){
//            map[i+1][j]--;
            arrayList.add(new Node(i+1, j));
        }
    }
    private boolean isValidCell(int i, int j){
        if(i>=0 && i<height){
            if(j>=0 && j<width){
                if(map[i][j]>0) {
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
