package com.liyonglin.accounts.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by 永霖 on 2016/7/31.
 */
public class SideslipListView extends ListView {

    private int mScreenWidth;//屏幕的宽度
    private boolean isDeleteShow;//删除组件是否显示
    private ViewGroup mPointChild;  //手指按下位置的item组件
    private int mDeleteWidth;  //删除组件的宽度
    private LinearLayout.LayoutParams mItemLayoutParams;  //手指按下时所在的item的布局参数
    private int mDownX;  //手指初次按下的X坐标
    private int mDownY;  //手指初次按下的Y坐标

    private int mPointPosition;  //手指按下位置所在的item位置
    private boolean isAllowItemClick;  //是否允许item点击

    public SideslipListView(Context context) {
        super(context);
        init(context);
    }

    public SideslipListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SideslipListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performActionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                performActionMove(ev);
                if(!isAllowItemClick){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                performActionUp(ev);
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void performActionDown(MotionEvent ev) {
        isAllowItemClick = true;
        mDownX = (int) ev.getX();
        mDownY = (int) ev.getY();
        if (isDeleteShow) {
            ViewGroup tmpViewGroup = (ViewGroup) getChildAt(pointToPosition(mDownX, mDownY) - getFirstVisiblePosition());
            if (!mPointChild.equals(tmpViewGroup)) {
                turnNormal();
            }
        }
        //获取当前的item
        mPointChild = (ViewGroup) getChildAt(pointToPosition(mDownX, mDownY) - getFirstVisiblePosition());

        mDeleteWidth = mPointChild.getChildAt(1).getLayoutParams().width;//获取删除组件的宽度
        mItemLayoutParams = (LinearLayout.LayoutParams) mPointChild.getChildAt(0).getLayoutParams();

        mItemLayoutParams.width = mScreenWidth;
        mPointChild.getChildAt(0).setLayoutParams(mItemLayoutParams);
    }

    private void performActionMove(MotionEvent ev) {
        int nowX = (int) ev.getX();
        int nowY = (int) ev.getY();
        int diffX = nowX - mDownX;
        if (Math.abs(diffX) > Math.abs(nowY - mDownY)) {
            if (!isDeleteShow && nowX < mDownX) {//删除按钮未显示时向左滑
                if (-diffX >= mDeleteWidth) {//如果滑动距离大于删除组件的宽度时进行偏移的最大处理
                    diffX = -mDeleteWidth;
                }
                mItemLayoutParams.leftMargin = diffX;
                mPointChild.getChildAt(0).setLayoutParams(mItemLayoutParams);
            } else if (isDeleteShow && nowX > mDownX) {//删除按钮显示时向右滑
                if (diffX >= mDeleteWidth) {
                    diffX = mDeleteWidth;
                }
                mItemLayoutParams.leftMargin = diffX - mDeleteWidth;
                mPointChild.getChildAt(0).setLayoutParams(mItemLayoutParams);
            }
            isAllowItemClick = false;
        }
    }

    private void performActionUp(MotionEvent ev) {
        isAllowItemClick = true;
        //如果向左滑出超过隐藏的二分之一就全部显示
        if (-mItemLayoutParams.leftMargin >= mDeleteWidth / 2) {
            mItemLayoutParams.leftMargin = -mDeleteWidth;
            isDeleteShow = true;
            mPointChild.getChildAt(0).setLayoutParams(mItemLayoutParams);
        } else {
            turnNormal();
        }
    }

    public void turnNormal() {
        mItemLayoutParams.leftMargin = 0;
        mPointChild.getChildAt(0).setLayoutParams(mItemLayoutParams);
        isDeleteShow = false;
    }

    public boolean isAllowItemClick() {
        return isAllowItemClick;
    }
}
