package com.liyonglin.accounts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.adapter.IconAdapter;
import com.liyonglin.accounts.bean.Classify;
import com.liyonglin.accounts.utils.AccountDBUtils;
import com.liyonglin.accounts.utils.FinalAttr;

public class ClassifyAddActivity extends ToolBarActivity2 {

    private GridView gv_show_icon;
    private ImageView iv_classify_update;
    private EditText et_classify_update;
    private int classifyMode;
    private AccountDBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent != null){
            classifyMode = intent.getIntExtra("classifyMode", 0);
        }

        setContentView(R.layout.activity_classify_add);
        if (classifyMode == FinalAttr.CLASSIFY_MODE_IN){
            setToolbarCenterText(getString(R.string.add_classify_in));
        }else{
            setToolbarCenterText(getString(R.string.add_classify_out));
        }
        setToolbarRightText(getString(R.string.commit));

        dbUtils = new AccountDBUtils(this);

        iv_classify_update = (ImageView) findViewById(R.id.iv_classify_add);
        et_classify_update = (EditText) findViewById(R.id.et_classify_add);
        iv_classify_update.setImageResource(FinalAttr.ICONS[0]);
        iv_classify_update.setTag(FinalAttr.ICONS[0]);

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
        dbUtils.insertToClassify(new Classify(newName, classifyMode, newImgId));
        setResult(FinalAttr.UPDATE, null);
        finish();
    }

}
