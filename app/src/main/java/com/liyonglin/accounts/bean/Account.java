package com.liyonglin.accounts.bean;

/**
 * Created by 永霖 on 2016/7/21.
 */
public class Account {
    private String account_name;
    private String account_time;
    private int account_payMode;
    private String account_describe;
    private String account_money;
    private int account_mode;

    public Account(String account_name, String account_time, int account_payMode,
                   String account_describe, String account_money, int account_mode) {
        this.account_name = account_name;
        this.account_time = account_time;
        this.account_payMode = account_payMode;
        this.account_describe = account_describe;
        this.account_money = account_money;
        this.account_mode = account_mode;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_time() {
        return account_time;
    }

    public void setAccount_time(String account_time) {
        this.account_time = account_time;
    }

    public int getAccount_payMode() {
        return account_payMode;
    }

    public void setAccount_payMode(int account_payMode) {
        this.account_payMode = account_payMode;
    }

    public String getAccount_describe() {
        return account_describe;
    }

    public void setAccount_describe(String account_describe) {
        this.account_describe = account_describe;
    }

    public String getAccount_money() {
        return account_money;
    }

    public void setAccount_money(String account_money) {
        this.account_money = account_money;
    }

    public int getAccount_mode() {
        return account_mode;
    }

    public void setAccount_mode(int account_mode) {
        this.account_mode = account_mode;
    }
}
