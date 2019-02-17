package com.example.zihan.grid;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
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

import com.example.zihan.grid.old.Cell;
import com.example.zihan.grid.old.CustomDialog;
import com.example.zihan.grid.old.GameView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

//import android.util.Log;

public class MainActivity extends AppCompatActivity{
    private Context mContext;
    private LinearLayout linearLayout;
    private int difficulty;
    private ImageButton mBtnDifficultyDown;
    private ImageButton mBtnDifficultyUp;
    private TextView mTvDifficulty;
    private GridLayout mGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    /**
     * Initialize all variables
     */
    private void initialize() {
        setContentView(R.layout.activity_main);
        this.mContext = this;
        this.mBtnDifficultyDown = findViewById(R.id.btnDifficultyDown);
        this.mBtnDifficultyUp = findViewById(R.id.btnDifficultyUp);
        this.mTvDifficulty = findViewById(R.id.tvDifficulty);
        this.difficulty = Integer.parseInt(this.mTvDifficulty.getText().toString());
        this.mGridLayout = findViewById(R.id.puzzlePreview);
        this.linearLayout =findViewById(R.id.mapPreviewParent);
        updatePreview();
    }

    public void start(View view) {
        setContentView(R.layout.activity_game);
    }

    /**
     * Decrease the difficulty by 1
     * @param view
     */
    public void decreaseDifficulty(View view) {
        this.difficulty--;
        updateTvDifficulty();
    }

    /**
     * Increase the difficulty by 1
     * @param view
     */
    public void increaseDifficulty(View view) {
//        System.out.println(view);
        this.difficulty++;
        updateTvDifficulty();
    }

    /**
     * Get difficulty
     * @return
     */
    public int getDifficulty(){
        return this.difficulty;
    }

    /**
     * Update the TextView in the activity_main.xml
     */
    public void updateTvDifficulty(){
        if (this.difficulty == 5) {
            this.mBtnDifficultyDown.setEnabled(false);
            this.mBtnDifficultyDown.setVisibility(View.INVISIBLE);
        } else if (this.difficulty == 12) {
            this.mBtnDifficultyUp.setEnabled(false);
            this.mBtnDifficultyUp.setVisibility(View.INVISIBLE);
        } else {
            this.mBtnDifficultyDown.setEnabled(true);
            this.mBtnDifficultyUp.setEnabled(true);
            this.mBtnDifficultyDown.setVisibility(View.VISIBLE);
            this.mBtnDifficultyUp.setVisibility(View.VISIBLE);
        }
        this.mTvDifficulty.setText(this.difficulty + "");
        updatePreview();
    }

    private void updatePreview(){
        int width = difficulty;
        mGridLayout.removeAllViews();
        mGridLayout.setColumnCount(width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = new Cell(mContext, i, j);
                mGridLayout.addView(cell, GetCellWidth(), GetCellWidth());
            }
        }
    }

    private int GetCellWidth() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cellWidth;
//        cellWidth = displayMetrics.widthPixels - marginLeft - marginRight;
//        return (cellWidth - 10) / width;
        return 100;
    }
}
