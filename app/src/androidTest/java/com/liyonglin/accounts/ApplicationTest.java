package com.liyonglin.accounts;

import android.app.Application;
import android.inputmethodservice.Keyboard;
import android.test.ApplicationTestCase;

import com.liyonglin.accounts.db.AccountDBHelper;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testAccountDB(){
        String str = "5.01";
        Float i = Float.parseFloat(str);

        System.out.println("i = " + i);
    }

}