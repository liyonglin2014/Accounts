package com.liyonglin.accounts.bean;

import java.io.Serializable;

/**
 * Created by 永霖 on 2016/7/21.
 */
public class Account implements Serializable{
    private int id;
    private int team_id;  //记账成员ID
    private long account_time;   //记账时间
    private int account_payMode;   //付款方式
    private String account_describe;   //备注
    private String account_money;   //金额
    private int account_mode;  //收入还是支出
    private int book_id;   //账本的ID
    private int classify_id;   //分类的ID
    private int isPay;    //是否已经支付
    private String imgPath;    //备注图片路径
    private int year;  //记录年份
    private int moon;  //记录月份

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", team_id=" + team_id +
                ", account_time=" + account_time +
                ", account_payMode=" + account_payMode +
                ", account_describe='" + account_describe + '\'' +
                ", account_money='" + account_money + '\'' +
                ", account_mode=" + account_mode +
                ", book_id=" + book_id +
                ", classify_id=" + classify_id +
                ", isPay=" + isPay +
                ", imgPath='" + imgPath + '\'' +
                ", year=" + year +
                ", moon=" + moon +
                '}';
    }

    public Account() {
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public Account(int team_id, long account_time, int account_payMode, String account_describe,
                   String account_money, int account_mode, int book_id, int classify_id, int isPay,
                   String imgPath, int year, int moon) {
        this.team_id = team_id;
        this.account_time = account_time;
        this.account_payMode = account_payMode;
        this.account_describe = account_describe;
        this.account_money = account_money;
        this.account_mode = account_mode;
        this.book_id = book_id;
        this.classify_id = classify_id;
        this.isPay = isPay;
        this.imgPath = imgPath;
        this.year = year;
        this.moon = moon;
    }

    public Account(int id, int team_id, long account_time, int account_payMode, String account_describe,
                   String account_money, int account_mode, int book_id, int classify_id, int isPay,
                   int year, String imgPath, int moon) {
        this.id = id;
        this.team_id = team_id;
        this.account_time = account_time;
        this.account_payMode = account_payMode;
        this.account_describe = account_describe;
        this.account_money = account_money;
        this.account_mode = account_mode;
        this.book_id = book_id;
        this.classify_id = classify_id;
        this.isPay = isPay;
        this.year = year;
        this.imgPath = imgPath;
        this.moon = moon;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMoon() {
        return moon;
    }

    public void setMoon(int moon) {
        this.moon = moon;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(int classify_id) {
        this.classify_id = classify_id;
    }

    public long getAccount_time() {
        return account_time;
    }

    public void setAccount_time(long account_time) {
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
