package com.patrick.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.patrick.customviewdemo.utils.UIUtils;

/**
 * @ 创建者     administrator
 * @ 创建时间   2016/7/17 11:26
 * @ 描述      ${TODO}
 * @ 更新者    $Author$
 * @ 更新时间  2016/7/17
 * @ 更新描述  ${TODO}
 */
public class CustomView extends View implements Runnable{

    private Paint mPaint;
    private int mRadius;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = UIUtils.getScreenInfo(getContext()).widthPixels;
        int height = UIUtils.getScreenInfo(getContext()).heightPixels;
        canvas.drawCircle(width/2,height/2,mRadius,mPaint);
    }

    @Override
    public void run() {
        while(true){
            if(mRadius < 200){
                mRadius += 10;
                postInvalidate();
            }else{
                mRadius = 0;
            }

            SystemClock.sleep(100);
        }
    }
}
