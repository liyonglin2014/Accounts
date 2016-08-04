package com.liyonglin.accounts.bean;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.liyonglin.accounts.R;

/**
 * Created by 永霖 on 2016/7/25.
 */
public class ToolBarHelper {

    private Context mContext;
    /* base view */
    private FrameLayout mContentView;
    /*定义的view*/
    private View mUserView;
    private Toolbar mToolBar;
    private LayoutInflater mInflater;
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    public ToolBarHelper(Context context, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        initContentView();
        initUserView(layoutId);
        initToolBar();
    }

    /**初始化整个内容**/
    private void initContentView() {
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    /**初始化toolbar**/
    private void initToolBar() {
        View toolbar = mInflater.inflate(R.layout.toolbar, mContentView);
        mToolBar = (Toolbar) toolbar.findViewById(R.id.mytoolbar);
    }

    @SuppressWarnings("ResourceType")
    private void initUserView(int id) {
        mUserView = mInflater.inflate(id, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
	    /**获取主题中定义的悬浮标志*/
        boolean overly = typedArray.getBoolean(0, false);
	    /**获取主题中定义的toolbar的高度*/
        int toolBarSize = (int) typedArray.getDimension(1,
                (int) mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
	    /**如果是悬浮状态，则不需要设置间距*/
        params.topMargin = overly ? 0 : toolBarSize;
        mContentView.addView(mUserView, params);

    }

    public FrameLayout getContentView() {
        return mContentView;
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }

}
