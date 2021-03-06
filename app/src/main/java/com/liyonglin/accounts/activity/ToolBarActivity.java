package com.liyonglin.accounts.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.liyonglin.accounts.bean.ToolBarHelper;

public class ToolBarActivity extends AppCompatActivity {

    private ToolBarHelper mToolBarHelper ;
    public Toolbar toolbar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mToolBarHelper = new ToolBarHelper(this,layoutResID) ;
        toolbar = mToolBarHelper.getToolBar() ;
        setContentView(mToolBarHelper.getContentView());

        /**把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
	    /**自定义的一些操作*/
        onCreateCustomToolBar(toolbar) ;
    }

    public void onCreateCustomToolBar(Toolbar toolbar){

        toolbar.setContentInsetsRelative(0,0);
    }

}
