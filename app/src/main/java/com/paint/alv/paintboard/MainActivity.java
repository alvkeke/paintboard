package com.paint.alv.paintboard;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //定义一个画笔,一个画布,和一个ImageView
    private Canvas canvas;
    private ImageView imageView;
    private Paint paint;
    private Bitmap bmp;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //隐藏系统状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //获取屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //初始化变量
        imageView = findViewById(R.id.imageview);
        bmp = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bmp);
        canvas.drawColor(Color.GRAY);
        paint = new Paint();
        //设置画笔颜色
        paint.setColor(Color.RED);
        //设置画笔宽度
        paint.setStrokeWidth(3);
        //给ImageView添加画布
        imageView.setImageBitmap(bmp);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            float oldX, oldY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        oldX = event.getX();
                        oldY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                    case MotionEvent.ACTION_MOVE:
                        canvas.drawLine(oldX, oldY, event.getX(), event.getY(), paint);
                        oldX = event.getX();
                        oldY = event.getY();
                        imageView.setImageBitmap(bmp);
                        break;
                }
                return true;
            }
        });

    }
}
