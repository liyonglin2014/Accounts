package com.liyonglin.accounts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.utils.FinalAttr;

/**
 * Created by 永霖 on 2016/7/31.
 */
public class IconAdapter extends BaseAdapter {

    private int[] icons = FinalAttr.ICONS;
    private Context context;

    public IconAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return icons.length;
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
        if(view == null){
            view = View.inflate(context, R.layout.gv_icon_item, null);
        }
        ImageView iv = (ImageView) view.findViewById(R.id.iv_icon_item);
        iv.setImageResource(icons[i]);
        return view;
    }

    class ViewHolder{

    }
}
