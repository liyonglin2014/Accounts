package com.liyonglin.accounts;

import android.app.Application;
import android.inputmethodservice.Keyboard;
import android.test.ApplicationTestCase;

import com.liyonglin.accounts.bean.Account;
import com.liyonglin.accounts.db.AccountDBHelper;
import com.liyonglin.accounts.utils.AccountDBUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testAccountDB(){
        int month = 13;
        String str = String.format("%02d", month);
        System.out.println(str);
    }

}