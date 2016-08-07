package com.patrick.customviewdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.patrick.customviewdemo.R;
import com.patrick.customviewdemo.utils.UIUtils;

/**
 * @ 创建者     administrator
 * @ 创建时间   2016/8/2 23:28
 * @ 描述      ${TODO}
 * @ 更新者    $Author$
 * @ 更新时间  2016/8/2
 * @ 更新描述  ${TODO}
 */
public class IconView extends View {

    private Context mCtx;
    private float mTextSize;
    private Bitmap mBitmap;
    private String mIconLable;
    private TextPaint mTextPaint;

    private enum Type{
        WIDTH, HEIGHT
    }

    public IconView(Context context) {
        this(context, null);
    }

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCtx = context;
        init();
    }

    private void init() {

        DisplayMetrics screenInfo = UIUtils.getScreenInfo(mCtx);
        mTextSize = screenInfo.widthPixels / 10f;

        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meizi);
        }

        if (mIconLable == null) {
            mIconLable = "picture description";
        }

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置测量后的尺寸
        setMeasuredDimension(getMeasureSize(widthMeasureSpec, Type.WIDTH), getMeasureSize(heightMeasureSpec, Type.HEIGHT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //左上角起始位置,考虑padding值,并居中
        int left = getWidth() / 2 - mBitmap.getWidth() / 2 /*getPaddingLeft()*/;
        int top  = /*getHeight() / 2 - mBitmap.getHeight() / 2*/ getPaddingTop();
        canvas.drawBitmap(mBitmap, left, top, null);
        //图片的高度 + paddingTop为起始为知
        int height = (int) (mBitmap.getHeight() + /*getHeight()/2 - mBitmap.getHeight()/2*/ getPaddingTop() - mTextPaint.ascent());
        canvas.drawText(mIconLable, getWidth() / 2, height, mTextPaint);
    }

    private int getMeasureSize(int measureSpec, Type type) {
        int result = 0;

        //获取mode和size
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if(mode == MeasureSpec.EXACTLY){
            //默认
            result = size;

        }else{  //AT_MOST and UNSPECIFIED
            if(type == Type.WIDTH){ //取文本和bitmap中width较大的
                int textWidth = (int) mTextPaint.measureText(mIconLable);
                result = textWidth >= mBitmap.getWidth() ?
                        textWidth + getPaddingLeft() + getPaddingRight():
                        mBitmap.getWidth() + getPaddingLeft() + getPaddingRight();
            }else{
                int textHeight = (int) (mTextPaint.descent() - mTextPaint.ascent());
                result = textHeight + mBitmap.getHeight() + getPaddingTop() + getPaddingBottom();
            }

            //AT_MOST 取size和result中较小
            if(mode == MeasureSpec.AT_MOST){
                result = Math.min(result, size);
            }
        }

        return result;
    }
}
