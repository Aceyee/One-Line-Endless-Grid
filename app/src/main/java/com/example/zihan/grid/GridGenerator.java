package com.example.zihan.grid;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class GridGenerator {
    private String TAG="";
    public Cell [][] grid;
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
        while(true){
            grid=new Cell[height][width];
            map = new int[height][width];
            setMap();
            generate(context);
            if(checkNum()){
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
                break;
            }
        }
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
    }

    private void generate(Context context) {
        Random random = new Random();
        int randomI = random.nextInt(height);
        int randomJ = random.nextInt(width);
//        int randomI=0;
//        int randomJ=0;
        start = new Cell(context, randomI, randomJ);
        ArrayList<Node> arrayList;
        int curI = randomI;
        int curJ = randomJ;
        while(true) {
            map[curI][curJ]=0;
            Log.d(TAG, "generate2: "+curI +" "+curJ);
            updateAdjacent(curI, curJ);
            if((curI==1&&curJ==1)||
                    (curI==height-2&&curJ==1)||
                    (curI==1&&curJ==width-2)||
                    (curI==height-2&&curJ==width-2)){
                arrayList = getCornor(curI, curJ);
            }else {
                arrayList = getAdjacent(curI, curJ);
            }

            if(arrayList.size()==2){
                int i1= arrayList.get(0).i;
                int j1= arrayList.get(0).j;
                int i2= arrayList.get(1).i;
                int j2= arrayList.get(1).j;

                if(i1==i2){// && (j1+j2)/2=curJ
                    arrayList = leftRight(j1, j2, curI);
                }else if(j1==j2){ //&&(i1+i2)/2==curI
                    arrayList = TopBot(i1, i2, curJ);
                }
            }

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

    }

    private boolean checkNum() {
        int count = 0;
        double numGrid = (double) width * height;
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                if(map[i][j]==0) {
                    count++;
                }
                if(count >= numGrid*3/4){
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<Node> TopBot(int i1, int i2, int curJ) {
        ArrayList<Node> al= new ArrayList<>();
        int count1 =0;
        int count2 =0;
        for(int j=0; j<width; j++){
            if(map[i1][j]>0){
                count1++;
            }
        }
        for(int j=0; j<width; j++){
            if(map[i2][j]>0){
                count2++;
            }
        }
        if(count1>count2){//top has more space
            al.add(new Node(i1, curJ));
        }else if(count2>count1){
            al.add(new Node(i2, curJ));
        }else{
            al.add(new Node(i1, curJ));
            al.add(new Node(i2, curJ));
        }
        return al;
    }

    private ArrayList<Node> leftRight(int j1, int j2, int curI) {
        ArrayList<Node> al= new ArrayList<>();
        int count1 = 0;
        int count2 = 0;
        for(int i=0; i<height; i++){
            if(map[i][j1]>0){
                count1++;
            }
        }
        for(int i=0; i<height; i++){
            if(map[i][j2]>0){
                count2++;
            }
        }
        if(count1>count2){//left line has more space
            al.add(new Node(curI, j1));
        }else if(count1<count2){//right line has more space
            al.add(new Node(curI, j2));
        }else{
            al.add(new Node(curI, j1));
            al.add(new Node(curI, j2));
        }
        return al;
    }


    private ArrayList<Node> getCornor(int curI, int curJ) {
        ArrayList<Node> al;
        if(curI==1&&curJ==1){
            al = topLeft();
        }else if(curI==height-2&&curJ==1){
            al = botLeft();
        }else if(curI==1&&curJ==width-2){
            al = topRight();
        }else { //(curI==height-2&&curJ==width-2)
            al=botRight();
        }
        return al;
    }

    private ArrayList<Node> topLeft() {
        ArrayList<Node> sideList = new ArrayList<>();
        if(map[1][0]>0 &&map[0][1]>0) {
            int countV=0;//vertical
            int countH=0;//horizontal
            for (int i = 2; i < height; i++) {
                for (int j = 0; j < 2; j++) {
                    if(map[i][j]==0){
                        countV++;
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 2; j < width; j++) {
                    if(map[i][j]==0){
                        countH++;
                    }
                }
            }
            if(countV>countH){
                sideList.add(new Node(1,0));
                if(map[2][0]>0){
                    map[2][0]=-1;
                }
            }else if(countH>countV){
                sideList.add(new Node(0,1));
                if(map[0][2]>0){
                    map[0][2]=-1;
                }
            }else{//countV==countH
                sideList.add(new Node(1,0));
                sideList.add(new Node(0,1));
            }
        }else{
            sideList = getAdjacent(1, 1);
        }
        return sideList;
    }

    private ArrayList<Node> botLeft() {
        ArrayList<Node> sideList = new ArrayList<>();
        if (map[height-2][0] > 0 && map[height-1][1] > 0) {
            int countV = 0;//vertical
            int countH = 0;//horizontal
            for (int i = 0; i < height-2; i++) {
                for (int j = 0; j < 2; j++) {
                    if (map[i][j] == 0) {
                        countV++;
                    }
                }
            }
            for (int i = height-2; i < height; i++) {
                for (int j = 2; j < width; j++) {
                    if (map[i][j] == 0) {
                        countH++;
                    }
                }
            }
            if (countV > countH) {
                sideList.add(new Node(height-2, 0));
                if(map[height-3][0]>0){
                    map[height-3][0]=-1;
                }
            } else if (countH > countV) {
                sideList.add(new Node(height-1, 1));
                if(map[height-1][2]>0){
                    map[height-1][2]=-1;
                }
            } else {//countV==countH
                sideList.add(new Node(height-2, 0));
                sideList.add(new Node(height-1, 1));
            }
        } else {
            sideList = getAdjacent(height-2, 1);
        }
        return sideList;
    }

    private ArrayList<Node> topRight() {
        ArrayList<Node> sideList = new ArrayList<>();
        if(map[0][width-2]>0 &&map[1][width-1]>0) {
            int countV=0;//vertical
            int countH=0;//horizontal
            for (int i = 2; i < height; i++) {
                for (int j =width-2; j < width; j++) {
                    if(map[i][j]==0){
                        countV++;
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < width-2; j++) {
                    if(map[i][j]==0){
                        countH++;
                    }
                }
            }
            if(countV>countH){
                sideList.add(new Node(1,width-1));
                if(map[2][width-1]>0){
                    map[2][width-1]=-1;
                }
            }else if(countH>countV){
                sideList.add(new Node(0,width-2));
                if(map[0][width-3]>0){
                    map[0][width-3]=-1;
                }
            }else{//countV==countH
                sideList.add(new Node(1,width-1));
                sideList.add(new Node(0,width-2));
            }
        }else{
            sideList = getAdjacent(1, width-2);
        }
        return sideList;
    }

    private ArrayList<Node> botRight() {
        ArrayList<Node> sideList = new ArrayList<>();
        if(map[height-1][width-2]>0 &&map[height-2][width-1]>0) {
            int countV=0;//vertical
            int countH=0;//horizontal
            for (int i = 0; i < height-2; i++) {
                for (int j =width-2; j < width; j++) {
                    if(map[i][j]==0){
                        countV++;
                    }
                }
            }
            for (int i = height-2; i < height; i++) {
                for (int j = 0; j < width-2; j++) {
                    if(map[i][j]==0){
                        countH++;
                    }
                }
            }
            if(countV>countH){
                sideList.add(new Node(height-2,width-1));
                if(map[height-3][width-1]>0){
                    map[height-3][width-1]=-1;
                }
            }else if(countH>countV){
                sideList.add(new Node(height-1,width-2));
                if(map[height-1][width-3]>0){
                    map[height-1][width-3]=-1;
                }
            }else{//countV==countH
                sideList.add(new Node(height-2,width-1));
                sideList.add(new Node(height-1,width-2));
            }
        }else{
            sideList = getAdjacent(height-2, width-2);
        }
        return sideList;
    }

    private void updateAdjacent(int i, int j) {
        ArrayList<Node> al;
        al = getAdjacent(i, j);
        for(int n =0; n <al.size(); n++){
            Node node = al.get(n);
            if(isValidCell(node.i, node.j)){
                map[node.i][node.j]--;
                if(map[node.i][node.j]==0){//avoid to be 0, since 0 means path
                    map[node.i][node.j]--;
                }
            }
        }
    }

    private ArrayList<Node> getAdjacent(int i, int j){
        ArrayList<Node> arrayList = new ArrayList<>();
        //TOP
        if(isValidCell(i-1,j)){
            arrayList.add(new Node(i-1, j));
        }
        //LEFT
        if(isValidCell(i,j-1)){
            arrayList.add(new Node(i, j-1));
        }
        //RIGHT
        if(isValidCell(i,j+1)){
            arrayList.add(new Node(i, j+1));
        }
        //BOTTOM
        if(isValidCell(i+1,j)){
            arrayList.add(new Node(i+1, j));
        }
        return arrayList;
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

    private void generateDefault(Context context) {
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                Cell c = new Cell(context, i, j);
                grid[i][j] = c;
            }
        }
        start = new Cell(context, 0, 0);
    }
}
