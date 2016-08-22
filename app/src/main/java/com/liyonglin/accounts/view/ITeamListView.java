package com.liyonglin.accounts.view;

import com.liyonglin.accounts.bean.Team;

import java.util.List;

/**
 * Created by 永霖 on 2016/8/5.
 */
public interface ITeamListView {

    void initView(List<Team> list);

    void updateView(List<Team> list);

    void updateButtonColor(int color);

    void setIsCheck(boolean flag);

    void clearEditText();
}
