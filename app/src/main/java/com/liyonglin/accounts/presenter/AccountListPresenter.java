package com.liyonglin.accounts.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.Account;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;
import com.liyonglin.accounts.utils.OtherUtils;
import com.liyonglin.accounts.view.IAccountListView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 永霖 on 2016/8/9.
 */
public class AccountListPresenter {

    private IAccountListView view;
    private AccountDBUtils dbUtils;
    private SharedPreferences sp;
    private Context context;
    private int bookId;
    private List<Account> accountsAll;
    private List<Account> accountsDate;
    private List<Account> accountsAllDate;
    private Calendar calendar = Calendar.getInstance();
    private int payMode;
    private int length;
    private int page;

    public AccountListPresenter(IAccountListView view, int payMode) {
        length = 5;
        this.view = view;
        context = (Context) view;
        dbUtils = new AccountDBUtils(context);
        sp = context.getSharedPreferences("state", Context.MODE_PRIVATE);
        bookId = sp.getInt("bookId", 0);
        this.payMode = payMode;
        accountsAll = dbUtils.findAllFromAccountByPaymode(bookId, payMode);
        accountsDate = dbUtils.findAllFromAccountByDate(bookId, payMode, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, 0, length);
        accountsAllDate = dbUtils.findAllFromAccountByAllDate(bookId, payMode, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1);
    }

    public void setAccountList() {
        view.setAccountList(accountsDate);
    }

    public void showDateDialog() {
        view.showDateDialog(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
    }

    public void deleteRecord(int id) {
        dbUtils.deleteFromTeamAccountById(id);
        updateAdapter();
    }

    public void updateAdapter() {
        accountsAll = dbUtils.findAllFromAccountByPaymode(bookId, payMode);
        accountsDate = dbUtils.findAllFromAccountByDate(bookId, payMode, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, 0, length * (page + 1));
        accountsAllDate = dbUtils.findAllFromAccountByAllDate(bookId, payMode, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1);
        setYueText();
        view.updateAdapter(accountsDate);
    }

    public List<Account> loadMore(int page){
        this.page = page;
        List<Account> more = dbUtils.findAllFromAccountByDate(bookId, payMode, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, page * length, length);
        return more;
    }

    public void setYueText() {
        float money_total = 0.00f;
        float money_out = 0.00f;
        float money_in = 0.00f;
        for (Account account : accountsAllDate) {
            if (account.getIsPay() == FinalAttr.YES) {
                float f = Float.parseFloat(account.getAccount_money());
                if (account.getAccount_mode() == FinalAttr.CLASSIFY_MODE_IN) {
                    money_in += f;
                } else {
                    money_out += f;
                }
            }
        }
        for (Account account : accountsAll) {
            if (account.getIsPay() == FinalAttr.YES) {
                float f = Float.parseFloat(account.getAccount_money());
                if (account.getAccount_mode() == FinalAttr.CLASSIFY_MODE_IN) {
                    money_total += f;
                } else {
                    money_total -= f;
                }
            }
        }
        view.setYueText(OtherUtils.formatFloat(money_total), OtherUtils.formatFloat(money_out),
                OtherUtils.formatFloat(money_in));
    }

    public void setDate(String year, String month) {
        this.page = 0;
        if (year == null || month == null) {
            month = OtherUtils.addZero(calendar.get(Calendar.MONTH) + 1);
            year = calendar.get(Calendar.YEAR) + "";
        } else {
            calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
            calendar.set(Calendar.YEAR, Integer.parseInt(year));
            updateAdapter();
        }
        String title = year + "/" + month;
        int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String describe = month + "." + OtherUtils.addZero(minDay) + "-"
                + month + "." + OtherUtils.addZero(maxDay);
        view.setDate(title, describe);
    }

    public void loadPrevious(){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        if(month == 1){
            setDate(OtherUtils.addZero(year - 1), OtherUtils.addZero(12));
        }else{
            setDate(OtherUtils.addZero(year), OtherUtils.addZero(month - 1));
        }
    }

    public boolean loadNext(){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        Calendar cal = Calendar.getInstance();
        if(year == cal.get(Calendar.YEAR) && month == cal.get(Calendar.MONTH) + 1){
            return false;
        }
        if(month == 12){
            setDate(OtherUtils.addZero(year + 1), OtherUtils.addZero(1));
        }else{
            setDate(OtherUtils.addZero(year), OtherUtils.addZero(month + 1));
        }
        return true;
    }
}
