package com.example.zihan.grid;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    GameView gameView;
    GridLayout gridLayout;
    LinearLayout linearLayout;
    private TextView tvMapWidth;
    private static int width;
    private Context mcontext;
    private ImageButton btnWidthMinus;
    private ImageButton btnWidthPlus;
    private ImageButton btnPause;
    private int marginLeft;
    private int marginRight;

    private CustomDialog.Builder builder;
    public static CustomDialog mDialog;
    ViewStub stubGuideSlide;
    private ImageView finger;
    GameView tutorialGameView;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext = this;
        endlessMode();
    }

    public void endlessMode() {
        Log.d("", "endless");
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        tvMapWidth = (TextView) findViewById(R.id.mapWidth);
        width = Integer.parseInt(tvMapWidth.getText().toString());
        btnWidthMinus = (ImageButton) findViewById(R.id.btnWidthMinus);
        btnWidthPlus = (ImageButton) findViewById(R.id.btnWidthPlus);
        gridLayout = findViewById(R.id.mapPreview);
        linearLayout =findViewById(R.id.mapPreviewParent);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
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

        builder = new CustomDialog.Builder(this);
        showTwoButtonDialog("通关", "下一关", "返回主菜单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.startGame();
                mDialog.dismiss();
                //这里写自定义处理XXX
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                endlessMode();
                mDialog.dismiss();
                //这里写自定义处理XXX
            }
        });
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

    private int GetCellWidth2() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cellWidth;
        cellWidth = displayMetrics.widthPixels - marginLeft/5 - marginRight/5;
        return (cellWidth - 5) / width;
    }

    public void hint(View view) {
        gameView.hint();
    }

    public void restart(View view) {
        gameView.restart();
    }

    public void returnMain(View view) {
        builder = new CustomDialog.Builder(this);
        showTwoButtonDialog("返回主菜单吗", null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                setContentView(R.layout.activity_main);
                endlessMode();
                //这里写自定义处理XXX
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                //这里写自定义处理XXX
            }
        });
        mDialog.show();
    }
    private void showTwoButtonDialog(String alertText, String confirmText, String cancelText, View.OnClickListener conFirmListener, View.OnClickListener cancelListener) {
        mDialog = builder.setMessage(alertText)
                .setPositiveButton(confirmText, conFirmListener)
                .setNegativeButton(cancelText, cancelListener)
                .createTwoButtonDialog();
    }

    public void tutorial(View view) {
        width=3;
        setContentView(R.layout.activity_tutorial);
        stubGuideSlide = (ViewStub) findViewById(R.id.guide_root_slide);
        tutorialGameView=(GameView)findViewById(R.id.tutorialGameView);

        builder = new CustomDialog.Builder(this);
        showTwoButtonDialog("通关", "下一关", "返回主菜单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorialGameView.startGame();
                mDialog.dismiss();
                //这里写自定义处理XXX
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                endlessMode();
                mDialog.dismiss();
                //这里写自定义处理XXX
            }
        });

        try {
            final View guideSlideView = stubGuideSlide.inflate();
            RelativeLayout rl = (RelativeLayout) guideSlideView.findViewById(R.id.guide_root);

            finger=(ImageView)findViewById(R.id.ivFinger);
            if (rl != null) {
                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        guideSlideView.setVisibility(View.GONE);
                    }
                });
            }

            Path path = new Path();
            float cellWidth = (float)GetCellWidth2();
            float x = cellWidth;
            float y = cellWidth;

//            Log.d(TAG, "tutorial: "+ tutorialGameView.getTop());

            path.moveTo(10 + 0.5f * x,1.5f * y);
            path.rLineTo(10+1.0f * x, 0);
            path.rLineTo(0, 2.0f *y);
            path.rLineTo(10+1.0f * x, 0);

            ObjectAnimator objectAnimator = new ObjectAnimator();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                objectAnimator = ObjectAnimator.ofFloat(finger, "x", "y", path);
            }
            objectAnimator.setDuration(2000);
            objectAnimator.setRepeatCount(2);
            objectAnimator.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
