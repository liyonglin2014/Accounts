package com.liyonglin.accounts.fragment;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.activity.HomepageActivity;

/**
 * Created by 永霖 on 2016/7/23.
 */
public class Fragment_qianbao extends BaseFragment {

    private View view;
    private ListView lv_qianbao;
    private String[] titles = {"现金", "支付宝", "微信", "银行卡"};
    private String[] describes = {"现金余额", "支付宝余额", "微信余额", "银行卡余额"};
    private int[] imgIds = {R.mipmap.qianbao_xianjin, R.mipmap.qianbao_zhifubao,
            R.mipmap.qianbao_weixin, R.mipmap.qianbao_yinhangka};
    private int[] colorIds = {R.color.qianbao_item_blue, R.color.qianbao_item_purple,
            R.color.qianbao_item_green, R.color.qianbao_item_yellow};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homepage_qianbao, container, false);
        lv_qianbao = (ListView) view.findViewById(R.id.lv_qianbao);
        lv_qianbao.setAdapter(new MyAdapter());
        lv_qianbao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isShowBook){
                    ((HomepageActivity)getActivity()).closeBook();
                    return;
                }
                Toast.makeText(getContext(), titles[i], Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titles.length;
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
            view = View.inflate(getContext(), R.layout.list_item_qianbao, null);
            GradientDrawable drawable =(GradientDrawable)view.getBackground();
            drawable.setColor(getResources().getColor(colorIds[i]));
            TextView tv_qianbao_item_title = (TextView) view.findViewById(R.id.tv_qianbao_item);
            TextView tv_qianbao_item_describe = (TextView) view.findViewById(R.id.tv_qianbao_item2);
            ImageView iv_qianbao_item = (ImageView) view.findViewById(R.id.iv_qianbao_item);
            tv_qianbao_item_title.setText(titles[i]);
            tv_qianbao_item_describe.setText(describes[i]);
            iv_qianbao_item.setImageResource(imgIds[i]);
            return view;
        }
    }



}
