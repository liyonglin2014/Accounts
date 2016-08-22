package com.liyonglin.accounts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.Account;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;
import com.liyonglin.accounts.view.MetaballView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 永霖 on 2016/8/11.
 */
public class AccountListAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_MENU = 1;
    public static final int VIEW_TYPE_NONE = 2;
    public static final int VIEW_TYPE_LOAD = 3;

    private Context mContext;
    private List<Account> list;
    private MyItemClickListener mItemClickListener;
    private AccountDBUtils dbUtils;
    private int index = 0;
    String flagStr;

    public void updateList(List<Account> accounts) {
        list = accounts;
        test();
        notifyDataSetChanged();
    }

    public void addAll(List<Account> accounts) {
        test2(accounts);
        list.addAll(accounts);
        notifyDataSetChanged();
    }

    public void addLoad(){
        list.add(null);
        notifyDataSetChanged();
    }

    public void removeLoad(){
        list.remove(list.size() - 1);
        notifyDataSetChanged();
    }

    public AccountListAdapter(Context mContext, List<Account> list) {
        this.mContext = mContext;
        this.list = list;
        dbUtils = new AccountDBUtils(mContext);
        test();
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion, Account account);
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    List<String> strs = new ArrayList<String>();
    List<Integer> ints = new ArrayList<Integer>();

    public Account getAccount(int position) {
        return list.get(position);
    }

    private void test() {
        strs.clear();
        ints.clear();
        flagStr = null;
        index = 0;
        for (int i = 0; i < list.size(); i++) {
            Date date = new Date(list.get(i).getAccount_time());
            String dateStr = new SimpleDateFormat("MM月dd日").format(date);
            if (!dateStr.equals(flagStr)) {
                ints.add(i + index);
                strs.add(dateStr);
                flagStr = dateStr;
                index++;
            }
        }
        for (int i = 0; i < ints.size(); i++) {
            list.add(ints.get(i), new Account());
        }

    }

    private void test2(List<Account> accounts) {
        int iSize = list.size();
        int newIndex = 0;
        List<Integer> ints2 = new ArrayList<Integer>();
        for (int i = 0; i < accounts.size(); i++) {
            Date date = new Date(accounts.get(i).getAccount_time());
            String dateStr = new SimpleDateFormat("MM月dd日").format(date);
            if (!dateStr.equals(flagStr)) {
                ints.add(i + newIndex + iSize);
                ints2.add(i + newIndex);
                strs.add(dateStr);
                flagStr = dateStr;
                index++;
                newIndex++;
            }
        }
        for (int i = 0; i < ints2.size(); i++) {
            accounts.add(ints2.get(i), new Account());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) == null) {
            return VIEW_TYPE_LOAD;
        }
        if (ints.contains(position)) {
            flagStr = strs.get(ints.indexOf(position));
            return VIEW_TYPE_NONE;
        } else {
            return VIEW_TYPE_MENU;
        }

    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NONE) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_account2, parent, false);
        } else if (viewType == VIEW_TYPE_MENU) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_account, parent, false);
        } else if (viewType == VIEW_TYPE_LOAD) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_loadmore, parent, false);
        } else {
            return null;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_NONE) {
            viewHolder = new DefaultViewHolder2(realContentView);
        } else if (viewType == VIEW_TYPE_MENU) {
            viewHolder = new DefaultViewHolder(realContentView, this.mItemClickListener);
        } else {
            viewHolder = new DefaultViewHolder3(realContentView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DefaultViewHolder) {
            DefaultViewHolder defaultViewHolder = (DefaultViewHolder) holder;
            Account account = list.get(position);
            Date date = new Date(account.getAccount_time());
            Classify classify = dbUtils.findFromClassifyById(account.getClassify_id());
            defaultViewHolder.ivClassify.setImageResource(classify.getImgId());
            defaultViewHolder.tvClassify.setText(classify.getName());
            if (TextUtils.isEmpty(account.getAccount_describe())) {
                defaultViewHolder.tvRemark.setVisibility(View.GONE);
            } else {
                defaultViewHolder.tvRemark.setVisibility(View.VISIBLE);
                defaultViewHolder.tvRemark.setText(account.getAccount_describe());
            }
            String money;
            if (account.getAccount_mode() == FinalAttr.CLASSIFY_MODE_IN) {
                money = account.getAccount_money();
            } else {
                money = "﹣" + account.getAccount_money();
            }
            defaultViewHolder.tvItemYue.setText(money);
            defaultViewHolder.tvItemTime.setText(new SimpleDateFormat("HH:mm").format(date));
        } else if (holder instanceof DefaultViewHolder2) {
            DefaultViewHolder2 defaultViewHolder2 = (DefaultViewHolder2) holder;
            defaultViewHolder2.tvDate.setText(flagStr);
        } else if (holder instanceof DefaultViewHolder3) {
            System.out.println("加载更多");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyItemClickListener mListener;
        @BindView(R.id.iv_classify)
        ImageView ivClassify;
        @BindView(R.id.tv_classify)
        TextView tvClassify;
        @BindView(R.id.tv_remark)
        TextView tvRemark;
        @BindView(R.id.tv_item_yue)
        TextView tvItemYue;
        @BindView(R.id.tv_item_time)
        TextView tvItemTime;

        public DefaultViewHolder(View itemView, MyItemClickListener Listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = Listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getAdapterPosition(), list.get(getAdapterPosition()));
            }
        }
    }

    class DefaultViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date)
        TextView tvDate;

        public DefaultViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DefaultViewHolder3 extends RecyclerView.ViewHolder {

        @BindView(R.id.metaball)
        MetaballView metaball;

        public DefaultViewHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
