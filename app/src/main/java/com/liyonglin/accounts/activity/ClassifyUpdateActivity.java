package com.liyonglin.accounts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.adapter.IconAdapter;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;

public class ClassifyUpdateActivity extends ToolBarActivity2 {

    private GridView gv_show_icon;
    private ImageView iv_classify_update;
    private EditText et_classify_update;
    private Classify classify;
    private AccountDBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_update);
        setToolbarCenterText(getString(R.string.update_classify));
        setToolbarRightText(getString(R.string.commit));

        dbUtils = new AccountDBUtils(this);

        Intent intent = getIntent();
        if(intent != null){
            classify = (Classify) intent.getSerializableExtra("classify");
        }

        iv_classify_update = (ImageView) findViewById(R.id.iv_classify_update);
        et_classify_update = (EditText) findViewById(R.id.et_classify_update);
        iv_classify_update.setImageResource(classify.getImgId());
        iv_classify_update.setTag(classify.getImgId());
        et_classify_update.setText(classify.getName());
        //默认选中全部
        Selection.selectAll(et_classify_update.getText());

        gv_show_icon = (GridView) findViewById(R.id.gv_show_icon);
        gv_show_icon.setAdapter(new IconAdapter(this));
        gv_show_icon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                iv_classify_update.setImageResource(FinalAttr.ICONS[i]);
                iv_classify_update.setTag(FinalAttr.ICONS[i]);
            }
        });
    }

    @Override
    public void onRight() {
        String newName = et_classify_update.getText().toString();
        int newImgId = (int) iv_classify_update.getTag();
        dbUtils.updateFromClassify(classify.getId(), newName, newImgId);
        setResult(FinalAttr.UPDATE, null);
        finish();
    }

}
