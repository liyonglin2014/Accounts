package com.liyonglin.accounts.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.liyonglin.accounts.R;
import com.liyonglin.accounts.adapter.ClassifyListAdapter;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;
import com.liyonglin.accounts.utils.SwipeMenuListViewUtils;

import java.util.List;

public class ClassifyListActivity extends ToolBarActivity2 {

    private SwipeMenuListView lv_classify;
    private List<Classify> classifyList;
    private ClassifyListAdapter adapter;
    private AccountDBUtils dbUtils;
    private int classifyMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_list);
        Intent intent = getIntent();
        if (intent != null) {
            classifyMode = intent.getIntExtra("classifyMode", 0);
        }
        if (classifyMode == FinalAttr.CLASSIFY_MODE_IN){
            setToolbarCenterText(getString(R.string.list_classify_in));
        }else{
            setToolbarCenterText(getString(R.string.list_classify_out));
        }
        initListView();

    }

    private void initListView() {
        dbUtils = new AccountDBUtils(this);
        classifyList = dbUtils.findFromClassifyByMode(classifyMode);
        lv_classify = (SwipeMenuListView) findViewById(R.id.lv_classify);
        adapter = new ClassifyListAdapter(this, classifyList);

        lv_classify.setAdapter(adapter);
        lv_classify.setMenuCreator(SwipeMenuListViewUtils.getCreator(this));
        lv_classify.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        lv_classify.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        final AlertDialog dialog = new AlertDialog.Builder(ClassifyListActivity.this)
                                .setView(R.layout.dialog_classify_delete).create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Button commit = (Button) dialog.findViewById(R.id.bt_classify_delete_commit);
                        Button cancel = (Button) dialog.findViewById(R.id.bt_classify_delete_cancel);
                        assert commit != null;
                        commit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dbUtils.deleteFromClassify(classifyList.get(position).getId());
                                setResult(FinalAttr.UPDATE, null);
                                updateAdapter();
                                dialog.dismiss();
                            }
                        });
                        assert cancel != null;
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        lv_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ClassifyListActivity.this, ClassifyUpdateActivity.class);
                intent.putExtra("classify", classifyList.get(i));
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == FinalAttr.UPDATE){
            updateAdapter();
            setResult(FinalAttr.UPDATE, null);
        }
    }


    private void updateAdapter(){
        classifyList.clear();
        classifyList.addAll(dbUtils.findFromClassifyByMode(classifyMode));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRight() {
        Intent intent = new Intent(this, ClassifyAddActivity.class);
        intent.putExtra("classifyMode", classifyMode);
        startActivityForResult(intent, 0);
    }
}
