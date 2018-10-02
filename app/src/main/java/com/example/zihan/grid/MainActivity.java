package com.example.zihan.grid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    GameView gameView;
    GridLayout gridLayout;
    private TextView tvMapWidth;
    private static int width;
    private Context mcontext;
    private ImageButton btnWidthMinus;
    private ImageButton btnWidthPlus;
    private ImageButton btnPause;
    private int marginLeft;
    private int marginRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;
        endlessMode();
    }

    public void endlessMode() {
        Log.d("", "endless");
        setContentView(R.layout.activity_main);
        tvMapWidth = (TextView) findViewById(R.id.mapWidth);
        width = Integer.parseInt(tvMapWidth.getText().toString());
        btnWidthMinus = (ImageButton) findViewById(R.id.btnWidthMinus);
        btnWidthPlus = (ImageButton) findViewById(R.id.btnWidthPlus);
        gridLayout = findViewById(R.id.mapPreview);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) gridLayout.getLayoutParams();
        marginLeft = lp.leftMargin;
        marginRight = lp.rightMargin;
        setTvMapWidth();
    }

    private void showpopupWindow(View v) {
        Button btItem1, btItem2, btItem3;

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View view = layoutInflater.inflate(R.layout.modal_pause, null);

        final PopupWindow popupWindow = new PopupWindow(view, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);

        // PopupWindow弹出位置
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp);
    }

    public void Next(View view) {
        gameView.startGame();
    }

    public void start(View view) {
        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.gameView);
        /*
        btnPause = (ImageButton) findViewById(R.id.btnPause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showpopupWindow(v);// 显示PopupWindow
            }
        });*/
    }

    public static int getWidth() {
        return width;
    }

    private void setTvMapWidth() {
        if (width == 5) {
            btnWidthMinus.setEnabled(false);
        } else if (width == 12) {
            btnWidthPlus.setEnabled(false);
        } else {
            btnWidthMinus.setEnabled(true);
            btnWidthPlus.setEnabled(true);
        }
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = new Cell(mcontext, i, j);
                gridLayout.addView(cell, GetCellWidth(), GetCellWidth());
            }
        }
        tvMapWidth.setText(width + "");
    }

    public void widthMinus(View view) {
        width--;
        setTvMapWidth();
    }

    public void widthPlus(View view) {
        width++;
        setTvMapWidth();
    }

    private int GetCellWidth() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cellWidth;
        cellWidth = displayMetrics.widthPixels - marginLeft - marginRight;
        return (cellWidth - 10) / width;
    }

    public void hint(View view) {
        gameView.hint();
    }

    public void restart(View view) {
        gameView.restart();
    }

    public void returnMain(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
        dialog.setTitle("标题");
        dialog.setMessage("返回主菜单吗");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setContentView(R.layout.activity_main);
                endlessMode();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    public void tutorial(View view) {
        width=0;
        setContentView(R.layout.activity_tutorial);
    }
}
