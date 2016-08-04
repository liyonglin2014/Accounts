package com.liyonglin.accounts.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.activity.AccountAddActivity;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;

import java.util.List;

/**
 * Created by 永霖 on 2016/7/30.
 */
public class GridViewAdapter extends BaseAdapter {

    private AccountDBUtils dbUtils;

    private Handler handler;
    private List<Classify> lists;
    private Context context;
    private boolean isShowDelete;
    private int view_width;
    private int view_height;

    private static final float DEGREE_0 = 1.8f;
    private static final float DEGREE_1 = -2.0f;
    private static final float DEGREE_2 = 2.0f;
    private static final float DEGREE_3 = -1.5f;
    private static final float DEGREE_4 = 1.5f;

    private int mCount = 0;

    public GridViewAdapter(List<Classify> lists, Context context, Handler handler) {
        this.lists = lists;
        this.context = context;
        dbUtils = new AccountDBUtils(this.context);
        this.handler = handler;
    }

    public void setIsShowDelete(boolean isShowDelete) {
        this.isShowDelete = isShowDelete;
        notifyDataSetChanged();
    }

    public boolean getIsShowDelete() {
        return this.isShowDelete;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int position = i;
        if (view == null) {
            view = View.inflate(context, R.layout.gv_classify_item, null);
        }
        view_width = view.getWidth();
        view_height = view.getHeight();
        ImageView iv = (ImageView) view.findViewById(R.id.iv_classify_item);
        TextView tv = (TextView) view.findViewById(R.id.tv_classify_item);
        ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_classify_delete);
        iv.setImageResource(lists.get(i).getImgId());
        tv.setText(lists.get(i).getName());
        if(position < lists.size() - 1){
            if (isShowDelete) {
                iv_delete.setVisibility(View.VISIBLE);
                shakeAnimation(view);
            } else {
                iv_delete.setVisibility(View.GONE);
                view.clearAnimation();
                mCount = 0;
            }
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbUtils.deleteFromClassify(lists.get(position).getId());
                    handler.sendEmptyMessage(lists.get(position).getMode() == FinalAttr.CLASSIFY_MODE_IN ?
                    FinalAttr.CLASSIFY_MODE_IN : FinalAttr.CLASSIFY_MODE_OUT);
                }
            });
        }else {
            iv_delete.setVisibility(View.GONE);
        }
        return view;
    }

    private void shakeAnimation(final View v) {
        float rotate = 0;
        int c = mCount++ % 5;
        if (c == 0) {
            rotate = DEGREE_0;
        } else if (c == 1) {
            rotate = DEGREE_1;
        } else if (c == 2) {
            rotate = DEGREE_2;
        } else if (c == 3) {
            rotate = DEGREE_3;
        } else {
            rotate = DEGREE_4;
        }
        final RotateAnimation mra = new RotateAnimation(rotate, -rotate, view_width / 2,
                view_height / 2);
        final RotateAnimation mrb = new RotateAnimation(-rotate, rotate, view_width / 2,
                view_height / 2);

        mra.setDuration(100);
        mrb.setDuration(100);

        mra.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (isShowDelete) {
                    mra.reset();
                    v.startAnimation(mrb);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });

        mrb.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (isShowDelete) {
                    mrb.reset();
                    v.startAnimation(mra);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
        v.startAnimation(mra);
    }
}
