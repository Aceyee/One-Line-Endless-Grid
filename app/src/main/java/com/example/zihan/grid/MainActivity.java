package com.example.zihan.grid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import android.util.Log;

public class MainActivity extends AppCompatActivity{
    private Context mContext;
    private LinearLayout linearLayout;
    private static int difficulty;
    private static int previewCellWidth;
    private static int cellWidth;
    private ImageButton mBtnDifficultyDown;
    private ImageButton mBtnDifficultyUp;
    private TextView mTvDifficulty;
    private GridLayout mGridLayout;
    private static boolean isTutorial;
    int marginLeft;
    int marginRight;

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
        this.mGridLayout = findViewById(R.id.puzzlePreview);
        this.linearLayout =findViewById(R.id.mapPreviewParent);
        this.mTvDifficulty = findViewById(R.id.tvDifficulty);
        difficulty = Integer.parseInt(this.mTvDifficulty.getText().toString());
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        marginLeft = lp.leftMargin;
        marginRight = lp.rightMargin;
        calcCellWidth();
        updatePreview();
    }

    public void startGame(View view) {
        setContentView(R.layout.activity_game);
    }

    public void startTutorial(View view) {
        isTutorial = true;
        setContentView(R.layout.activity_tutorial);
    }

    /**
     * Decrease the difficulty by 1
     * @param view
     */
    public void decreaseDifficulty(View view) {
        difficulty--;
        updateTvDifficulty();
    }

    /**
     * Increase the difficulty by 1
     * @param view
     */
    public void increaseDifficulty(View view) {
        difficulty++;
        updateTvDifficulty();
    }

    /**
     * Get difficulty
     * @return
     */
    public static int getDifficulty(){
        return difficulty;
    }

    /**
     * Update the TextView in the activity_main.xml
     */
    public void updateTvDifficulty(){
        if (difficulty == 5) {
            this.mBtnDifficultyDown.setEnabled(false);
            this.mBtnDifficultyDown.setVisibility(View.INVISIBLE);
        } else if (difficulty == 12) {
            this.mBtnDifficultyUp.setEnabled(false);
            this.mBtnDifficultyUp.setVisibility(View.INVISIBLE);
        } else {
            this.mBtnDifficultyDown.setEnabled(true);
            this.mBtnDifficultyUp.setEnabled(true);
            this.mBtnDifficultyDown.setVisibility(View.VISIBLE);
            this.mBtnDifficultyUp.setVisibility(View.VISIBLE);
        }
        this.mTvDifficulty.setText(difficulty + "");
        calcCellWidth();
        updatePreview();
    }

    /**
     * Change the grid puzzle when clicked btnDifficultyUp and btnDifficultyDown
     */
    private void updatePreview(){
        int width = difficulty;
        mGridLayout.removeAllViews();
        mGridLayout.setColumnCount(width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = new Cell(mContext, i, j);
                mGridLayout.addView(cell, previewCellWidth, previewCellWidth);
            }
        }
    }

    /**
     * Dynamically calculate cell with in pixel
     * Two scenarios:
     * - preview grid
     * - game grid
     */
    private void calcCellWidth() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int preViewGridWidth = displayMetrics.widthPixels - marginLeft - marginRight;
        int gridWidth = displayMetrics.widthPixels;
        int size = difficulty;
        cellWidth = (gridWidth-10) / size;
        previewCellWidth = (preViewGridWidth - 10) / size;
    }

    public static int getCellWidth(){
        return cellWidth;
    }

    public static boolean getIsTutorial(){
        return isTutorial;
    }

    public void returnMain(View view) {
        initialize();
    }
}
