package com.example.zihan.grid;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    GameView gameView;
    private static SeekBar seekBarWidth;
    private static SeekBar seekBarHeight;
    private static int height;
    private static int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chapterMode(View view) {
        Log.d("","chapter");
    }

    public void endlessMode(View view) {
        Log.d("","endless");

        setContentView(R.layout.activity_setting);
        seekBarWidth=(SeekBar)findViewById(R.id.mapWidth);
        seekBarHeight=(SeekBar)findViewById(R.id.mapHeight);
        seekBarWidth.setMax(7);
        seekBarHeight.setMax(7);
    }

    public void aboutMe(View view) {
        Log.d("","about");
    }

    public void instruction(View view) {
        Log.d("","instruction");
    }

    public void Next(View view) {
        gameView.startGame();
    }

    public void start(View view) {
        width = seekBarWidth.getProgress()+5;
        height = seekBarHeight.getProgress()+5;

        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.gameView);
        //gameView.numRows=height;
        //gameView.numCols=width;
        //Log.d(TAG, "start: "+height+" "+width);
    }
    public static int getHeight(){
        return height;
    }

    public static int getWidth(){
        return width;
    }

    public void colorfy(View view) {
    }
}
