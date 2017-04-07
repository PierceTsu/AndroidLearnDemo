package com.pierce.learnscrollerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private Scroller mScroller;
    private MyLinearLayout mLay1;
    private MyLinearLayout mLay2;
    private ContentLinearLayout mLay0;
    private Button mBtn1;
    private Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScroller = new Scroller(this);

        mLay1 = new MyLinearLayout(this);
        mLay2 = new MyLinearLayout(this);
        mLay1.setBackgroundColor(Color.YELLOW);
        mLay2.setBackgroundColor(Color.GREEN);

        mLay0 = new ContentLinearLayout(this);
        mLay0.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );

        setContentView(mLay0,params);

        initViews();
        initListener();
    }

    private void initViews() {

        LinearLayout.LayoutParams p1
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        p1.weight = 1;
        mLay0.addView(mLay1, p1);

        LinearLayout.LayoutParams p2
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        p2.weight = 1;
        mLay0.addView(mLay2, p2);

        mBtn1 = new Button(this);
        mBtn2 = new Button(this);
        mBtn1.setText("btn1 in lay1");
        mBtn2.setText("btn2 in lay2");

        mLay1.addView(mBtn1);
        mLay2.addView(mBtn2);
        mLay0.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mLay1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mLay2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    private void initListener() {
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScroller.startScroll(0, 0, -30, -30, 2000);
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScroller.startScroll(0, 0, 30, 30, 2000);
            }
        });
    }


    class MyButton extends Button {
        public MyButton(Context ctx) {
            super(ctx);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Log.d(TAG, this.toString() + " onDraw------");
        }
    }

    class MyLinearLayout extends LinearLayout {
        public MyLinearLayout(Context ctx) {
            super(ctx);
        }

        @Override
        public void computeScroll() {
            Log.d(TAG, this.toString() + " computeScroll-----------");
            if (mScroller.computeScrollOffset()){   //如果mScroller没有调用startScroll，这里将会返回false。
                //因为调用computeScroll函数的是MyLinearLayout实例，
                //所以调用scrollTo移动的将是该实例的孩子，也就是MyButton实例
                scrollTo(mScroller.getCurrX(), 0);
                Log.d(TAG, "getCurrX = " + mScroller.getCurrX());

                //继续让系统重绘
                getChildAt(0).invalidate();
            }
        }
    }

    class ContentLinearLayout extends LinearLayout {
        public ContentLinearLayout(Context ctx) {
            super(ctx);
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            Log.d(TAG, "contentview dispatchDraw");
            super.dispatchDraw(canvas);
        }

        @Override
        public void computeScroll() {
        }
    }
}
