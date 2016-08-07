package com.patrick.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @ 创建者     administrator
 * @ 创建时间   2016/7/24 13:59
 * @ 描述      ${TODO}
 * @ 更新者    $Author$
 * @ 更新时间  2016/7/24
 * @ 更新描述  ${TODO}
 */
public class FontView extends View {

    private static final String TEXT = "∮ěがぁキAg壁";
    private Paint mTxtPaint;
    private Paint mLinePaint;

    public FontView(Context context) {
        this(context, null);
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTxtPaint.setTextSize(50);
        mTxtPaint.setTextAlign(Paint.Align.CENTER);
        mTxtPaint.setColor(Color.BLUE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = canvas.getWidth() / 2 - mTxtPaint.measureText(TEXT) / 2;
        float y = canvas.getHeight() / 2 /*- (mTxtPaint.descent() + mTxtPaint.ascent())/2*/ ;
//        canvas.drawText(TEXT, x, y,mTxtPaint);

        canvas.drawText(TEXT,canvas.getWidth()/2,y,mTxtPaint);

        canvas.drawLine(0,canvas.getHeight()/2,canvas.getWidth(),canvas.getHeight()/2,mLinePaint);
    }
}
