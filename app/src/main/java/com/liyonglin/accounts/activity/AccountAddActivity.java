package com.liyonglin.accounts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.adapter.GridViewAdapter;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;
import com.liyonglin.accounts.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

public class AccountAddActivity extends AppCompatActivity implements View.OnClickListener {

    private AccountDBUtils dbUtils;

    private ViewPager vp_show_classify;
    private GridView gv_show_classify1;
    private GridView gv_show_classify2;
    private List<Classify> classifies_in;
    private List<Classify> classifies_out;

    private List<View> views;

    private TextView tv_account_in;
    private TextView tv_account_out;

    private ImageView iv_classify_img;
    private TextView tv_classify_name;

    private GridViewAdapter gvAdapter_in;
    private GridViewAdapter gvAdapter_out;

    private int intentClassifyMode;

    private TextView tv_account_money;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FinalAttr.CLASSIFY_MODE_IN :
                    classifies_in.clear();
                    classifies_in.addAll(dbUtils.findFromClassifyByMode(FinalAttr.CLASSIFY_MODE_IN));
                    classifies_in.add(new Classify(getString(R.string.bianji), FinalAttr.CLASSIFY_MODE_IN,
                            R.mipmap.icon_bianji));
                    gvAdapter_in.notifyDataSetChanged();
                    break;
                case FinalAttr.CLASSIFY_MODE_OUT:
                    classifies_out.clear();
                    classifies_out.addAll(dbUtils.findFromClassifyByMode(FinalAttr.CLASSIFY_MODE_OUT));
                    classifies_out.add(new Classify(getString(R.string.bianji), FinalAttr.CLASSIFY_MODE_OUT,
                            R.mipmap.icon_bianji));
                    gvAdapter_out.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_add);
        initView();
        initViewPage();
        initGridView();
    }

    private void initGridView() {
        gv_show_classify1.setOnItemClickListener(new GridViewItemClickListener(classifies_in, gvAdapter_in));
        gv_show_classify2.setOnItemClickListener(new GridViewItemClickListener(classifies_out, gvAdapter_out));
        gv_show_classify1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i < gv_show_classify1.getCount() - 1) {
                    gvAdapter_in.setIsShowDelete(true);
                }
                return true;
            }
        });
        gv_show_classify2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i < gv_show_classify2.getCount() - 1) {
                    gvAdapter_out.setIsShowDelete(true);
                }
                return true;
            }
        });
    }

    private void initViewPage() {
        vp_show_classify = (ViewPager) findViewById(R.id.vp_show_classify);
        vp_show_classify.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }
        });
        tv_account_in.setOnClickListener(this);
        tv_account_out.setOnClickListener(this);
        vp_show_classify.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setTabSelectColor(position);
            }
        });
        vp_show_classify.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    setNotShowDelete();
                }
                return false;
            }
        });
    }

    private void initView() {

        dbUtils = new AccountDBUtils(this);
        classifies_in = dbUtils.findFromClassifyByMode(FinalAttr.CLASSIFY_MODE_IN);
        classifies_in.add(new Classify(getString(R.string.bianji), FinalAttr.CLASSIFY_MODE_IN,
                R.mipmap.icon_bianji));
        classifies_out = dbUtils.findFromClassifyByMode(FinalAttr.CLASSIFY_MODE_OUT);
        classifies_out.add(new Classify(getString(R.string.bianji), FinalAttr.CLASSIFY_MODE_OUT,
                R.mipmap.icon_bianji));
        views = new ArrayList<View>();
        View view1 = View.inflate(this, R.layout.vp_showclassify_item, null);
        View view2 = View.inflate(this, R.layout.vp_showclassify_item, null);
        views.add(view1);
        views.add(view2);

        gv_show_classify1 = (GridView) view1.findViewById(R.id.gv_show_classify);
        gvAdapter_in = new GridViewAdapter(classifies_in, this, handler);
        gv_show_classify1.setAdapter(gvAdapter_in);

        gv_show_classify2 = (GridView) view2.findViewById(R.id.gv_show_classify);
        gvAdapter_out = new GridViewAdapter(classifies_out, this, handler);
        gv_show_classify2.setAdapter(gvAdapter_out);

        tv_account_in = (TextView) findViewById(R.id.tv_account_in);
        tv_account_out = (TextView) findViewById(R.id.tv_account_out);

        iv_classify_img = (ImageView) findViewById(R.id.iv_classify_img);
        tv_classify_name = (TextView) findViewById(R.id.tv_classify_name);
        iv_classify_img.setImageResource(classifies_in.get(0).getImgId());
        tv_classify_name.setText(classifies_in.get(0).getName());

        tv_account_money = (TextView) findViewById(R.id.tv_account_money);
        tv_account_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KeyboardUtils(AccountAddActivity.this, AccountAddActivity.this, tv_account_money)
                        .showKeyboard();
            }
        });
    }

    private void setTabSelectColor(int i) {
        setNotShowDelete();
        resetColor();
        switch (i) {
            case 0:
                tv_account_in.setTextColor(getResources().getColor(R.color.blue_light));
                iv_classify_img.setImageResource(classifies_in.get(0).getImgId());
                tv_classify_name.setText(classifies_in.get(0).getName());
                break;
            case 1:
                tv_account_out.setTextColor(getResources().getColor(R.color.blue_light));
                iv_classify_img.setImageResource(classifies_out.get(0).getImgId());
                tv_classify_name.setText(classifies_out.get(0).getName());
                break;
        }

        vp_show_classify.setCurrentItem(i);

    }

    private void resetColor() {
        tv_account_in.setTextColor(getResources().getColor(R.color.gray2));
        tv_account_out.setTextColor(getResources().getColor(R.color.gray2));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_account_in:
                resetColor();
                setTabSelectColor(0);
                break;
            case R.id.tv_account_out:
                resetColor();
                setTabSelectColor(1);
                break;
        }
    }

    class GridViewItemClickListener implements AdapterView.OnItemClickListener {
        private List<Classify> lists;
        private GridViewAdapter adapter;

        public GridViewItemClickListener(List<Classify> lists, GridViewAdapter adapter) {
            this.lists = lists;
            this.adapter = adapter;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (this.adapter.getIsShowDelete()) {
                return;
            }
            if(i < lists.size() - 1){
                iv_classify_img.setImageResource(lists.get(i).getImgId());
                tv_classify_name.setText(lists.get(i).getName());
            }else {
                intentClassifyMode = lists.get(0).getMode();
                Intent intent = new Intent(AccountAddActivity.this, ClassifyListActivity.class);
                intent.putExtra("classifyMode", intentClassifyMode);
                startActivityForResult(intent, 0);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == FinalAttr.UPDATE){
            handler.sendEmptyMessage(intentClassifyMode);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setNotShowDelete();
        }
        return super.onTouchEvent(event);
    }

    private void setNotShowDelete() {
        gvAdapter_in.setIsShowDelete(false);
        gvAdapter_out.setIsShowDelete(false);
    }

    @Override
    protected void onPause() {
        setNotShowDelete();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (gvAdapter_in.getIsShowDelete() || gvAdapter_out.getIsShowDelete()) {
            setNotShowDelete();
            return;
        }
        super.onBackPressed();
    }
}
