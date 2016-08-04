package com.liyonglin.accounts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;

/**
 * Created by 永霖 on 2016/7/17.
 */
public class AccountDBHelper extends SQLiteOpenHelper {
    public AccountDBHelper(Context context) {
        super(context, "account.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(FinalAttr.CREATE_TEAM);
        sqLiteDatabase.execSQL(FinalAttr.CREATE_TEAM_ACCOUNT);
        sqLiteDatabase.execSQL(FinalAttr.CREATE_ACCOUNT_BOOK);
        sqLiteDatabase.execSQL(FinalAttr.CREATE_CLASSIFY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
