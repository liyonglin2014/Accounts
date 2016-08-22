package com.liyonglin.accounts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.Account;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountShowActivity extends ToolBarActivity2 {

    @BindView(R.id.iv_account)
    ImageView ivAccount;
    @BindView(R.id.iv_account_classify)
    ImageView ivAccountClassify;
    @BindView(R.id.tv_account_classify)
    TextView tvAccountClassify;
    @BindView(R.id.tv_account_money)
    TextView tvAccountMoney;
    @BindView(R.id.tv_account_book)
    TextView tvAccountBook;
    @BindView(R.id.tv_account_time)
    TextView tvAccountTime;
    @BindView(R.id.tv_account_payway)
    TextView tvAccountPayway;
    @BindView(R.id.tv_account_team)
    TextView tvAccountTeam;
    @BindView(R.id.tv_account_remark)
    TextView tvAccountRemark;
    @BindView(R.id.tv_account_bianji)
    TextView tvAccountBianji;
    private int mode;
    private Account account;
    private Classify classify;
    private AccountDBUtils dbUtils;
    private String imgPath;
    private Date date;
    private String remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_show);
        ButterKnife.bind(this);
        dbUtils = new AccountDBUtils(this);
        Intent intent =  getIntent();
        mode = intent.getIntExtra("mode", 0);
        account = (Account) intent.getSerializableExtra("account");
        setColorWhite(FinalAttr.QIANBAO_COLORS[mode]);
        setToolbarCenterText(getString(R.string.detail));
        setToolbarRightText(getString(R.string.delete));
        initView();
    }

    private void initView() {
        classify = dbUtils.findFromClassifyById(account.getClassify_id());
        ivAccountClassify.setImageResource(classify.getImgId());
        tvAccountClassify.setText(classify.getName());
        tvAccountMoney.setText(account.getAccount_mode() == FinalAttr.CLASSIFY_MODE_IN ?
                account.getAccount_money() : "﹣" + account.getAccount_money());
        tvAccountBook.setText(dbUtils.findAllFromBookById(account.getBook_id()).getBookName());
        date = new Date(account.getAccount_time());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        tvAccountTime.setText(time);
        tvAccountPayway.setText(FinalAttr.QIANBAO_TITLES[mode]);
        tvAccountTeam.setText(dbUtils.findFromTeamById(account.getTeam_id()).getName());
        imgPath = account.getImgPath();
        remark = account.getAccount_describe();
        if (TextUtils.isEmpty(imgPath)) {
            ivAccount.setVisibility(View.GONE);
        } else {
            ivAccount.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(imgPath),
                    ivAccount);
        }

        if (TextUtils.isEmpty(remark)) {
            tvAccountRemark.setVisibility(View.GONE);
        } else {
            tvAccountRemark.setVisibility(View.VISIBLE);
            tvAccountRemark.setText(remark);
        }

    }

    @OnClick({R.id.iv_account, R.id.tv_account_bianji})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_account:
                Intent showImg = new Intent(this, ShowImgActivity.class);
                showImg.putExtra("imgPath", imgPath);
                showImg.putExtra("remark", remark);
                showImg.putExtra("money", account.getAccount_money());
                showImg.putExtra("date", new SimpleDateFormat("yyyy/MM/dd").format(date));
                showImg.putExtra("isDelete", FinalAttr.NO);
                startActivity(showImg);
                break;
            case R.id.tv_account_bianji:
                Intent bianji = new Intent(this, AccountAddActivity.class);
                bianji.putExtra("account", account);
                startActivityForResult(bianji, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == FinalAttr.UPDATE){
            account = dbUtils.findFromAccountByid(account.getId());
            initView();
            setResult(FinalAttr.UPDATE);
        }
    }
}
