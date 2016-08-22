package com.liyonglin.accounts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.utils.FinalAttr;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

public class ShowImgActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_account_img;
    private TextView tv_account_remark;
    private ImageView iv_back;
    private ImageView iv_delete;
    private TextView tv_account_date;
    private TextView tv_account_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_img);
        initView();
        Intent intent = getIntent();
        String imgPath = intent.getStringExtra("imgPath");
        String remark = intent.getStringExtra("remark");
        String money = intent.getStringExtra("money");
        String date = intent.getStringExtra("date");
        int isDelete = intent.getIntExtra("isDelete", -1);
        String uri = ImageDownloader.Scheme.FILE.wrap(imgPath);
        ImageLoader.getInstance().displayImage(uri, iv_account_img);
        tv_account_remark.setText(remark);
        tv_account_money.setText(money);
        tv_account_date.setText(date);
        if (isDelete == FinalAttr.NO){
            iv_delete.setVisibility(View.GONE);
        }
    }

    private void initView() {
        iv_account_img = (ImageView) findViewById(R.id.iv_account_img);
        tv_account_remark = (TextView) findViewById(R.id.tv_account_remark);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        tv_account_date = (TextView) findViewById(R.id.tv_account_date);
        tv_account_money = (TextView) findViewById(R.id.tv_account_money);

        iv_back.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back :
                finish();
                break;
            case R.id.iv_delete :
                setResult(FinalAttr.UPDATE);
                finish();
                break;
        }
    }
}
