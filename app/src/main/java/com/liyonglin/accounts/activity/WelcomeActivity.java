package com.liyonglin.accounts.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.AccountBook;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    private AccountDBUtils dbUtils;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        dbUtils = new AccountDBUtils(this);
        List<AccountBook> books = dbUtils.findAllFromBook();
        if (books.size() < 1){
            dbUtils.insertToBook(getString(R.string.team_account), 1);
            dbUtils.insertToBook(getString(R.string.single_account), 2);
            books = dbUtils.findAllFromBook();
        }
        sp = getSharedPreferences("state", MODE_PRIVATE);
        if (sp.getInt("bookId", -1) == -1){
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("bookId", books.get(0).getBookId());
            editor.commit();
        }

        initClassify();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, HomepageActivity.class));
            }
        }, 2000);
    }

    private void initClassify() {
        if(dbUtils.findFromClassifyByMode(FinalAttr.CLASSIFY_MODE_IN).size() < 1){
            dbUtils.insertToClassify(new Classify(getString(R.string.gongzi), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_shouru_gongzi));
            dbUtils.insertToClassify(new Classify(getString(R.string.shenghuofei), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_shouru_shenghuofei));
            dbUtils.insertToClassify(new Classify(getString(R.string.hongbao), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_hongbao));
            dbUtils.insertToClassify(new Classify(getString(R.string.linghuaqian), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_shouru_linghuaqian));
            dbUtils.insertToClassify(new Classify(getString(R.string.waikuaijianzhi), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_shouru_waikuaijianzhi));
            dbUtils.insertToClassify(new Classify(getString(R.string.touzishouru), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_shouru_touzishouru));
            dbUtils.insertToClassify(new Classify(getString(R.string.jiangjin), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_shouru_jiangjin));
            dbUtils.insertToClassify(new Classify(getString(R.string.baoxiao), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_shouru_baoxiao));
            dbUtils.insertToClassify(new Classify(getString(R.string.xianjin), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_shouru_xianjin));
            dbUtils.insertToClassify(new Classify(getString(R.string.tuikuan), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_shouru_tuikuan));
            dbUtils.insertToClassify(new Classify(getString(R.string.zhifubao), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_shouru_zhifubao));
            dbUtils.insertToClassify(new Classify(getString(R.string.qita), FinalAttr.CLASSIFY_MODE_IN, R.mipmap.icon_qita));

            dbUtils.insertToClassify(new Classify(getString(R.string.canyin), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_canyin));
            dbUtils.insertToClassify(new Classify(getString(R.string.jiaotong), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_jiaotong));
            dbUtils.insertToClassify(new Classify(getString(R.string.jiushuiyinliao), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_jiushuiyinliao));
            dbUtils.insertToClassify(new Classify(getString(R.string.shuiguo), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_shuiguo));
            dbUtils.insertToClassify(new Classify(getString(R.string.lingshi), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_lingshi));
            dbUtils.insertToClassify(new Classify(getString(R.string.maicai), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_maicai));
            dbUtils.insertToClassify(new Classify(getString(R.string.yifuxiebao), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_yifuxiebao));
            dbUtils.insertToClassify(new Classify(getString(R.string.shenghuoyongpin), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_shenghuoyongpin));
            dbUtils.insertToClassify(new Classify(getString(R.string.huafei), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_huafei));
            dbUtils.insertToClassify(new Classify(getString(R.string.hufucaizhuang), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_huazhuang));
            dbUtils.insertToClassify(new Classify(getString(R.string.fangzu), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_fangzu));
            dbUtils.insertToClassify(new Classify(getString(R.string.dianying), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_dianying));
            dbUtils.insertToClassify(new Classify(getString(R.string.taobao), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_taobao));
            dbUtils.insertToClassify(new Classify(getString(R.string.hongbao), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_hongbao));
            dbUtils.insertToClassify(new Classify(getString(R.string.yaopin), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_zhichu_yaopin));
            dbUtils.insertToClassify(new Classify(getString(R.string.qita), FinalAttr.CLASSIFY_MODE_OUT, R.mipmap.icon_qita));
        }
    }
}
