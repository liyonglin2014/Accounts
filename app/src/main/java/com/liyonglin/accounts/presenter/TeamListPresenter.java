package com.liyonglin.accounts.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.Team;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.view.ITeamListView;

import java.util.List;

/**
 * Created by 永霖 on 2016/8/5.
 */
public class TeamListPresenter {

    private ITeamListView view;
    private List<Team> list;
    private AccountDBUtils dbUtils;
    private SharedPreferences sp;
    private Context context;
    private int bookId;

    public TeamListPresenter(ITeamListView view) {
        this.view = view;
        context = (Context) view;
        dbUtils = new AccountDBUtils(context);
        sp = context.getSharedPreferences("state", context.MODE_PRIVATE);
        bookId = sp.getInt("bookId", 0);
    }

    public void initView(){
        list = dbUtils.findAllFromTeam(bookId);
        view.initView(list);
    }

    public void deleteTeam(int id){
        dbUtils.deleteFromTeamById(id);
        updateList();
    }

    public void updateButtonColor(CharSequence charSequence){
        boolean flag = TextUtils.isEmpty(charSequence);
        if (flag){
            view.updateButtonColor(R.color.gray3);
        }else{
            view.updateButtonColor(R.color.blue_light);
        }
        view.setIsCheck(!flag);
    }

    public void addTeam(String name){
        dbUtils.insertToTeam(name, bookId);
        updateList();
        view.clearEditText();
    }

    public void updateTeam(int id, String name){
        dbUtils.updateFromTeam(id, name);
        updateList();
    }

    private void updateList(){
        list = dbUtils.findAllFromTeam(bookId);
        view.updateView(list);
    }
}
