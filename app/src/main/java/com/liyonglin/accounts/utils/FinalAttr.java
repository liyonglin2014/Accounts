package com.liyonglin.accounts.utils;

import com.liyonglin.accounts.R;

/**
 * Created by 永霖 on 2016/7/17.
 */
public class FinalAttr {
    public static final String CREATE_TEAM = "CREATE TABLE team(_id integer PRIMARY KEY " +
            "AUTOINCREMENT NOT NULL, name varchar(20), book_id integer NOT NULL);";
    public static final String CREATE_TEAM_ACCOUNT = "CREATE TABLE team_account(_id integer PRIMARY " +
            "KEY AUTOINCREMENT,account_name varchar(20),account_time varchar(20),account_payMode " +
            "integer NOT NULL,account_describe varchar,account_money varchar(20)," +
            "account_mode integer NOT NULL, book_id integer NOT NULL);";
    public static final String CREATE_ACCOUNT_BOOK = "create table account_book (_id integer PRIMARY KEY " +
            "AUTOINCREMENT NOT NULL, book_name varchar(20), book_mode integer NOT NULL)";
    public static final String CREATE_CLASSIFY = "create table classify (_id integer PRIMARY KEY " +
            "AUTOINCREMENT NOT NULL, classify_name varchar(20), classify_mode integer NOT NULL, " +
            "classify_imgId integer NOT NULL)";

    public static final String TABLE_NAME_TEAM = "team";
    public static final String TABLE_NAME_TEAMACCOUNT = "team_account";
    public static final String TABLE_NAME_ACCOUNTBOOK = "account_book";
    public static final String TABLE_NAME_CLASSIFY = "classify";

    public static final String Field_ID = "_id";

    public static final String Field_ACCOUNT_NAME = "account_name";
    public static final String Field_ACCOUNT_TIME = "account_time";
    public static final String Field_ACCOUNT_PAYMODE = "account_payMode";
    public static final String Field_ACCOUNT_DESCRIBLE = "account_describe";
    public static final String Field_ACCOUNT_MONEY = "account_money";
    public static final String Field_ACCOUNT_MODE = "account_mode";

    public static final String Field_BOOK_MODE = "book_mode";
    public static final String Field_BOOK_NAME = "book_name";

    public static final String Field_CLASSIFY_NAME = "classify_name";
    public static final String Field_CLASSIFY_MODE = "classify_mode";
    public static final String Field_CLASSIFY_IMGID = "classify_imgId";

    public static final int CLASSIFY_MODE_IN = 1;
    public static final int CLASSIFY_MODE_OUT = 2;

    public static final int UPDATE = 200;

    public static final int [] ICONS = {R.mipmap.icon_shouru_baoxiao, R.mipmap.icon_shouru_gongzi,
            R.mipmap.icon_shouru_jiangjin, R.mipmap.icon_shouru_linghuaqian, R.mipmap.icon_shouru_shenghuofei,
            R.mipmap.icon_shouru_touzishouru, R.mipmap.icon_shouru_tuikuan, R.mipmap.icon_shouru_waikuaijianzhi,
            R.mipmap.icon_shouru_xianjin, R.mipmap.icon_shouru_zhifubao, R.mipmap.icon_zhichu_canyin,
            R.mipmap.icon_zhichu_dianying, R.mipmap.icon_zhichu_fangzu, R.mipmap.icon_zhichu_huafei,
            R.mipmap.icon_zhichu_huazhuang, R.mipmap.icon_zhichu_jiaotong, R.mipmap.icon_zhichu_jiushuiyinliao,
            R.mipmap.icon_zhichu_lingshi, R.mipmap.icon_zhichu_maicai, R.mipmap.icon_zhichu_shenghuoyongpin,
            R.mipmap.icon_zhichu_shuiguo, R.mipmap.icon_zhichu_taobao, R.mipmap.icon_zhichu_yaopin,
            R.mipmap.icon_zhichu_yifuxiebao, R.mipmap.icon_hongbao, R.mipmap.icon_qita,
            R.mipmap.icon_add01, R.mipmap.icon_add02,R.mipmap.icon_add03,R.mipmap.icon_add04,
            R.mipmap.icon_add05, R.mipmap.icon_add06,R.mipmap.icon_add07,R.mipmap.icon_add08,
            R.mipmap.icon_add09, R.mipmap.icon_add10,R.mipmap.icon_add11,R.mipmap.icon_add12,
            R.mipmap.icon_add13, R.mipmap.icon_add14,R.mipmap.icon_add15,R.mipmap.icon_add16,
            R.mipmap.icon_add17, R.mipmap.icon_add18,R.mipmap.icon_add19,R.mipmap.icon_add20,
            R.mipmap.icon_add21, R.mipmap.icon_add22,R.mipmap.icon_add23,R.mipmap.icon_add24,
            R.mipmap.icon_add25, R.mipmap.icon_add26,R.mipmap.icon_add27,R.mipmap.icon_add28,
            R.mipmap.icon_add29, R.mipmap.icon_add30,R.mipmap.icon_add31,R.mipmap.icon_add32,
            R.mipmap.icon_add33, R.mipmap.icon_add34,R.mipmap.icon_add35,R.mipmap.icon_add36,
            R.mipmap.icon_add37, R.mipmap.icon_add38,R.mipmap.icon_add39,R.mipmap.icon_add40,
            R.mipmap.icon_add41, R.mipmap.icon_add42,R.mipmap.icon_add43,R.mipmap.icon_add44,
            R.mipmap.icon_add45, R.mipmap.icon_add46,R.mipmap.icon_add47,R.mipmap.icon_add48,
            R.mipmap.icon_add49, R.mipmap.icon_add50,R.mipmap.icon_add51,R.mipmap.icon_add52,
            R.mipmap.icon_add53, R.mipmap.icon_add54,R.mipmap.icon_add55,R.mipmap.icon_add56,
            R.mipmap.icon_add57, R.mipmap.icon_add58,R.mipmap.icon_add59,R.mipmap.icon_add60,
            R.mipmap.icon_add61, R.mipmap.icon_add62,R.mipmap.icon_add63,R.mipmap.icon_add64,
            R.mipmap.icon_add65, R.mipmap.icon_add66,R.mipmap.icon_add67,R.mipmap.icon_add68,
            R.mipmap.icon_add69, R.mipmap.icon_add70,R.mipmap.icon_add71,R.mipmap.icon_add72,
            R.mipmap.icon_add73, R.mipmap.icon_add74,R.mipmap.icon_add75,R.mipmap.icon_add76,
            R.mipmap.icon_add77, R.mipmap.icon_add78,R.mipmap.icon_add79,R.mipmap.icon_add80,
            R.mipmap.icon_add81, R.mipmap.icon_add82,R.mipmap.icon_add83,R.mipmap.icon_add84,
            R.mipmap.icon_add85, R.mipmap.icon_add86,R.mipmap.icon_add87};
}
