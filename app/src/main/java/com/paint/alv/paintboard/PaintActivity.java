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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class PaintActivity extends AppCompatActivity {

    //定义一个画笔,一个画布,和一个ImageView
    private Canvas canvas;
    private ImageView imageView;
    private Paint paint;
    private Bitmap bmp;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);


        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //隐藏系统状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //获取屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //初始化变量
        imageView = findViewById(R.id.imagePaint);
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
        //给画板设置绘画的触摸监听,最懂一点进行操作
        imageView.setOnTouchListener(new View.OnTouchListener() {
            //定义变量来储存开始触摸是的点
            float oldX, oldY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //获取触摸时的点
                        oldX = event.getX();
                        oldY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                    case MotionEvent.ACTION_MOVE:
                        //当触摸点改变,从按下时的点到最后的点画一条直线,应该不长
                        canvas.drawLine(oldX, oldY, event.getX(), event.getY(), paint);
                        //改变新的最后的点,使得可以连续画下去
                        oldX = event.getX();
                        oldY = event.getY();
                        //更新图片,使其显示出来
                        imageView.setImageBitmap(bmp);
                        break;
                }
                return true;
            }
        });


    }

    //初始化菜单
     @Override
     public boolean onCreateOptionsMenu(Menu menu){
        //给activity添加菜单
        getMenuInflater().inflate(R.menu.menu_paint, menu);
        return true;
     }

     //响应菜单项的点击
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menuClear:

                break;
            case R.id.menuExit:

                break;
            case R.id.menuSave:
                Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
