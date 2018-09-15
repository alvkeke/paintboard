package com.paint.alv.paintboard;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        给控件声明变量
         */
        //按钮变量,用于新建和还原
        Button btnNew = findViewById(R.id.btnNew);
        Button btnRestore = findViewById(R.id.btnRestore);
        //图片变量声明,用于存放最近的图片
        ImageView[] ims = new ImageView[9];
        ims[0] = findViewById(R.id.img00);
        ims[1] = findViewById(R.id.img01);
        ims[2] = findViewById(R.id.img02);
        ims[3] = findViewById(R.id.img00);
        ims[4] = findViewById(R.id.img01);
        ims[5] = findViewById(R.id.img02);
        ims[6] = findViewById(R.id.img00);
        ims[7] = findViewById(R.id.img01);
        ims[8] = findViewById(R.id.img02);
        //变量用于存放选中的图片

        /*
        循环设置图片
         */
        for(int i = 0; i<9; i++){
            ims[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        //ims[0].setImageBitmap(BitmapFactory.decodeFile("/mnt/sdcard/PaintBoard/2018-09-16-12_37_18.jpg"));

        //给按钮添加点击事件相应
        //新建按钮
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaintActivity.class);
                startActivity(intent);
            }
        });
        //图片恢复按钮
        btnRestore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });


    }
}
