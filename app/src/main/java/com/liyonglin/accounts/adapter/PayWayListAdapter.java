package com.liyonglin.accounts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyonglin.accounts.R;

import java.util.List;

/**
 * Created by 永霖 on 2016/8/4.
 */
public class PayWayListAdapter extends BaseAdapter {

    private Context context;
    private String strs[];
    private int icons[] = {R.mipmap.xianjin_gray, R.mipmap.zhifubao_gray, R.mipmap.weixin_gray, R.mipmap.yinhangka_gray};
    private List<Integer> moneys;
    private int select = 0;

    public PayWayListAdapter(List<Integer> moneys, Context context, String strs[]) {
        this.moneys = moneys;
        this.context = context;
        this.strs = strs;
    }

    public void setSelect(int select){
        this.select = select;
    }

    @Override
    public int getCount() {
        return strs.length;
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
        view = View.inflate(context, R.layout.list_item_payway, null);
        ImageView iv_payway = (ImageView) view.findViewById(R.id.iv_payway);
        TextView tv_payway_title = (TextView) view.findViewById(R.id.tv_payway_title);
        TextView tv_payway_summary = (TextView) view.findViewById(R.id.tv_payway_summary);
        ImageView iv_payway_select = (ImageView) view.findViewById(R.id.iv_payway_select);

        iv_payway.setImageResource(icons[i]);
        tv_payway_title.setText(strs[i]);

        if(i == select){
            iv_payway_select.setVisibility(View.VISIBLE);
        }else {
            iv_payway_select.setVisibility(View.GONE);
        }

        return view;
    }
}
