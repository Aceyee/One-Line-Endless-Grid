package com.example.zihan.grid;

import android.content.Context;

public class GridGenerator {
    public Cell [][] grid;
    public ModelCell[][]modelCells;
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
        modelCells = new ModelCell[height][width];
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                ModelCell mc = new ModelCell(context, i, j);
                modelCells[i][j]=mc;
                Cell c = new Cell(context, i, j);
                grid[i][j] = c;
            }
        }
        start = new Cell(context, 0, 0);
    }
}
