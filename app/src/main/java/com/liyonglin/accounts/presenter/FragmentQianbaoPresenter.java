package com.liyonglin.accounts.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.liyonglin.accounts.bean.Account;
import com.liyonglin.accounts.fragment.Fragment_qianbao;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;
import com.liyonglin.accounts.utils.OtherUtils;
import com.liyonglin.accounts.view.IFragmentQianbaoView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 永霖 on 2016/8/8.
 */
public class FragmentQianbaoPresenter {

    private IFragmentQianbaoView view;
    private Context context;
    private AccountDBUtils dbUtils;
    private SharedPreferences sp;
    private int bookId;
    private List<Account> accounts;

    public FragmentQianbaoPresenter(IFragmentQianbaoView view) {
        this.view = view;
        context = ((Fragment_qianbao) view).getActivity();
        dbUtils = new AccountDBUtils(context);
        sp = context.getSharedPreferences("state", context.MODE_PRIVATE);
        bookId = sp.getInt("bookId", 0);
        accounts = dbUtils.findAllFromAccountByBookid(bookId);
    }

    public void setTvYueText() {
        float money = 0.00f;
        System.out.println(bookId);
        for (Account account : accounts) {
            if (account.getIsPay() == FinalAttr.YES) {
                float f = Float.parseFloat(account.getAccount_money());
                if(account.getAccount_mode() == FinalAttr.CLASSIFY_MODE_IN) {
                    money += f;
                }else{
                    money -= f;
                }
            }
        }
        view.setTvYueText(OtherUtils.formatFloat(money));
    }

    public String getMoney(int payMode) {
        float money = 0;
        for (Account account : accounts) {
            if (payMode < FinalAttr.PAYMODE.length) {
                if(account.getAccount_payMode() == payMode && account.getIsPay() == FinalAttr.YES){
                    float f = Float.parseFloat(account.getAccount_money());
                    if(account.getAccount_mode() == FinalAttr.CLASSIFY_MODE_IN) {
                        money += f;
                    }else{
                        money -= f;
                    }
                }
            }else if(payMode == 4){
                if(account.getAccount_mode() == FinalAttr.CLASSIFY_MODE_IN && account.getIsPay() == FinalAttr.NO){
                    float f = Float.parseFloat(account.getAccount_money());
                    money += f;
                }
            }else {
                if(account.getAccount_mode() == FinalAttr.CLASSIFY_MODE_OUT && account.getIsPay() == FinalAttr.NO){
                    float f = Float.parseFloat(account.getAccount_money());
                    money += f;
                }
            }
        }
        return OtherUtils.formatFloat(money);
    }


    public void updateView(){
        bookId = sp.getInt("bookId", 0);
        accounts = dbUtils.findAllFromAccountByBookid(bookId);
        setTvYueText();
        view.updateView();
    }

}
