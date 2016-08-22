package com.liyonglin.accounts.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Selection;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.adapter.GridViewAdapter;
import com.liyonglin.accounts.adapter.PayWayListAdapter;
import com.liyonglin.accounts.adapter.TeamListAdapter;
import com.liyonglin.accounts.bean.Account;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.bean.Displayer;
import com.liyonglin.accounts.bean.Team;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;
import com.liyonglin.accounts.utils.KeyboardUtils;
import com.liyonglin.accounts.utils.ImgUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccountAddActivity extends KeyBoardBaseActivity implements View.OnClickListener {

    private AccountDBUtils dbUtils;

    private ViewPager vp_show_classify;
    private GridView gv_show_classify1;
    private GridView gv_show_classify2;
    private List<Classify> classifies_in;
    private List<Classify> classifies_out;
    private int mClassifyId;

    private List<View> views;

    private TextView tv_account_in;
    private TextView tv_account_out;
    private ImageView iv_back;

    private ImageView iv_classify_img;
    private TextView tv_classify_name;

    private GridViewAdapter gvAdapter_in;
    private GridViewAdapter gvAdapter_out;

    private int intentClassifyMode;

    private TextView tv_account_money;

    private KeyboardUtils kbUtils;
    private LinearLayout ll_showkeyboard;

    private TextView tv_show_payWay;
    private TextView tv_show_isPay;
    private TextView tv_show_date;
    private ImageView iv_show_team;
    private ImageView iv_show_write;

    private int mWidth;
    private String payway_titles[];
    private List<Team> teamList;
    private TeamListAdapter teamListAdapter;
    private PayWayListAdapter payWayListAdapter;

    private PopupWindow pw;
    private TextView tv_isPay;
    private TextView tv_isNotPay;
    private View view_show_ispay;

    private SharedPreferences sp;

    private AlertDialog dialog_team;
    private AlertDialog dialog_payway;
    private AlertDialog dialog_write;
    private AlertDialog dialog_camera;
    private View view_team;
    private View view_payway;
    private View view_write;
    private View view_camera;
    private ListView lv_show_team;
    private TextView tv_team_bianji;
    private ListView lv_show_payway;
    private EditText et_write;
    private ImageView iv_write;
    private ImageView iv_write_close;
    private ImageView iv_write_commit;
    private TextView tv_paizhao;
    private TextView tv_tuku;
    private TextView tv_camera_cancel;

    public static final int CAMERA_REQUEST_CODE = 3;
    public static final int GALLERY_REQUEST_CODE = 4;
    public static final int CROP_REQUEST_CODE = 5;
    private String imgPath;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new Displayer(0))
            .build();

    private Date date;
    private SimpleDateFormat format;

    private static final int SHOW_ACCOUNT_IMG = 6;
    private static final int SHOW_ACCOUNT_DATE = 7;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FinalAttr.CLASSIFY_MODE_IN:
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
    private Account intentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_add);
        WindowManager m = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay();
        mWidth = d.getWidth();
        date = new Date();
        format = new SimpleDateFormat("MM月dd日");
        initView();
        initViewPage();
        initGridView();
        initData();
        initDialog();
        initDialogView();
        intentAccount = (Account) getIntent().getSerializableExtra("account");
        if(intentAccount != null){
            initUpdateAccount(intentAccount);
        }
    }

    private void initUpdateAccount(Account account) {
        int account_mode = account.getAccount_mode();
        Classify classify = dbUtils.findFromClassifyById(account.getClassify_id());
        setTabSelectColor(account_mode == FinalAttr.CLASSIFY_MODE_IN ? 0 : 1);
        mClassifyId = classify.getId();
        iv_classify_img.setImageResource(classify.getImgId());
        tv_classify_name.setText(classify.getName());
        tv_account_money.setText(account.getAccount_money());
        tv_show_payWay.setTag(account.getAccount_payMode());
        tv_show_payWay.setText(FinalAttr.QIANBAO_TITLES[account.getAccount_payMode()]);
        tv_show_isPay.setTag(FinalAttr.YES == account.getIsPay() ? FinalAttr.YES : FinalAttr.NO);
        tv_show_isPay.setText(FinalAttr.YES == account.getIsPay() ? getString(R.string.isPay) : getString(R.string.isNotPay));
        date = new Date(account.getAccount_time());
        Team team = dbUtils.findFromTeamById(account.getTeam_id());
        for (int i = 0; i < teamList.size(); i++) {
            if(team.getId() == teamList.get(i).getId()){
                iv_show_team.setTag(i);
                break;
            }
        }
        System.out.println(iv_show_team.getTag());
        imgPath = account.getImgPath();
        String remark = account.getAccount_describe();
        if (!TextUtils.isEmpty(imgPath)) {
            String path = ImageDownloader.Scheme.FILE.wrap(imgPath);
            ImageLoader.getInstance().displayImage(path, iv_write, options);
        }
        et_write.setText(remark);
    }

    @Override
    public void OK() {
        String money = tv_account_money.getText().toString();
        if("0.00".equals(money)){
            Toast.makeText(AccountAddActivity.this, R.string.money_not_zero, Toast.LENGTH_SHORT).show();
            return;
        }
        int team_id = teamList.get((Integer) iv_show_team.getTag()).getId();
        long account_time = date.getTime();
        int account_payMode = (int) tv_show_payWay.getTag();
        String account_describe = et_write.getText().toString();
        int book_id = sp.getInt("bookId", 0);
        int classify_id = mClassifyId;
        int isPay = (int) tv_show_isPay.getTag();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int moon = cal.get(Calendar.MONTH) + 1;
        Account account = new Account(team_id, account_time, account_payMode, account_describe,
                money, intentClassifyMode, book_id, classify_id,
                isPay, imgPath, year, moon);
        if(intentAccount != null){
            dbUtils.updateFromAccount(account, intentAccount.getId());
        }else{
            dbUtils.insertToTeamAccount(account);
        }
        setResult(FinalAttr.UPDATE);
        finish();
    }

    private void initData() {
        sp = getSharedPreferences("state", MODE_PRIVATE);
        String strs[] = {getString(R.string.xianjin), getString(R.string.zhifubao),
                getString(R.string.weixin), getString(R.string.yinhangka)};
        payway_titles = strs;

        payWayListAdapter = new PayWayListAdapter(null, this, payway_titles);

        teamList = dbUtils.findAllFromTeam(sp.getInt("bookId", 0));
        teamListAdapter = new TeamListAdapter(this, teamList);
    }

    private void initGridView() {
        gv_show_classify1.setOnItemClickListener(new GridViewItemClickListener(classifies_in, gvAdapter_in));
        gv_show_classify2.setOnItemClickListener(new GridViewItemClickListener(classifies_out, gvAdapter_out));
        gv_show_classify1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < gv_show_classify1.getCount() - 1) {
                    gvAdapter_in.setIsShowDelete(true);
                }
                return true;
            }
        });
        gv_show_classify2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < gv_show_classify2.getCount() - 1) {
                    gvAdapter_out.setIsShowDelete(true);
                }
                return true;
            }
        });
        gv_show_classify1.setOnScrollListener(new GridViewScrollChangeListener());
        gv_show_classify2.setOnScrollListener(new GridViewScrollChangeListener());
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
        mClassifyId = classifies_in.get(0).getId();
        vp_show_classify.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setTabSelectColor(position);
                intentClassifyMode = position == 0 ? FinalAttr.CLASSIFY_MODE_IN : FinalAttr.CLASSIFY_MODE_OUT;
            }
        });
        vp_show_classify.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    setNotShowDelete();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    kbUtils.showKeyboard();
                }
                return false;
            }
        });
    }

    private void initView() {

        intentClassifyMode = FinalAttr.CLASSIFY_MODE_IN;

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
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        iv_classify_img = (ImageView) findViewById(R.id.iv_classify_img);
        tv_classify_name = (TextView) findViewById(R.id.tv_classify_name);
        iv_classify_img.setImageResource(classifies_in.get(0).getImgId());
        tv_classify_name.setText(classifies_in.get(0).getName());

        tv_account_money = (TextView) findViewById(R.id.tv_account_money);
        tv_account_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kbUtils.showKeyboard();
            }
        });
        ll_showkeyboard = (LinearLayout) findViewById(R.id.ll_showkeyboard);
        ll_showkeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        kbUtils = new KeyboardUtils(this, this, tv_account_money, ll_showkeyboard);

        tv_show_payWay = (TextView) findViewById(R.id.tv_show_payWay);
        tv_show_payWay.setTag(0);
        tv_show_payWay.setOnClickListener(this);
        tv_show_isPay = (TextView) findViewById(R.id.tv_show_isPay);
        tv_show_isPay.setTag(FinalAttr.YES);
        tv_show_isPay.setOnClickListener(this);
        tv_show_date = (TextView) findViewById(R.id.tv_show_date);
        tv_show_date.setText(format.format(date));
        tv_show_date.setOnClickListener(this);
        iv_show_team = (ImageView) findViewById(R.id.iv_show_team);
        iv_show_team.setOnClickListener(this);
        iv_show_team.setTag(0);
        iv_show_write = (ImageView) findViewById(R.id.iv_show_write);
        iv_show_write.setOnClickListener(this);

        view_show_ispay = View.inflate(this, R.layout.dialog_show_ispay, null);
        tv_isPay = (TextView) view_show_ispay.findViewById(R.id.tv_isPay);
        tv_isNotPay = (TextView) view_show_ispay.findViewById(R.id.tv_isNotPay);
        tv_isPay.setOnClickListener(this);
        tv_isNotPay.setOnClickListener(this);
    }

    private void setTabSelectColor(int i) {
        setNotShowDelete();
        resetColor();
        switch (i) {
            case 0:
                tv_account_in.setTextColor(getResources().getColor(R.color.blue_light));
                iv_classify_img.setImageResource(classifies_in.get(0).getImgId());
                tv_classify_name.setText(classifies_in.get(0).getName());
                mClassifyId = classifies_in.get(0).getId();
                break;
            case 1:
                tv_account_out.setTextColor(getResources().getColor(R.color.blue_light));
                iv_classify_img.setImageResource(classifies_out.get(0).getImgId());
                tv_classify_name.setText(classifies_out.get(0).getName());
                mClassifyId = classifies_out.get(0).getId();
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
                intentClassifyMode = FinalAttr.CLASSIFY_MODE_IN;
                setTabSelectColor(0);
                break;
            case R.id.tv_account_out:
                intentClassifyMode = FinalAttr.CLASSIFY_MODE_OUT;
                resetColor();
                setTabSelectColor(1);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_show_payWay:
                showPayWay();
                break;
            case R.id.tv_show_isPay:
                showIsPay();
                break;
            case R.id.tv_show_date:
                Intent startDate = new Intent(this, CalendarSelectActivity.class);
                startDate.putExtra("date", date);
                startActivityForResult(startDate, SHOW_ACCOUNT_DATE);
                break;
            case R.id.iv_show_team:
                showTeam();
                break;
            case R.id.iv_show_write:
                showWrite();
                break;
            case R.id.tv_isPay:
                tv_show_isPay.setText(tv_isPay.getText());
                tv_show_isPay.setTag(FinalAttr.YES);
                pw.dismiss();
                break;
            case R.id.tv_isNotPay:
                tv_show_isPay.setText(tv_isNotPay.getText());
                tv_show_isPay.setTag(FinalAttr.NO);
                pw.dismiss();
                break;
            case R.id.tv_team_bianji:
                startActivityForResult(new Intent(this, TeamListActivity.class), 2);
                break;
            case R.id.iv_write:
                if(TextUtils.isEmpty(imgPath)){
                    showCamera();
                }else{
                    Intent intent = new Intent(this, ShowImgActivity.class);
                    intent.putExtra("imgPath", imgPath);
                    intent.putExtra("remark", et_write.getText().toString());
                    intent.putExtra("money", tv_account_money.getText().toString());
                    intent.putExtra("date", new SimpleDateFormat("yyyy/MM/dd").format(date));
                    intent.putExtra("isDelete", FinalAttr.YES);
                    startActivityForResult(intent, SHOW_ACCOUNT_IMG);
                }
                break;
            case R.id.iv_write_close:
                dialog_write.dismiss();
                break;
            case R.id.iv_write_commit:
                dialog_write.dismiss();
                break;
            case R.id.tv_camera_cancel:
                dialog_camera.dismiss();
                break;
            case R.id.tv_paizhao:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, CAMERA_REQUEST_CODE);
                break;
            case R.id.tv_tuku:
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType("image/*");
                startActivityForResult(intent2, GALLERY_REQUEST_CODE);
                break;
        }
    }

    private void showCamera() {
        dialog_camera.show();
        setDialogWindow(dialog_camera.getWindow());
    }

    private void showWrite() {
        dialog_write.show();
        Selection.selectAll(et_write.getText());
        dialog_write.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setDialogWindow(dialog_write.getWindow());
    }


    private void initDialog() {
        view_team = View.inflate(this, R.layout.dialog_show_team, null);
        dialog_team = new AlertDialog.Builder(this).setView(view_team).create();

        pw = new PopupWindow(view_show_ispay, 300, ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        view_payway = View.inflate(this, R.layout.dialog_show_payway, null);
        dialog_payway = new AlertDialog.Builder(this).setView(view_payway).create();

        view_write = View.inflate(this, R.layout.dialog_show_write, null);
        dialog_write = new AlertDialog.Builder(this).setView(view_write).create();

        view_camera = View.inflate(this, R.layout.dialog_show_camera, null);
        dialog_camera = new AlertDialog.Builder(this).setView(view_camera).create();
    }

    private void initDialogView() {
        lv_show_team = (ListView) view_team.findViewById(R.id.lv_show_team);
        lv_show_team.setAdapter(teamListAdapter);
        lv_show_team.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                iv_show_team.setTag(i);
                dialog_team.dismiss();
            }
        });

        tv_team_bianji = (TextView) view_team.findViewById(R.id.tv_team_bianji);
        tv_team_bianji.setOnClickListener(this);

        lv_show_payway = (ListView) view_payway.findViewById(R.id.lv_show_payway);
        lv_show_payway.setAdapter(payWayListAdapter);
        lv_show_payway.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv_show_payWay.setText(payway_titles[i]);
                tv_show_payWay.setTag(i);
                dialog_payway.dismiss();
            }
        });

        et_write = (EditText) view_write.findViewById(R.id.et_write);
        iv_write = (ImageView) view_write.findViewById(R.id.iv_write);
        iv_write_close = (ImageView) view_write.findViewById(R.id.iv_write_close);
        iv_write_commit = (ImageView) view_write.findViewById(R.id.iv_write_commit);
        iv_write.setOnClickListener(this);
        iv_write_close.setOnClickListener(this);
        iv_write_commit.setOnClickListener(this);

        tv_paizhao = (TextView) view_camera.findViewById(R.id.tv_paizhao);
        tv_tuku = (TextView) view_camera.findViewById(R.id.tv_tuku);
        tv_camera_cancel = (TextView) view_camera.findViewById(R.id.tv_camera_cancel);
        tv_paizhao.setOnClickListener(this);
        tv_tuku.setOnClickListener(this);
        tv_camera_cancel.setOnClickListener(this);
    }

    private void showTeam() {
        dialog_team.show();
        setDialogWindow(dialog_team.getWindow());
        teamListAdapter.setSelect((Integer) iv_show_team.getTag());
    }

    private void showIsPay() {
        backgroundAlpha(0.7f);
        pw.showAsDropDown(tv_show_isPay, 0, 0);
    }

    public void backgroundAlpha(float bgAlpha) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void showPayWay() {
        dialog_payway.show();
        setDialogWindow(dialog_payway.getWindow());
        payWayListAdapter.setSelect((Integer) tv_show_payWay.getTag());
    }

    private void setDialogWindow(Window wd) {
        wd.setGravity(Gravity.BOTTOM);
        wd.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams p = wd.getAttributes();
        p.width = mWidth;
        System.out.println(p.width);
        wd.setAttributes(p);
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
            if (i < lists.size() - 1) {
                iv_classify_img.setImageResource(lists.get(i).getImgId());
                tv_classify_name.setText(lists.get(i).getName());
                mClassifyId = lists.get(i).getId();
            } else {
                intentClassifyMode = lists.get(0).getMode();
                Intent intent = new Intent(AccountAddActivity.this, ClassifyListActivity.class);
                intent.putExtra("classifyMode", intentClassifyMode);
                startActivityForResult(intent, 1);
            }
        }
    }

    class GridViewScrollChangeListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (i == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                kbUtils.hideKeyboard();
            }
        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        }

    }

    private File deleteFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == FinalAttr.UPDATE) {
            if (requestCode == 1) {
                handler.sendEmptyMessage(intentClassifyMode);
            } else if (requestCode == 2) {
                teamList.clear();
                teamList.addAll(dbUtils.findAllFromTeam(sp.getInt("bookId", 0)));
                teamListAdapter.notifyDataSetChanged();
            } else if(requestCode == SHOW_ACCOUNT_IMG){
                new File(imgPath).delete();
                imgPath = null;
                iv_write.setImageResource(R.mipmap.xiangji);
            } else if(requestCode == SHOW_ACCOUNT_DATE){
                Date newDate = (Date) data.getSerializableExtra("date");
                date = newDate;
                tv_show_date.setText(format.format(date));
            }
        }else{
            if (requestCode == CAMERA_REQUEST_CODE) {
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bm = extras.getParcelable("data");
                        Uri uri = ImgUtils.saveBitmap(bm);
                        deleteFile = ImgUtils.startImageZoom(uri, this);
                    }
                }
            } else if (requestCode == CROP_REQUEST_CODE) {
                if (data == null) {
                    return;
                }
                Bundle extras = data.getExtras();
                if (extras == null) {
                    return;
                }
                Bitmap bm = extras.getParcelable("data");
                imgPath = ImgUtils.getFile(bm).getAbsolutePath();
                String path = ImageDownloader.Scheme.FILE.wrap(imgPath);
                ImageLoader.getInstance().displayImage(path, iv_write, options);
                dialog_camera.dismiss();
                deleteFile.delete();
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                if (data == null) {
                    return;
                }
                Uri uri;
                uri = data.getData();
                Uri fileUri = ImgUtils.convertUri(uri, this);
                deleteFile = ImgUtils.startImageZoom(fileUri, this);
            }
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
