package com.liyonglin.accounts.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.Classify;

import java.util.List;

/**
 * Created by 永霖 on 2016/7/31.
 */
public class ClassifyListAdapter extends BaseAdapter {

    private Context context;
    private List<Classify> classifyList;

    public ClassifyListAdapter(Context context, List<Classify> classifyList) {
        this.context = context;
        this.classifyList = classifyList;
    }

    @Override
    public int getCount() {
        return classifyList.size();
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
        ViewHolder holder;
        if(view == null){
            view = View.inflate(context, R.layout.list_item_classify, null);
            holder = new ViewHolder();
            holder.iv_classify_item = (ImageView) view.findViewById(R.id.iv_classify_item);
            holder.tv_classify_item = (TextView) view.findViewById(R.id.tv_classify_item);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.iv_classify_item.setImageResource(classifyList.get(i).getImgId());
        holder.tv_classify_item.setText(classifyList.get(i).getName());
        return view;
    }

    class ViewHolder{
        ImageView iv_classify_item;
        TextView tv_classify_item;
    }

}
