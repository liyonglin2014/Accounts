package com.liyonglin.accounts.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.activity.AccountListActivity;
import com.liyonglin.accounts.activity.HomepageActivity;
import com.liyonglin.accounts.adapter.QianbaoListAdapter;
import com.liyonglin.accounts.presenter.FragmentQianbaoPresenter;
import com.liyonglin.accounts.utils.FinalAttr;
import com.liyonglin.accounts.view.IFragmentQianbaoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 永霖 on 2016/7/23.
 */
public class Fragment_qianbao extends BaseFragment implements IFragmentQianbaoView {

    @BindView(R.id.tv_yue)
    TextView tvYue;
    @BindView(R.id.lv_qianbao)
    ListView lvQianbao;
    private View view;
    private FragmentQianbaoPresenter presenter;
    private QianbaoListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homepage_qianbao, container, false);
        ButterKnife.bind(this, view);
        presenter = new FragmentQianbaoPresenter(this);
        presenter.setTvYueText();
        adapter = new QianbaoListAdapter(getActivity(), presenter);
        lvQianbao.setAdapter(adapter);
        lvQianbao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isShowBook) {
                    ((HomepageActivity) getActivity()).closeBook();
                    return;
                }
                Intent intent = new Intent(getActivity(), AccountListActivity.class);
                intent.putExtra("mode", i);
                Fragment_qianbao.this.startActivityForResult(intent, 0);
            }
        });
        return view;
    }


    @Override
    public void setTvYueText(String text) {
        tvYue.setText(text);
    }

    @Override
    public void updateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateFragment() {
        presenter.updateView();
        isShowBook = false;
    }
}
