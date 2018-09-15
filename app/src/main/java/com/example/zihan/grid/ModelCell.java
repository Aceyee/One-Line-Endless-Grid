package com.example.zihan.grid;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

public class ModelCell extends FrameLayout {
    int type;
    String []types = {"topLeft",
    "topDown",
    "topRight",
    "leftTop",
    "leftDown",
    "leftRight",
    "DownTop",
    "DownLeft",
    "DownRight",
    "rightTop",
    "rightLeft",
    "rightDown"
    };
    /*
    int topLeft = 0;
    int topDown = 1;
    int topRight =2;
    int leftTop = 3;
    int leftDown =4;
    int leftRight=5;
    int DownTop = 6;
    int DownLeft=7;
    int DownRight=8;
    int rightTop=9;
    int rightLeft=10;
    int rightDown=11;*/

    String TAG="";
    public TextView view;

    public int i;
    public int j;
    public boolean visited;

    public ModelCell(Context context) {
        super(context);
        init();
    }

    public ModelCell(Context context, int i, int j) {
        super(context);
        this.i = i;
        this.j = j;
        init();
    }

    private void init() {
        Random random = new Random();
        int i = random.nextInt(12);
        view = new TextView(getContext());
        view.setText(types[i]);

        view.setBackgroundColor(getResources().getColor(R.color.cellColor));
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10,10,0,0);
        addView(view, lp);
    }

    /*
    public boolean equals(ModelCell obj) {
        if(this.i==obj.i && this.j==obj.j){
            return true;
        }
        return false;
    }*/
}
