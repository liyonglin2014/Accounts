package com.liyonglin.accounts.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.ToolBarHelper;
import com.liyonglin.accounts.bean.ToolBarHelper2;

public class ToolBarActivity2 extends AppCompatActivity {

    private ToolBarHelper2 mToolBarHelper ;
    public Toolbar toolbar ;
    private LinearLayout ll_back;
    private TextView tv_toolbar2_center;
    private TextView tv_toolbar2_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mToolBarHelper = new ToolBarHelper2(this,layoutResID) ;
        toolbar = mToolBarHelper.getToolBar() ;
        setContentView(mToolBarHelper.getContentView());

        ll_back = (LinearLayout) toolbar.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });

        tv_toolbar2_center = (TextView) toolbar.findViewById(R.id.tv_toolbar2_center);
        tv_toolbar2_right = (TextView) toolbar.findViewById(R.id.tv_toolbar2_right);
        tv_toolbar2_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRight();
            }
        });

        /**把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
	    /**自定义的一些操作*/
        onCreateCustomToolBar(toolbar) ;
    }

    public void onRight() {

    }

    public void onCreateCustomToolBar(Toolbar toolbar){

        toolbar.setContentInsetsRelative(0,0);
    }

    public void onBack(){
        finish();
    }

    public void setToolbarCenterText(CharSequence text){
        tv_toolbar2_center.setText(text);
    }

    public void setToolbarRightText(CharSequence text){
        tv_toolbar2_right.setText(text);
    }
}
