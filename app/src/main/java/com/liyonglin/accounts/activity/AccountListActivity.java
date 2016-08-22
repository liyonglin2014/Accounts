package com.liyonglin.accounts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.adapter.AccountListAdapter;
import com.liyonglin.accounts.bean.Account;
import com.liyonglin.accounts.bean.DividerItemDecoration;
import com.liyonglin.accounts.bean.LoadDataScrollController;
import com.liyonglin.accounts.presenter.AccountListPresenter;
import com.liyonglin.accounts.utils.FinalAttr;
import com.liyonglin.accounts.utils.MyDatePicker;
import com.liyonglin.accounts.utils.OtherUtils;
import com.liyonglin.accounts.utils.SwipeMenuListViewUtils;
import com.liyonglin.accounts.view.IAccountListView;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountListActivity extends ToolBarActivity2 implements IAccountListView, LoadDataScrollController.OnRecycleRefreshListener, View.OnClickListener {

    @BindView(R.id.tv_account_yue)
    TextView tvAccountYue;
    @BindView(R.id.ll_changeColor1)
    LinearLayout llChangeColor1;
    @BindView(R.id.tv_account_time1)
    TextView tvAccountTime1;
    @BindView(R.id.tv_account_time2)
    TextView tvAccountTime2;
    @BindView(R.id.ll_select_time)
    LinearLayout llSelectTime;
    @BindView(R.id.tv_account_out)
    TextView tvAccountOut;
    @BindView(R.id.tv_account_in)
    TextView tvAccountIn;
    @BindView(R.id.ll_changeColor2)
    LinearLayout llChangeColor2;
    @BindView(R.id.tv_transferOfAccount)
    TextView tvTransferOfAccount;
    @BindView(R.id.rv_accounts)
    SwipeMenuRecyclerView rvAccounts;
    @BindView(R.id.refresh_down)
    SwipeRefreshLayout refreshDown;
    @BindView(R.id.ll_norecord)
    LinearLayout llNorecord;

    private TextView tvDeleteTip;
    private Button btClassifyDeleteCommit;
    private Button btClassifyDeleteCancel;
    private int mode;
    private AccountListPresenter presenter;
    private int color;

    private LoadDataScrollController controller;
    private AccountListAdapter adapter;
    private AlertDialog dialog;
    private int adapterPosition;
    private int start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        ButterKnife.bind(this);
        init();
        initDialog();
    }

    private void init() {
        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", 0);
        presenter = new AccountListPresenter(this, mode);
        color = FinalAttr.QIANBAO_COLORS[mode];
        setToolbarCenterText(FinalAttr.QIANBAO_TITLES[mode]);
        setColorWhite(color);
        setToolbarRightVisibility(View.GONE);
        llChangeColor1.setBackgroundColor(getResources().getColor(color));
        llChangeColor2.setBackgroundColor(getResources().getColor(color));
        presenter.setYueText();
        presenter.setDate(null, null);
        presenter.setAccountList();
    }

    private void initDialog(){
        View view = View.inflate(this, R.layout.dialog_classify_delete, null);
        dialog = new AlertDialog.Builder(this).setView(view).create();
        tvDeleteTip = (TextView) view.findViewById(R.id.tv_delete_tip);
        btClassifyDeleteCommit = (Button) view.findViewById(R.id.bt_classify_delete_commit);
        btClassifyDeleteCancel = (Button) view.findViewById(R.id.bt_classify_delete_cancel);
        btClassifyDeleteCommit.setOnClickListener(this);
        btClassifyDeleteCancel.setOnClickListener(this);
        tvDeleteTip.setText(getString(R.string.delete_commit_record));
    }

    private void showDialog() {
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @OnClick({R.id.ll_select_time, R.id.tv_transferOfAccount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_select_time:
                presenter.showDateDialog();
                break;
            case R.id.tv_transferOfAccount:
                break;
            case R.id.bt_classify_delete_commit:
                dialog.dismiss();
                presenter.deleteRecord(adapter.getAccount(adapterPosition).getId());
                break;
            case R.id.bt_classify_delete_cancel:
                dialog.dismiss();
                break;
        }
    }


    @Override
    public void setYueText(String total, String out, String in) {
        tvAccountYue.setText(total);
        tvAccountOut.setText(out);
        tvAccountIn.setText(in);
    }

    @Override
    public void setDate(String title, String describe) {
        tvAccountTime1.setText(title);
        tvAccountTime2.setText(describe);
    }

    @Override
    public void showDateDialog(int year, int month) {
        MyDatePicker picker = new MyDatePicker(this, MyDatePicker.YEAR_MONTH);
        picker.setRange(1990, Calendar.getInstance().get(Calendar.YEAR));//年份范围
        picker.setRangeMonth(1, Calendar.getInstance().get(Calendar.MONTH) + 1);
        picker.setSelectedItem(year, month);
        picker.setOnDatePickListener(new MyDatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                presenter.setDate(OtherUtils.addZero(Integer.parseInt(year)),
                        OtherUtils.addZero(Integer.parseInt(month)));
                presenter.setYueText();
                controller.setPage(0);
            }
        });
        picker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == FinalAttr.UPDATE) {
            presenter.updateAdapter();
        }
    }

    @Override
    public void setAccountList(List<Account> accounts) {
        if (accounts.size() < 1) {
            llNorecord.setVisibility(View.VISIBLE);
        } else {
            llNorecord.setVisibility(View.GONE);
        }
        controller = new LoadDataScrollController(this);
        rvAccounts.setLayoutManager(new LinearLayoutManager(this));
        rvAccounts.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new AccountListAdapter(this, accounts);
        adapter.setOnItemClickListener(new AccountListAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, Account account) {
                Intent intent = new Intent(AccountListActivity.this, AccountShowActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("account", account);
                startActivityForResult(intent, 0);
            }
        });
        rvAccounts.setAdapter(adapter);
        rvAccounts.setSwipeMenuCreator(SwipeMenuListViewUtils.getCreator2(this));
        rvAccounts.setSwipeMenuItemClickListener(new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
                closeable.smoothCloseMenu();
                AccountListActivity.this.adapterPosition = adapterPosition;
                showDialog();
            }
        });
        rvAccounts.addOnScrollListener(controller);
        rvAccounts.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        start = (int) e.getY();
                        break;
                    case MotionEvent.ACTION_UP :
                        int dy = (int) (start - e.getY());
                        controller.setDy(dy);
                        break;
                    default :
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });
        refreshDown.setOnRefreshListener(controller);
    }

    @Override
    public void updateAdapter(List<Account> accounts) {
        if (accounts.size() < 1) {
            llNorecord.setVisibility(View.VISIBLE);
        } else {
            llNorecord.setVisibility(View.GONE);
        }
        setResult(FinalAttr.UPDATE);
        adapter.updateList(accounts);
    }

    @Override
    public void refresh() {
        refreshDown.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshDown.setRefreshing(false);
                controller.setLoadDataStatus(false);
                presenter.loadPrevious();
            }
        }, 500);
    }

    private int index;

    @Override
    public void loadMore(final int page) {

        List<Account> more = presenter.loadMore(page);
        if(more.size() < 1){
            if(index == 0){
                Toast.makeText(AccountListActivity.this, "再次加载到下月记录", Toast.LENGTH_SHORT).show();
                index++;
            }else{
                boolean flag = presenter.loadNext();
                if(flag){
                    index = 0;
                    controller.setPage(0);
                    controller.setCount();
                }else{
                    Toast.makeText(AccountListActivity.this, "已经是最新月份了", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            adapter.addLoad();
            rvAccounts.scrollToPosition(adapter.getItemCount() - 1);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.removeLoad();
                    adapter.addAll(presenter.loadMore(page));
                    controller.setCount();
                }
            }, 700);

        }
        controller.setLoadDataStatus(false);
    }

}
