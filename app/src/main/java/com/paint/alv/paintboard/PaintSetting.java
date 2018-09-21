package com.paint.alv.paintboard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.net.Inet4Address;

public class PaintSetting extends AppCompatActivity {

    private TextView txtPenSize;
    private SeekBar seekBarPensize;
    private TextView txtPenColor;
    private SeekBar seekBarPenColor_b;
    private SeekBar seekBarPenColor_r;
    private SeekBar seekBarPenColor_g;

    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_setting);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //初始化控件
        paint = new Paint();


        //取得布局中的控件
        Button btnOK = findViewById(R.id.setting_btn_ok);
        Button btnCancel = findViewById(R.id.setting_btn_cancel);
        txtPenColor = findViewById(R.id.txtColor);
        txtPenSize = findViewById(R.id.txtPenSize);
        seekBarPensize = findViewById(R.id.seekBar_PenSize);
        seekBarPenColor_b = findViewById(R.id.seekBar_color_B);
        seekBarPenColor_g = findViewById(R.id.seekBar_color_R);
        seekBarPenColor_r = findViewById(R.id.seekBar_color_G);

        //开始获取传入信息
        Intent intentIn = getIntent();
        //获取画笔大小
        float penSize = intentIn.getFloatExtra("PenSize", 3);
        paint.setStrokeWidth(penSize);
        //获取画笔颜色
        int penColor = intentIn.getIntExtra("PenColor", 0xff0000);
        paint.setColor(penColor);
        txtPenColor.setText("Color of the Pen:" + Integer.toHexString(paint.getColor()));
        txtPenColor.setBackgroundColor(paint.getColor());

        //设置控件基本属性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBarPensize.setMin(1);
        }
        seekBarPenColor_r.setMax(255);
        seekBarPenColor_g.setMax(255);
        seekBarPenColor_b.setMax(255);
        seekBarPensize.setMax(100);

        //将获取的信息放入控件中
        seekBarPensize.setProgress(Math.round(penSize));
        seekBarPenColor_b.setProgress(Color.blue(penColor));
        seekBarPenColor_g.setProgress(Color.green(penColor));
        seekBarPenColor_r.setProgress(Color.red(penColor));
        txtPenSize.setText("Size of the Pen:" + Math.round(penSize));


        //添加控件事件监听
        seekBarPensize.setOnSeekBarChangeListener(new pensizeSeek());
        seekBarPenColor_r.setOnSeekBarChangeListener(new pencolorSeek());
        seekBarPenColor_g.setOnSeekBarChangeListener(new pencolorSeek());
        seekBarPenColor_b.setOnSeekBarChangeListener(new pencolorSeek());

        btnCancel.setOnClickListener(new btncancelClick());
        btnOK.setOnClickListener(new btnokClick());


    }

    class btnokClick implements View.OnClickListener{
        //如果是确认按钮按下
        @Override
        public void onClick(View v) {
            //取得现在对话框上的设置,并将其返回
            Intent intentResult = new Intent();

            intentResult.putExtra("PenColor", paint.getColor());
            intentResult.putExtra("PenSize", paint.getStrokeWidth());
            //设置返回值
            setResult(2, intentResult);
            //结束activity
            finish();
        }
    }

    class btncancelClick implements View.OnClickListener{
        //如果是取消按钮按下
        @Override
        public void onClick(View v) {
            //取得传入的数据
            Float PenSize = getIntent().getFloatExtra("PenSize", 3);
            int PenColor = getIntent().getIntExtra("PenColor", 0xff0000);
            //将其设置在返回的数据中
            Intent intentResult = new Intent();
            intentResult.putExtra("PenSize", PenSize);
            intentResult.putExtra("PenColor", PenColor);
            //返回传入的数据
            setResult(2, intentResult);
            finish();
        }
    }

    class pensizeSeek implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //如果滚动条数据为0,则将其设置为1
            if(progress == 0) seekBarPensize.setProgress(1);
            //获取滑动条上的数据,显示在标签上
            txtPenSize.setText("Size of the Pen:" + Math.round(progress));
            //设置画笔大小为滑动条上的值
            paint.setStrokeWidth(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class pencolorSeek implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int r, g, b;
            //获取三个滑动条上的数据
            b = seekBarPenColor_b.getProgress();
            g = seekBarPenColor_g.getProgress();
            r = seekBarPenColor_r.getProgress();
            //将颜色值算出,根据RGB
            paint.setColor(0xff<<24 | r<<16 | g<<8 | b);
            //在标签上显示
            txtPenColor.setText("Color of the Pen:" + Integer.toHexString(paint.getColor()));
            //设置标签背景颜色
            txtPenColor.setBackgroundColor(paint.getColor());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

}
