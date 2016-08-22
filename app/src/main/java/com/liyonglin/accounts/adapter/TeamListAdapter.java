package com.liyonglin.accounts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.Team;

import java.util.List;

/**
 * Created by 永霖 on 2016/8/5.
 */
public class TeamListAdapter extends BaseAdapter {

    private Context context;
    private List<Team> list;
    private int select = 0;

    public TeamListAdapter(Context context, List<Team> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
            view = View.inflate(context, R.layout.list_item_team, null);
        }
        TextView tv_team_name = (TextView) view.findViewById(R.id.tv_team_name);
        ImageView iv_team_select = (ImageView) view.findViewById(R.id.iv_team_select);
        tv_team_name.setText(list.get(i).getName());
        if(select == i){
            iv_team_select.setVisibility(View.VISIBLE);
        }else {
            iv_team_select.setVisibility(View.GONE);
        }
        return view;
    }

    public void setSelect(int select){
        this.select = select;
    }

    public void updateAll(List<Team> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
