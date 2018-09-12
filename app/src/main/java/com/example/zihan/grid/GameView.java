package com.example.zihan.grid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View{
    private static final int SQUARE_SIZE_DEF = 200;
    private Rect mRectSquare;
    private Paint mPaintSquare;
    private Paint mPaintCircle;
    private int mSquareColor;
    private int mSquareSize;

    private float mCircleX, mCircleY;
    private float mCircleRadius = 100f;
    private Bitmap mImage;

    public GameView(Context context) {
        super(context);

        init(null);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

//    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//
//        init(attrs);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(@Nullable AttributeSet set){
        mRectSquare = new Rect();
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(Color.parseColor("#00ccff"));

        mImage = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                mImage = getResizedBitmap(mImage, getWidth(), getHeight());

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        int newWidth = mImage.getWidth() -50;
                        int newHeight = mImage.getHeight() -50;
                        if(newWidth<=0||newHeight<=0){
                            cancel();
                            return;
                        }
                        mImage = getResizedBitmap(mImage,newWidth, newHeight);
                        postInvalidate();
                    }
                },2000l, 500l);
            }
        });

        if(set==null){
            return;
        }
        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.GameView);
        mSquareColor = ta.getColor(R.styleable.GameView_square_color, Color.GREEN);
        mSquareSize = ta.getDimensionPixelSize(R.styleable.GameView_square_size, SQUARE_SIZE_DEF);
        mPaintSquare.setColor(mSquareColor);
        ta.recycle();
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
        Matrix matrix = new Matrix();

        RectF src = new RectF(0,0,bitmap.getWidth(), bitmap.getHeight());
        RectF dst = new RectF(0,0, reqWidth, reqHeight);
        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public void swapColor(){
        mPaintSquare.setColor(mPaintSquare.getColor()==mSquareColor ? Color.RED : mSquareColor);
        postInvalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
//        canvas.drawColor(Color.RED);
        mRectSquare.left=50;
        mRectSquare.top=50;
        mRectSquare.right=mRectSquare.left+mSquareSize;
        mRectSquare.bottom=mRectSquare.top+mSquareSize;

        canvas.drawRect(mRectSquare, mPaintSquare);

        if(mCircleX==0f || mCircleY==0f){
            mCircleX = getWidth()/2;
            mCircleY = getHeight()/2;
        }
        canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, mPaintCircle);

        float imageX = (getWidth()-mImage.getWidth())/2;
        float imageY = (getHeight()-mImage.getHeight())/2;
        canvas.drawBitmap(mImage, imageX,imageY, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                float x = event.getX();
                float y = event.getY();

                if(mRectSquare.left<x && mRectSquare.right>x){
                    if(mRectSquare.top<y && mRectSquare.bottom>y){
                        mCircleRadius +=10f;
                        postInvalidate();
                    }
                }
                return true;
            }
            case MotionEvent.ACTION_MOVE:{
                float x = event.getX();
                float y = event.getY();

                double dx = Math.pow(x-mCircleX,2);
                double dy = Math.pow(y-mCircleY,2);

                if(dx+dy<Math.pow(mCircleRadius,2)){
                    //Touched
                    mCircleX = x;
                    mCircleY = y;

                    postInvalidate();
                    return true;
                }
            }
        }
        return value;
    }
}
