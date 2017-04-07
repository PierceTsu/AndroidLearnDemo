package com.pierce.dialogdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog dialog;
    private View   dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //指定dialog的主题
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        dialog = new AlertDialog.Builder(this,R.style.dialog).create();
        // 设置点击外边缘不消失，2.x的应该是默认不消失的
        dialog.setCanceledOnTouchOutside(false);

        ImageView btnClose = (ImageView) dialogView.findViewById(R.id.iv_close);
        btnClose.setOnClickListener(this);
    }

    public void show(View v) {
        //该顺序不能调换
        dialog.show();
        dialog.getWindow().setContentView(dialogView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }
}
