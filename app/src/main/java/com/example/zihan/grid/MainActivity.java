package com.example.zihan.grid;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    GameView gameView;
    View view;
    GridLayout gridLayout;
    private TextView tvMapWidth;
    private static int width;
    private Button btnWidthMinus;
    private Button btnWidthPlus;
    private int marginLeft;
    private int marginRight;

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
        this.view = view;
        tvMapWidth=(TextView) findViewById(R.id.mapWidth);
        width = Integer.parseInt(tvMapWidth.getText().toString());
        btnWidthMinus=findViewById(R.id.btnWidthMinus);
        btnWidthPlus=findViewById(R.id.btnWidthPlus);
        gridLayout = findViewById(R.id.mapPreview);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)gridLayout.getLayoutParams();
        marginLeft = lp.leftMargin;
        marginRight = lp.rightMargin;
        setTvMapWidth();
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


    public static int getWidth(){
        return width;
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
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(width);
        for(int i=0; i<width; i++){
            for (int j=0; j<width; j++){
                Cell cell = new Cell(view.getContext(),i,j);
                gridLayout.addView(cell,GetCellWidth(),GetCellWidth());
            }
        }
        tvMapWidth.setText(width+"");
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
        cellWidth = displayMetrics.widthPixels- marginLeft-marginRight;
        return ( cellWidth - 10 )/width;
    }
}
