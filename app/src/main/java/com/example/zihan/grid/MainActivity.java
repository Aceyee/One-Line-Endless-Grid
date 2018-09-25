package com.example.zihan.grid;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    GameView gameView;
    private TextView tvMapWidth;
    private TextView tvMapHeight;
    private static int height;
    private static int width;
    private Button btnWidthMinus;
    private Button btnWidthPlus;
    private Button btnHeightMinus;
    private Button btnHeightPlus;

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
        tvMapWidth=(TextView) findViewById(R.id.mapWidth);
        tvMapHeight=(TextView) findViewById(R.id.mapHeight);
        height = Integer.parseInt(tvMapHeight.getText().toString());
        width = Integer.parseInt(tvMapWidth.getText().toString());
        btnHeightMinus = findViewById(R.id.btnHeightMinus);
        btnHeightPlus=findViewById(R.id.btnHeightPlus);
        btnWidthMinus=findViewById(R.id.btnWidthMinus);
        btnWidthPlus=findViewById(R.id.btnWidthPlus);
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
        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.gameView);
    }
    public static int getHeight(){
        return height;
    }

    public static int getWidth(){
        return width;
    }

    public void colorfy(View view) {
    }

    private void setTvMapWidth(){
        if(width==5){
            btnWidthMinus.setEnabled(false);
        }else if(width==12){
            btnWidthPlus.setEnabled(false);
        }else{
            btnWidthMinus.setEnabled(true);
            btnWidthPlus.setEnabled(true);
        }
        tvMapWidth.setText(width+"");
    }
    private void setTvMapHeight(){
        if(height==5){
            btnHeightMinus.setEnabled(false);
        }else if(height==12){
            btnHeightPlus.setEnabled(false);
        }else{
            btnHeightMinus.setEnabled(true);
            btnHeightPlus.setEnabled(true);
        }
        tvMapHeight.setText(height+"");
    }
    public void widthMinus(View view) {
        width--;
        setTvMapWidth();
    }

    public void widthPlus(View view) {
        width++;
        setTvMapWidth();
    }

    public void heightMinus(View view) {
        height--;
        setTvMapHeight();
    }

    public void heightPlus(View view) {
        height++;
        setTvMapHeight();
    }
}
