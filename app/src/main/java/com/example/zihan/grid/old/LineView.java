package com.example.zihan.grid.old;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.zihan.grid.Cell;

import java.util.ArrayList;

public class LineView extends View{
    Paint paint;
    String TAG = "LineView";
    ArrayList<Cell> arrayList;
    float width;
    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public LineView(Context context, ArrayList<Cell> stack, int width, int color) {
        super(context);
        arrayList = stack;
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(10);
        paint.setStrokeCap( Paint.Cap.ROUND );
        this.width = 0.5f * width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0; i<arrayList.size()-1;i++){
            Cell c1 =arrayList.get(i);
            Cell c2 = arrayList.get(i+1);
            canvas.drawLine(c1.getX()+width,c1.getY()+width,c2.getX()+width,c2.getY()+width, paint);
        }
    }
}
