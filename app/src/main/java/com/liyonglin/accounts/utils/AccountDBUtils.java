package com.liyonglin.accounts.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liyonglin.accounts.bean.Account;
import com.liyonglin.accounts.bean.AccountBook;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.db.AccountDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 永霖 on 2016/7/21.
 */
public class AccountDBUtils {
    private AccountDBHelper helper;
    private SQLiteDatabase db;

    public AccountDBUtils(Context context) {
        this.helper = new AccountDBHelper(context);
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
        db.insert(FinalAttr.TABLE_NAME_ACCOUNTBOOK, null, values);
        db.close();
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
        db.close();
        return list;
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
    }


    public void insertToTeam(String name) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        db.insert(FinalAttr.TABLE_NAME_TEAM, null, values);
        db.close();
    }

    public void deleteFromTeam(String name) {
        db = helper.getWritableDatabase();
        db.delete(FinalAttr.TABLE_NAME_TEAM, "name = ?", new String[]{name});
        db.close();
    }

    public void updateFromTeam(String oldName, String newName) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newName);
        db.update(FinalAttr.TABLE_NAME_TEAM, values, "name = ?", new String[]{oldName});
        db.close();
    }

    public List<String> findAllFromTeam() {
        List<String> list = new ArrayList<String>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(FinalAttr.TABLE_NAME_TEAM, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        db.close();
        return list;
    }

    public void insertToTeamAccount(Account account) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinalAttr.Field_ACCOUNT_NAME, account.getAccount_name());
        values.put(FinalAttr.Field_ACCOUNT_TIME, account.getAccount_time());
        values.put(FinalAttr.Field_ACCOUNT_PAYMODE, account.getAccount_payMode());
        values.put(FinalAttr.Field_ACCOUNT_DESCRIBLE, account.getAccount_describe());
        values.put(FinalAttr.Field_ACCOUNT_MONEY, account.getAccount_money());
        values.put(FinalAttr.Field_ACCOUNT_MODE, account.getAccount_mode());
        db.insert(FinalAttr.TABLE_NAME_TEAMACCOUNT, null, values);
        db.close();
    }
}
