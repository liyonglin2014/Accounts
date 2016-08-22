package com.liyonglin.accounts.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.presenter.FragmentQianbaoPresenter;
import com.liyonglin.accounts.utils.FinalAttr;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 永霖 on 2016/8/8.
 */
public class QianbaoListAdapter extends BaseAdapter {

    private Context context;
    private String[] describes = {"现金余额", "支付宝余额", "微信余额", "银行卡余额", "别人欠我的钱", "我欠别人的钱"};
    private FragmentQianbaoPresenter presenter;

    public QianbaoListAdapter(Context context, FragmentQianbaoPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return FinalAttr.QIANBAO_TITLES.length;
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
        view = View.inflate(context, R.layout.list_item_qianbao, null);
        GradientDrawable drawable =(GradientDrawable)view.getBackground();
        drawable.setColor(context.getResources().getColor(FinalAttr.QIANBAO_COLORS[i]));
        ViewHolder holder = new ViewHolder(view);
        holder.tvQianbaoItem.setText(FinalAttr.QIANBAO_TITLES[i]);
        holder.tvQianbaoItem2.setText(describes[i]);
        holder.ivQianbaoItem.setImageResource(FinalAttr.QIANBAO_IMGS[i]);
        holder.tvQianbaoItemMoney.setText(presenter.getMoney(i));
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_qianbao_item)
        ImageView ivQianbaoItem;
        @BindView(R.id.tv_qianbao_item)
        TextView tvQianbaoItem;
        @BindView(R.id.tv_qianbao_item2)
        TextView tvQianbaoItem2;
        @BindView(R.id.tv_qianbao_item_money)
        TextView tvQianbaoItemMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
