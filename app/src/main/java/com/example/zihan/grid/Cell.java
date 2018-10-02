package com.example.zihan.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class Cell extends FrameLayout {
    String TAG="Cell";
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
        lp.setMargins(10,10,0,0);
        addView(view, lp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(0xFF000000);     //画笔颜色
        paint.setStrokeWidth(5);        //画笔粗细
        int width = this.getWidth();
        int height = this.getHeight();
        //drawLine 参数为坐标（X开始，Y开始，X结束，Y结束，画笔）
        canvas.drawLine(1, height-1, width-1, height-1, paint);
        super.onDraw(canvas);
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
}
