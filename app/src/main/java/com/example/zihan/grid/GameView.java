package com.example.zihan.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

public class GameView extends GridLayout {

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGameView();
    }
    public GameView(Context context) {
        super(context);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    private void initGameView(){
        setBackgroundColor(0xffbbadc0);
    }

}
