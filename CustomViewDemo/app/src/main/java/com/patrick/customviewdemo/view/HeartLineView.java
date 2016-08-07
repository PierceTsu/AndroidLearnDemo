package com.patrick.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @ 创建者     administrator
 * @ 创建时间   2016/7/24 17:38
 * @ 描述      ${TODO}
 * @ 更新者    $Author$
 * @ 更新时间  2016/7/24
 * @ 更新描述  ${TODO}
 */
public class HeartLineView extends View {

    private Paint mPaint;
    private Path mPath;
    private int mScreenW, mScreenH;
    private int mStartX, mStartY;
    private int mInitScreenW;
    private int mInitX;
    private int mMoveX;
    private int mTransX;
    private boolean mIsCanvasMove;

    public HeartLineView(Context context) {
        this(context, null);
    }

    public HeartLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(10,0,0,Color.RED);

        mPath = new Path();

        mTransX = 0;

        mIsCanvasMove = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mScreenW = w;
        mScreenH = h;

        mStartX = 0;
        mStartY = mScreenH/2;

        mInitScreenW = mScreenW;

        mInitX = mScreenW/2;
        mMoveX = mScreenW /24;

        mPath.moveTo(mStartX, mStartY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.lineTo(mStartX, mStartY);
        
        //向左移动画布
        canvas.translate(-mTransX ,0);

        calCoors();
        canvas.drawPath(mPath,mPaint);
        invalidate();
    }

    private void calCoors() {
        if (mIsCanvasMove) {
            mTransX += 4;
        }

        if (mStartX < mInitX) {
            mStartX += 8;
        } else {
            if (mStartX < mInitX + mMoveX) {
                mStartX += 2;
                mStartY -= 8;
            } else {
                if (mStartX < mInitX + (mMoveX * 2)) {
                    mStartX += 2;
                    mStartY += 14;
                } else {
                    if (mStartX < mInitX + (mMoveX * 3)) {
                        mStartX += 2;
                        mStartY -= 12;
                    } else {
                        if (mStartX < mInitX + (mMoveX * 4)) {
                            mStartX += 2;
                            mStartY += 6;
                        } else {
                            if (mStartX < mInitScreenW) {
                                mStartX += 8;
                            } else {
                                mIsCanvasMove = true;
                                mInitX = mInitX + mInitScreenW;
                            }
                        }
                    }
                }
            }

        }
    }
}
