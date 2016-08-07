package com.patrick.customviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_custom_layout);
//        initView();
    }

//    private void initView() {
//        CustomView customView = (CustomView) findViewById(R.id.cv);
//        new Thread(customView).start();
//    }
}
