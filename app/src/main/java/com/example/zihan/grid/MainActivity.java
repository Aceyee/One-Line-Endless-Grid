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
    private int difficulty;
    private ImageButton btnDifficultyDown;
    private ImageButton btnDifficultyUp;
    private TextView tvDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    /**
     * Initialize all variables
     */
    private void initialize() {
        this.btnDifficultyDown = findViewById(R.id.btnDifficultyDown);
        this.btnDifficultyUp = findViewById(R.id.btnDifficultyUp);
        this.tvDifficulty = findViewById(R.id.tvDifficulty);
        this.difficulty = Integer.parseInt(this.tvDifficulty.getText().toString());
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

    public int getDifficulty(){
        return this.difficulty;
    }
    /**
     * Update the TextView in the activity_main.xml
     */
    public void updateTvDifficulty(){
        if (this.difficulty == 5) {
            this.btnDifficultyDown.setEnabled(false);
            this.btnDifficultyDown.setVisibility(View.INVISIBLE);
        } else if (this.difficulty == 12) {
            this.btnDifficultyUp.setEnabled(false);
            this.btnDifficultyUp.setVisibility(View.INVISIBLE);
        } else {
            this.btnDifficultyDown.setEnabled(true);
            this.btnDifficultyUp.setEnabled(true);
            this.btnDifficultyDown.setVisibility(View.VISIBLE);
            this.btnDifficultyUp.setVisibility(View.VISIBLE);
        }
        this.tvDifficulty.setText(this.difficulty + "");
    }
}
