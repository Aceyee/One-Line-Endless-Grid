package com.example.zihan.grid;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chapterMode(View view) {
        Log.d("","chapter");
        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.gameView);
    }

    public void endlessMode(View view) {
        Log.d("","endless");
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
}
