package com.example.zihan.grid;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class Cell extends FrameLayout {

    private View view;

    public Cell(Context context) {
        super(context);

        view = new View(getContext());
        view.setBackgroundColor(0xffeee4da);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10,10,0,0);
        addView(view, lp);
    }

}
