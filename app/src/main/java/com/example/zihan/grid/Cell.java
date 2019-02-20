package com.example.zihan.grid;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class Cell extends FrameLayout {
    public View view;
    public int i;
    public int j;
    public boolean visited;
    public boolean block=false;

    public Cell(Context context) {
        super(context);
        init();
    }

    public Cell(Context context, Cell c) {
        super(context);
        init();
        this.i = c.i;
        this.j = c.j;
        this.visited = c.visited;
        this.block = c.block;
        if(block){
            backgroundColor();
        }
    }

    public Cell(Context context, int i, int j) {
        super(context);
        this.i = i;
        this.j = j;
        init();
    }

    private void init() {
//        setWillNotDraw(false);
        this.visited=false;
        view = new View(getContext());
        view.setBackgroundColor(getResources().getColor(R.color.cellColor));
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(5,5,0,0);
        addView(view, lp);
    }

    public void backgroundColor(){
        view.setBackgroundColor(0x00000000);
    }

    public boolean equals(Cell obj) {
        if(this.i==obj.i && this.j==obj.j){
            return true;
        }
        return false;
    }
        /*
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0,0,100,100, paint);
        setBackgroundColor(getResources().getColor(R.color.cellColor));
    }*/
}
