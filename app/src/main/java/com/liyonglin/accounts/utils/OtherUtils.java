package com.liyonglin.accounts.utils;

import java.text.DecimalFormat;

/**
 * Created by 永霖 on 2016/8/10.
 */
public class OtherUtils {

    public static String formatFloat(float f) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String text = decimalFormat.format(f);//format 返回的是字符串
        return text;
    }

    public static String addZero(int number){
        return String.format("%02d", number);
    }

}
