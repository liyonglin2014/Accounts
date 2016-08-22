package com.liyonglin.accounts.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.liyonglin.accounts.R;
import com.liyonglin.accounts.adapter.TeamListAdapter;
import com.liyonglin.accounts.bean.Team;
import com.liyonglin.accounts.presenter.TeamListPresenter;
import com.liyonglin.accounts.utils.FinalAttr;
import com.liyonglin.accounts.utils.SwipeMenuListViewUtils;
import com.liyonglin.accounts.view.ITeamListView;

import java.util.List;

public class TeamListActivity extends ToolBarActivity2 implements ITeamListView, View.OnClickListener {

    private TeamListPresenter presenter;
    private SwipeMenuListView listView;
    private TeamListAdapter adapter;
    private EditText et_team_add;
    private Button bt_team_commit;

    private AlertDialog dialog_update;
    private AlertDialog dialog_delete;
    private Button bt_commit;
    private TextView tv_commit;
    private EditText et_update_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        setToolbarCenterText(getString(R.string.chengyuanguanli));
        setToolbarRightVisibility(View.GONE);
        presenter = new TeamListPresenter(this);
        initDialog();
        presenter.initView();
        bt_team_commit.setClickable(false);
    }

    private void initDialog(){
        View view_update = View.inflate(this, R.layout.dialog_team_update, null);
        dialog_update = new AlertDialog.Builder(TeamListActivity.this).setView(view_update).create();
        dialog_update.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog_update.getWindow().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        tv_commit = (TextView) view_update.findViewById(R.id.tv_team_update_commit);
        TextView tv_cancel = (TextView) view_update.findViewById(R.id.tv_team_update_cancel);
        et_update_team = (EditText) view_update.findViewById(R.id.et_team_add_update_name);
        tv_cancel.setOnClickListener(this);
        tv_commit.setOnClickListener(this);

        View view_delete = View.inflate(this, R.layout.dialog_classify_delete, null);
        dialog_delete = new AlertDialog.Builder(TeamListActivity.this).setView(view_delete).create();
        dialog_delete.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv = (TextView) view_delete.findViewById(R.id.tv_delete_tip);
        tv.setText(getString(R.string.delete_commit_team));
        bt_commit = (Button) view_delete.findViewById(R.id.bt_classify_delete_commit);
        Button cancel = (Button) view_delete.findViewById(R.id.bt_classify_delete_cancel);
        bt_commit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void initView(final List<Team> list) {
        listView = (SwipeMenuListView) findViewById(R.id.slv_team);
        adapter = new TeamListAdapter(this, list);
        adapter.setSelect(-1);
        listView.setAdapter(adapter);
        listView.setMenuCreator(SwipeMenuListViewUtils.getCreator(this));
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        dialog_delete.show();
                        bt_commit.setTag(list.get(position).getId());
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog_update.show();
                et_update_team.setText(list.get(i).getName());
                Selection.selectAll(et_update_team.getText());
                tv_commit.setTag(list.get(i).getId());
            }
        });

        et_team_add = (EditText) findViewById(R.id.et_team_add);
        bt_team_commit = (Button) findViewById(R.id.bt_team_commit);
        et_team_add.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.updateButtonColor(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        bt_team_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addTeam(et_team_add.getText().toString());
            }
        });
    }

    @Override
    public void updateView(List<Team> list) {
        adapter.updateAll(list);
        setResult(FinalAttr.UPDATE, null);
    }

    @Override
    public void updateButtonColor(int color) {
        bt_team_commit.setBackgroundResource(color);
    }

    @Override
    public void setIsCheck(boolean flag) {
        bt_team_commit.setClickable(flag);
    }

    @Override
    public void clearEditText() {
        et_team_add.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_classify_delete_commit :
                presenter.deleteTeam((Integer) bt_commit.getTag());
                dialog_delete.dismiss();
                break;
            case R.id.bt_classify_delete_cancel :
                dialog_delete.dismiss();
                break;
            case R.id.tv_team_update_cancel :
                dialog_update.dismiss();
                break;
            case R.id.tv_team_update_commit :
                presenter.updateTeam((Integer) tv_commit.getTag(), et_update_team.getText().toString());
                dialog_update.dismiss();
                break;
        }
    }
}
