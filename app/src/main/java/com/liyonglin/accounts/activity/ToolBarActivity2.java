package com.liyonglin.accounts.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.ToolBarHelper2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToolBarActivity2 extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_toolbar2_center)
    TextView tvToolbar2Center;
    @BindView(R.id.tv_toolbar2_right)
    TextView tvToolbar2Right;

    private ToolBarHelper2 mToolBarHelper;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mToolBarHelper = new ToolBarHelper2(this, layoutResID);
        toolbar = mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView());
        ButterKnife.bind(this);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });
        tvToolbar2Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRight();
            }
        });

        /**把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /**自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }

    public void onRight() {

    }

    public void onCreateCustomToolBar(Toolbar toolbar) {

        toolbar.setContentInsetsRelative(0, 0);
    }

    public void onBack() {
        finish();
    }

    public void setToolbarCenterText(CharSequence text) {
        tvToolbar2Center.setText(text);
    }

    public void setToolbarRightVisibility(int visibility) {
        tvToolbar2Right.setVisibility(visibility);
    }

    public void setToolbarRightText(CharSequence text) {
        tvToolbar2Right.setText(text);
    }

    public void setColorWhite(int background){
        ivBack.setImageResource(R.mipmap.back_write);
        tvBack.setTextColor(getResources().getColor(R.color.white));
        tvToolbar2Center.setTextColor(getResources().getColor(R.color.white));
        tvToolbar2Right.setTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(background));
    }
}
