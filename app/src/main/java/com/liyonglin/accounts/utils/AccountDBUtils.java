package com.liyonglin.accounts.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.Account;
import com.liyonglin.accounts.bean.AccountBook;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.bean.Team;
import com.liyonglin.accounts.db.AccountDBHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 永霖 on 2016/7/21.
 */
public class AccountDBUtils {
    private AccountDBHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public AccountDBUtils(Context context) {
        this.helper = new AccountDBHelper(context);
        this.context = context;
    }

    public void insertToClassify(Classify classify){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinalAttr.Field_CLASSIFY_NAME, classify.getName());
        values.put(FinalAttr.Field_CLASSIFY_MODE, classify.getMode());
        values.put(FinalAttr.Field_CLASSIFY_IMGID, classify.getImgId());
        db.insert(FinalAttr.TABLE_NAME_CLASSIFY, null, values);
        db.close();
    }

    public Classify findFromClassifyById(int id){
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_CLASSIFY, null, "_id = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Classify classify = null;
        if(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_CLASSIFY_NAME));
            int mode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_CLASSIFY_MODE));
            int imgId = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_CLASSIFY_IMGID));
            classify = new Classify(id, name, mode, imgId);
        }
        cursor.close();
        db.close();
        return classify;
    }

    public List<Classify> findFromClassifyByMode(int classify_mode){
        List<Classify> lists = new ArrayList<Classify>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_CLASSIFY, null, "classify_mode = ?",
                new String[]{String.valueOf(classify_mode)}, null, null, null, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ID));
            String name = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_CLASSIFY_NAME));
            int mode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_CLASSIFY_MODE));
            int imgId = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_CLASSIFY_IMGID));
            lists.add(new Classify(id, name, mode, imgId));
        }
        cursor.close();
        db.close();
        return lists;
    }

    public void deleteFromClassify(int id){
        db = helper.getWritableDatabase();
        db.delete(FinalAttr.TABLE_NAME_CLASSIFY, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateFromClassify(int id, String classifyName, int imgId){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinalAttr.Field_CLASSIFY_NAME, classifyName);
        values.put(FinalAttr.Field_CLASSIFY_IMGID, imgId);
        db.update(FinalAttr.TABLE_NAME_CLASSIFY, values, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void insertToBook(String bookName, int bookMode){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinalAttr.Field_BOOK_NAME, bookName);
        values.put(FinalAttr.Field_BOOK_MODE, bookMode);
        int l = (int) db.insert(FinalAttr.TABLE_NAME_ACCOUNTBOOK, null, values);
        db.close();
        if(bookMode == FinalAttr.BOOK_MODE_TEAM){
            insertToTeam(context.getString(R.string.me), l);
        }
    }

    public List<AccountBook> findAllFromBook() {
        List<AccountBook> list = new ArrayList<AccountBook>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_ACCOUNTBOOK, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String bookName = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_BOOK_NAME));
            int bookMode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_BOOK_MODE));
            int bookId = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ID));
            list.add(new AccountBook(bookName, bookMode, bookId));
        }
        cursor.close();
        db.close();
        return list;
    }

    public AccountBook findAllFromBookById(int id) {
        AccountBook book = null;
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_ACCOUNTBOOK, null, "_id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToNext()) {
            String bookName = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_BOOK_NAME));
            int bookMode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_BOOK_MODE));
            int bookId = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ID));
            book = new AccountBook(bookName, bookMode, bookId);
        }
        cursor.close();
        db.close();
        return book;
    }

    public void updateFromBook(int bookId, String bookName){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinalAttr.Field_BOOK_NAME, bookName);
        db.update(FinalAttr.TABLE_NAME_ACCOUNTBOOK, values, "_id = ?", new String[]{String.valueOf(bookId)});
        db.close();
    }

    public void deleteFromBook(int bookId){
        db = helper.getWritableDatabase();
        db.delete(FinalAttr.TABLE_NAME_ACCOUNTBOOK, "_id = ?", new String[]{String.valueOf(bookId)});
        db.close();
        deleteFromTeamByBookId(bookId);
    }


    public void insertToTeam(String name, int bookId) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinalAttr.Field_TEAM_NAME, name);
        values.put(FinalAttr.Field_TEAM_BOOKID, bookId);
        db.insert(FinalAttr.TABLE_NAME_TEAM, null, values);
        db.close();
    }

    public void deleteFromTeamById(int id) {
        db = helper.getWritableDatabase();
        db.delete(FinalAttr.TABLE_NAME_TEAM, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteFromTeamByBookId(int bookId) {
        db = helper.getWritableDatabase();
        db.delete(FinalAttr.TABLE_NAME_TEAM, "book_id = ?", new String[]{String.valueOf(bookId)});
        db.close();
    }

    public void updateFromTeam(int id, String newName) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinalAttr.Field_TEAM_NAME, newName);
        db.update(FinalAttr.TABLE_NAME_TEAM, values, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Team> findAllFromTeam(int bookId) {
        List<Team> list = new ArrayList<Team>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_TEAM, null, "book_id = ?",
                new String[]{String.valueOf(bookId)}, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ID));
            String name = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_TEAM_NAME));
            list.add(new Team(id, name, bookId));
        }
        cursor.close();
        db.close();
        return list;
    }

    public Team findFromTeamById(int id) {
        Team team = null;
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_TEAM, null, "_id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        while (cursor.moveToNext()) {
            int bookId = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_TEAM_BOOKID));
            String name = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_TEAM_NAME));
            team = new Team(id, name, bookId);
        }
        cursor.close();
        db.close();
        return team;
    }

    public void insertToTeamAccount(Account account) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinalAttr.Field_ACCOUNT_TEAMID, account.getTeam_id());
        values.put(FinalAttr.Field_ACCOUNT_TIME, account.getAccount_time());
        values.put(FinalAttr.Field_ACCOUNT_PAYMODE, account.getAccount_payMode());
        values.put(FinalAttr.Field_ACCOUNT_DESCRIBLE, account.getAccount_describe());
        values.put(FinalAttr.Field_ACCOUNT_MONEY, account.getAccount_money());
        values.put(FinalAttr.Field_ACCOUNT_MODE, account.getAccount_mode());
        values.put(FinalAttr.Field_ACCOUNT_BOOKID, account.getBook_id());
        values.put(FinalAttr.Field_ACCOUNT_CLASSIFYID, account.getClassify_id());
        values.put(FinalAttr.Field_ACCOUNT_ISPAY, account.getIsPay());
        values.put(FinalAttr.Field_ACCOUNT_IMGPATH, account.getImgPath());
        values.put(FinalAttr.Field_ACCOUNT_YEAR, account.getYear());
        values.put(FinalAttr.Field_ACCOUNT_MOON, account.getMoon());
        db.insert(FinalAttr.TABLE_NAME_TEAMACCOUNT, null, values);
        db.close();
    }

    public void deleteFromTeamAccountById(int id) {
        db = helper.getWritableDatabase();
        db.delete(FinalAttr.TABLE_NAME_TEAMACCOUNT, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateFromAccount(Account account, int id) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinalAttr.Field_ACCOUNT_TEAMID, account.getTeam_id());
        values.put(FinalAttr.Field_ACCOUNT_TIME, account.getAccount_time());
        values.put(FinalAttr.Field_ACCOUNT_PAYMODE, account.getAccount_payMode());
        values.put(FinalAttr.Field_ACCOUNT_DESCRIBLE, account.getAccount_describe());
        values.put(FinalAttr.Field_ACCOUNT_MONEY, account.getAccount_money());
        values.put(FinalAttr.Field_ACCOUNT_MODE, account.getAccount_mode());
        values.put(FinalAttr.Field_ACCOUNT_BOOKID, account.getBook_id());
        values.put(FinalAttr.Field_ACCOUNT_CLASSIFYID, account.getClassify_id());
        values.put(FinalAttr.Field_ACCOUNT_ISPAY, account.getIsPay());
        values.put(FinalAttr.Field_ACCOUNT_IMGPATH, account.getImgPath());
        values.put(FinalAttr.Field_ACCOUNT_YEAR, account.getYear());
        values.put(FinalAttr.Field_ACCOUNT_MOON, account.getMoon());
        db.update(FinalAttr.TABLE_NAME_TEAMACCOUNT, values, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Account> findAllFromAccountByBookid(int bookId){
        List<Account> list = new ArrayList<Account>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_TEAMACCOUNT, null, FinalAttr.Field_ACCOUNT_BOOKID + " = ?",
                new String[]{String.valueOf(bookId)}, null, null, FinalAttr.Field_ACCOUNT_TIME + " desc");
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ID));
            int team_id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_TEAMID));
            long account_time = cursor.getLong(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_TIME));
            int account_payMode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_PAYMODE));
            String account_describe = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_DESCRIBLE));
            String account_money = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MONEY));
            int account_mode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MODE));
            int clasify_id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_CLASSIFYID));
            int isPay = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_ISPAY));
            String imgPath = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_IMGPATH));
            int year = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_YEAR));
            int moon = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MOON));
            Account account = new Account(id, team_id, account_time, account_payMode, account_describe,
                    account_money, account_mode, bookId, clasify_id, isPay, year, imgPath, moon);
            list.add(account);
        }
        return list;
    }

    public Account findFromAccountByid(int id){
        Account account = null;
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_TEAMACCOUNT, null, "_id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToNext()){
            int bookId = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_BOOKID));
            int team_id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_TEAMID));
            long account_time = cursor.getLong(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_TIME));
            int account_payMode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_PAYMODE));
            String account_describe = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_DESCRIBLE));
            String account_money = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MONEY));
            int account_mode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MODE));
            int clasify_id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_CLASSIFYID));
            int isPay = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_ISPAY));
            String imgPath = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_IMGPATH));
            int year = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_YEAR));
            int moon = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MOON));
            account = new Account(id, team_id, account_time, account_payMode, account_describe,
                    account_money, account_mode, bookId, clasify_id, isPay, year, imgPath, moon);
        }
        return account;
    }

    public List<Account> findAllFromAccountByPaymode(int bookId, int payMode){
        List<Account> list = new ArrayList<Account>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_TEAMACCOUNT, null, FinalAttr.Field_ACCOUNT_BOOKID
                + " = ? AND " + FinalAttr.Field_ACCOUNT_PAYMODE + " = ?",
                new String[]{String.valueOf(bookId), String.valueOf(payMode)}, null, null, FinalAttr.Field_ACCOUNT_TIME + " desc");
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ID));
            int team_id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_TEAMID));
            long account_time = cursor.getLong(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_TIME));
            int account_payMode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_PAYMODE));
            String account_describe = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_DESCRIBLE));
            String account_money = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MONEY));
            int account_mode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MODE));
            int clasify_id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_CLASSIFYID));
            int isPay = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_ISPAY));
            String imgPath = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_IMGPATH));
            int year = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_YEAR));
            int moon = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MOON));
            Account account = new Account(id, team_id, account_time, account_payMode, account_describe,
                    account_money, account_mode, bookId, clasify_id, isPay, year, imgPath, moon);
            list.add(account);
        }
        return list;
    }

    public List<Account> findAllFromAccountByDate(int bookId, int payMode, int year, int moon, int start, int length){
        List<Account> list = new ArrayList<Account>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_TEAMACCOUNT, null, FinalAttr.Field_ACCOUNT_BOOKID
                        + " = ? AND " + FinalAttr.Field_ACCOUNT_PAYMODE + " = ? AND "
                + FinalAttr.Field_ACCOUNT_YEAR + " = ? AND " + FinalAttr.Field_ACCOUNT_MOON + " = ?",
                new String[]{String.valueOf(bookId), String.valueOf(payMode), String.valueOf(year),
                        String.valueOf(moon)}, null, null, FinalAttr.Field_ACCOUNT_TIME + " desc",
                start + "," + length);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ID));
            int team_id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_TEAMID));
            long account_time = cursor.getLong(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_TIME));
            int account_payMode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_PAYMODE));
            String account_describe = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_DESCRIBLE));
            String account_money = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MONEY));
            int account_mode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MODE));
            int clasify_id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_CLASSIFYID));
            int isPay = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_ISPAY));
            String imgPath = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_IMGPATH));
            Account account = new Account(id, team_id, account_time, account_payMode, account_describe,
                    account_money, account_mode, bookId, clasify_id, isPay, year, imgPath, moon);
            list.add(account);
        }
        return list;
    }

    public List<Account> findAllFromAccountByAllDate(int bookId, int payMode, int year, int moon){
        List<Account> list = new ArrayList<Account>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_TEAMACCOUNT, null, FinalAttr.Field_ACCOUNT_BOOKID
                        + " = ? AND " + FinalAttr.Field_ACCOUNT_PAYMODE + " = ? AND "
                        + FinalAttr.Field_ACCOUNT_YEAR + " = ? AND " + FinalAttr.Field_ACCOUNT_MOON + " = ?",
                new String[]{String.valueOf(bookId), String.valueOf(payMode), String.valueOf(year),
                        String.valueOf(moon)}, null, null, FinalAttr.Field_ACCOUNT_TIME + " desc");
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ID));
            int team_id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_TEAMID));
            long account_time = cursor.getLong(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_TIME));
            int account_payMode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_PAYMODE));
            String account_describe = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_DESCRIBLE));
            String account_money = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MONEY));
            int account_mode = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_MODE));
            int clasify_id = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_CLASSIFYID));
            int isPay = cursor.getInt(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_ISPAY));
            String imgPath = cursor.getString(cursor.getColumnIndex(FinalAttr.Field_ACCOUNT_IMGPATH));
            Account account = new Account(id, team_id, account_time, account_payMode, account_describe,
                    account_money, account_mode, bookId, clasify_id, isPay, year, imgPath, moon);
            list.add(account);
        }
        return list;
    }
}
