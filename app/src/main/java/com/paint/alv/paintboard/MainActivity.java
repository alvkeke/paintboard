package com.paint.alv.paintboard;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    //变量用于存储用户现在选择的图片
    private int iofpicaddr = -1;
    private int iofpicaddr_load = -1;
    //变量用于存放图片的地址
    private String[] imaddr = new String[9];
    //图片变量声明,用于存放最近的图片
    final ImageView[] ims = new ImageView[9];

    //双击判断数
    int clicknum = 0;
    //双击判断间隔记录
    Long timemm;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        给控件声明变量
         */

        //按钮变量,用于新建和还原
        Button btnNew = findViewById(R.id.btnNew);
        final Button btnRestore = findViewById(R.id.btnRestore);
        //设置图片控件
        ims[0] = findViewById(R.id.img00);
        ims[1] = findViewById(R.id.img01);
        ims[2] = findViewById(R.id.img02);
        ims[3] = findViewById(R.id.img10);
        ims[4] = findViewById(R.id.img11);
        ims[5] = findViewById(R.id.img12);
        ims[6] = findViewById(R.id.img20);
        ims[7] = findViewById(R.id.img21);
        ims[8] = findViewById(R.id.img22);

        /*
        向用户获取磁盘读取权限
         */
        //定义变量
        int REQUEST_EXTERNAL_STORAGE = 1;
        //定义存储权限的字符串数组
        String[] PERMISSIONS_STORAGE = {
                //Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        //定义变量用于获取当前权限情况
        int permissionStatus = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //如果当前用户没有允许,则向用户请求权限
        if(permissionStatus != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }

        //循环初始化地址字符串的值
        for(int i = 0; i<9; i++){
            imaddr[i] = null;
        }




        /*
        给按钮添加点击事件相应
         */
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
                //判断选中的控件是否加载有图片
                if(iofpicaddr >=0 && iofpicaddr <9 && imaddr[iofpicaddr] != null){
                    //debug输出
                    System.out.println(iofpicaddr);
                    Intent intent = new Intent(MainActivity.this, PaintActivity.class);
                    //给activity传入参数
                    intent.putExtra("addrpic", imaddr[iofpicaddr]);
                    //显示activity
                    startActivity(intent);

                }
            }
        });
        //循环给图片控件添加触摸事件响应
        for(int k = 0; k<9; k++){
            ims[k].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    //判断事件是否为点击事件
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            int i;
                            //判断按下的是哪个控件
                            for(i = 0; i<9; i++){
                                if(ims[i].equals(v)){
                                    //Toast.makeText(getApplicationContext(), Integer.toString(i), Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            //此时i存储的是点击的图片控件的序号,将其存到全局变量中,暂时
                            iofpicaddr_load = i;

                            //此处进行双击判断

                            switch (clicknum){
                                //如果为第一次点击,则设置点击次数为1
                                case 0:
                                    clicknum = 1;
                                    break;
                                case 1:
                                    //如果点击次数超过1,则判断时间间隔
                                    if(new Date().getTime() - timemm <600){
                                        //如果在间隔之内则判断是否点击的同一图片
                                        if(iofpicaddr == iofpicaddr_load){
                                            btnRestore.callOnClick();
                                        }

                                        //如果为同一图片则将图片复原,并清零点击次数
                                        clicknum = 0;
                                    }
                                    break;

                            }
                            //真正的设置
                            iofpicaddr = i;

                            break;
                        case MotionEvent.ACTION_UP:
                            //抬起手指时开始计时
                            timemm = new Date().getTime();
                            break;

                    }

                    //为了能够处理up事件,则必须返回true,如果返回false则不能成功
                    return true;
                }
            });
        }

    }

    //每次显示主界面时,重载显示的图片
    @Override
    public void onStart() {
        //声明用于存放图片根目录的变量
        File rootdir = new File(Environment.getExternalStorageDirectory().getPath() + "/PaintBoard/");

        //判断图片根目录是否存在,如果存在则从中加载地址
        if(rootdir.exists()){
            //列出该目录下的文件,存到picfs中
            File[] picfs = rootdir.listFiles();
            //将文件按照最后修改时间排列
            ArrayTheFiles(picfs);

            //定义一个变量,用于累加,判断最大加载数目
            int i = 0;
            //循环遍历picfs,一个一个加载图片
            for(File f : picfs){

                //将路径添加到路径数组中
                imaddr[i] =f.getPath();
                //给ImageView添加图片
                ims[i].setImageBitmap(BitmapFactory.decodeFile(imaddr[i]));
                //当加载数码到达九张,就结束加载
                if(i++ >= 9) break;
            }
        }
        super.onStart();
    }

    //排列图片文件的函数
    private void ArrayTheFiles(File[] picfs) {
        //循环排序,冒泡排序
        //没有记住其他的排序算法,就连这个也是百度来的,更何况夜已深,不想再寻找更深入的排序方法了
        //等有时间在修改此处
        for(int i = 0; i<picfs.length-1; i++){
            for(int j = 0; j<picfs.length-1; j++){
                if(picfs[j].lastModified() < picfs[j+1].lastModified()){
                    File f = picfs[j];
                    picfs[j] = picfs[j+1];
                    picfs[j+1] = f;
                }
            }
        }

        //return picfs;
    }
}
