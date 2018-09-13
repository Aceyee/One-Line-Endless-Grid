package com.example.zihan.grid;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class Cell extends FrameLayout {
    String TAG="";
    public View view;
    public Rect rect;
    public int width;
    public int i;
    public int j;
    public boolean visited;


    public Cell(Context context) {
        super(context);
        init();
    }



    public Cell(Context context, int i, int j, int width) {
        super(context);

        this.i = i;
        this.j = j;
        this.width=width;
        init();
    }

    private void init() {
        this.visited=false;
        rect = new Rect();
        view = new View(getContext());
        view.setBackgroundColor(0xffeee4da);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10,10,0,0);
        addView(view, lp);
    }
}
