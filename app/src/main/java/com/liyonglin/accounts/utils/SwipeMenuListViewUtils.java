package com.liyonglin.accounts.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.liyonglin.accounts.activity.TeamListActivity;
import com.liyonglin.accounts.adapter.AccountListAdapter;

/**
 * Created by 永霖 on 2016/8/5.
 */
public class SwipeMenuListViewUtils {

    private static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static SwipeMenuCreator getCreator(final Context context) {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context.getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.RED));
                deleteItem.setWidth(dp2px(85, context));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        return creator;
    }

    public static com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator getCreator2(final Context context) {
        com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator creator = new com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator() {

            @Override
            public void onCreateMenu(com.yanzhenjie.recyclerview.swipe.SwipeMenu swipeLeftMenu, com.yanzhenjie.recyclerview.swipe.SwipeMenu swipeRightMenu, int viewType) {
                if (viewType == AccountListAdapter.VIEW_TYPE_NONE) {// 根据Adapter的ViewType来决定菜单的样式、颜色等属性、或者是否添加菜单。
                    // Do nothing.
                } else if (viewType == AccountListAdapter.VIEW_TYPE_MENU) {
                    com.yanzhenjie.recyclerview.swipe.SwipeMenuItem deleteItem = new com.yanzhenjie.recyclerview.swipe.SwipeMenuItem(context.getApplicationContext());
                    deleteItem.setBackgroundDrawable(new ColorDrawable(Color.RED));
                    deleteItem.setWidth(dp2px(80, context));
                    deleteItem.setHeight(dp2px(60, context));
                    deleteItem.setText("删除");
                    deleteItem.setTextSize(18);
                    deleteItem.setTextColor(Color.WHITE);
                    swipeRightMenu.addMenuItem(deleteItem);
                }
            }

        };
        return creator;
    }

}
