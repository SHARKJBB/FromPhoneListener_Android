package com.example.sharkj.fromphonelistener;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FPLMainActivity extends AppCompatActivity {

    //当本类被加载的时候，会自动调用执行onCreate();方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载界面文件
        setContentView(R.layout.activity_fplmain);
        //实现按钮的点击效果
        //1.获得界面上的按钮组件
        final Button btn = (Button) this.findViewById(R.id.BIN_ID);
        //2.对按钮组件添加点击事件
        btn.setOnClickListener(new View.OnClickListener() {
            //当按钮被点击的时候，会自动调用执行OnClick();方法
            @Override
            public void onClick(View view) {
                Toast.makeText(FPLMainActivity.this, "正在开启来电监控功能...", Toast.LENGTH_LONG).show();
                //开启来电监控功能，但是在本类文件中无法完成，需要在Service类中完成
                //补充：讲程序流程跳转到Service类中
                //1.创建跳转意图对象
                Intent it = new Intent();
                //2.告知意图对象从哪里跳转到哪里
                it.setClass(FPLMainActivity.this, FPLService.class);
                // 补充：获得监控者手机号码，并且连同程序流程携带跳转到Service类中，以便使用
                EditText et = findViewById(R.id.ET_ID);
                String phoneNumber = et.getText().toString();
                Bundle bundle = new Bundle();//创建一个容器
                bundle.putString("phonenum", phoneNumber);
                it.putExtra("rongqi", bundle);//将电话号码内容带到Service类中
                //3.命令意图对象进行跳转
                startService(it);
            }
        });
    }
}
