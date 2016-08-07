package com.patrick.customviewdemo.viewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ 创建者     administrator
 * @ 创建时间   2016/8/7 19:55
 * @ 描述      ${TODO}
 * @ 更新者    $Author$
 * @ 更新时间  2016/8/7
 * @ 更新描述  ${TODO}
 */
public class CustomLayout extends ViewGroup{

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //临时变量储存父容器的期望值
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;

        //需要对子元素进行测量
        if(getChildCount() > 0){

            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);
                //子元素的布局参数
                CustomLayoutParams cuslParams = (CustomLayoutParams) childView.getLayoutParams();
                //测量子元素并考虑边距
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);
                //计算父容器的期望
                parentDesireWidth += childView.getMeasuredWidth() + cuslParams.leftMargin + cuslParams.rightMargin;
                parentDesireHeight += childView.getMeasuredHeight() + cuslParams.topMargin + cuslParams.bottomMargin;
            }

            //父容器的内边距
            parentDesireWidth += getPaddingLeft() + getPaddingRight();
            parentDesireHeight += getPaddingTop() + getPaddingBottom();

            //取期望值与建议最小值的最大值
            parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
            parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());

            //遍历childView 调用measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //measureChildren(widthMeasureSpec, heightMeasureSpec);
        }

        //设置最终测量值
        setMeasuredDimension(parentDesireWidth, parentDesireHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int parentPaddingLeft = getPaddingLeft();
        int parentPaddingTop = getPaddingTop();

        if(getChildCount() >0){

            int tempHeight = 0;

            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);
                CustomLayoutParams cuslParams = (CustomLayoutParams) childView.getLayoutParams();
                childView.layout(
                        parentPaddingLeft + cuslParams.leftMargin,
                        tempHeight + parentPaddingTop + cuslParams.topMargin,
                        childView.getMeasuredWidth() + parentPaddingLeft + cuslParams.leftMargin,
                        childView.getMeasuredHeight() + tempHeight + parentPaddingTop + cuslParams.topMargin
                );

                tempHeight += childView.getMeasuredHeight() + cuslParams.topMargin + cuslParams.bottomMargin;
            }
        }
    }

    @Override
    protected CustomLayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }

    /**
     * 检查当前布局参数是否是我们定义的类型,这在code声明布局参数时常常用到
     */
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }

    public static class CustomLayoutParams extends MarginLayoutParams{

        public CustomLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CustomLayoutParams(int width, int height) {
            super(width, height);
        }

        public CustomLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CustomLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
