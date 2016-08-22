package com.liyonglin.accounts.view;

import com.liyonglin.accounts.bean.Account;

import java.util.List;

/**
 * Created by 永霖 on 2016/8/9.
 */
public interface IAccountListView {

    void setYueText(String total, String out, String in);

    void setDate(String title, String describe);

    void showDateDialog(int year, int month);

    void setAccountList(List<Account> accounts);

    void updateAdapter(List<Account> accounts);

}
