package com.liyonglin.accounts.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.adapter.GalleryAdapter;
import com.liyonglin.accounts.bean.AccountBook;
import com.liyonglin.accounts.fragment.BaseFragment;
import com.liyonglin.accounts.fragment.Fragment_mingxi;
import com.liyonglin.accounts.fragment.Fragment_qianbao;
import com.liyonglin.accounts.fragment.Fragment_tixing;
import com.liyonglin.accounts.fragment.Fragment_zhongxin;
import com.liyonglin.accounts.utils.AccountDBUtils;

import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends ToolBarActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<BaseFragment> fragments;

    private LinearLayout ll_homepageBottom_mingxi;
    private LinearLayout ll_homepageBottom_qianbao;
    private LinearLayout ll_homepageBottom_tixing;
    private LinearLayout ll_homepageBottom_zhongxin;

    private ImageView iv_homepageBottom_mingxi;
    private ImageView iv_homepageBottom_qianbao;
    private ImageView iv_homepageBottom_tixing;
    private ImageView iv_homepageBottom_zhongxin;

    private TextView tv_homepageBottom_mingxi;
    private TextView tv_homepageBottom_qianbao;
    private TextView tv_homepageBottom_tixing;
    private TextView tv_homepageBottom_zhongxin;

    private ImageView iv_account_add;

    private LinearLayout ll_book;
    private RecyclerView rv_select_book;
    private TextView tv_show_bookName;
    private ImageView iv_select_book;
    private GalleryAdapter galleryAdapter;
    private List<AccountBook> books;

    private AccountDBUtils dbUtils;
    private SharedPreferences sp;

    private LinearLayout ll_select_teamBook;
    private LinearLayout ll_select_singleBook;
    private ImageView iv_select_teamBook;
    private ImageView iv_select_singleBook;
    private EditText et_book_add_name;
    private TextView tv_book_add_cancel;
    private TextView tv_book_add_commit;

    private TextView tv_book_update_cancel;
    private TextView tv_book_update_commit;
    private EditText et_book_update_name;
    private TextView tv_book_delete;

    private Button bt_book_delete_commit;
    private Button bt_book_delete_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initView();
        initViewPage();
        initEvent();
        setToolBarEvent();
        selectBook();

    }

    private void setFragmentIsShowBook(int visibility){
        for(BaseFragment fragment : fragments){
            fragment.setIsShowBook(visibility == ViewGroup.GONE);
        }
    }

    private void setToolBarEvent(){
        for(AccountBook book : books){
            if (book.getBookId() == sp.getInt("bookId", -1)){
                tv_show_bookName.setText(book.getBookName());
            }
        }
        ll_book.setOnClickListener(new ViewGroup.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = rv_select_book.getVisibility();
                if(visibility == ViewGroup.GONE){
                    rv_select_book.setVisibility(ViewGroup.VISIBLE);
                    startDownAnimation();
                    iv_select_book.setImageResource(R.mipmap.up);
                }else{
                    startUpAnimation();
                    iv_select_book.setImageResource(R.mipmap.down);
                }
                setFragmentIsShowBook(visibility);
            }
        });
    }

    private void startDownAnimation(){
        TranslateAnimation ta1 = new TranslateAnimation(0.0f, 0.0f,
                -390, 0.0f);
        ta1.setDuration(500);
        ta1.setFillAfter(true);
        toolbar.startAnimation(ta1);
        viewPagerDownAnimation();
    }

    private void viewPagerDownAnimation(){
        TranslateAnimation ta3 = new TranslateAnimation(0.0f, 0.0f,
                0.0f, 390);
        ta3.setDuration(500);
        ta3.setFillAfter(true);
        viewPager.startAnimation(ta3);
    }

    private void viewPagerUpAnimation(){
        TranslateAnimation ta3 = new TranslateAnimation(0.0f, 0.0f,
                390, 0.0f);
        ta3.setDuration(500);
        ta3.setFillAfter(true);
        viewPager.startAnimation(ta3);
    }

    private void startUpAnimation(){
        TranslateAnimation ta2 = new TranslateAnimation(0.0f, 0.0f,
                0.0f, -390);
        ta2.setDuration(500);
        ta2.setFillAfter(true);
        ta2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                rv_select_book.setVisibility(ViewGroup.GONE);
                toolbar.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        toolbar.startAnimation(ta2);
        viewPagerUpAnimation();
    }


    private void selectBook() {
        //设置横向
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_select_book.setLayoutManager(linearLayoutManager);
        galleryAdapter = new GalleryAdapter(this, books);
        galleryAdapter.setOnItemClickListener(new GalleryAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (postion == books.size() - 1){
                    addBook();
                }else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("bookId", books.get(postion).getBookId());
                    editor.commit();
                    tv_show_bookName.setText(books.get(postion).getBookName());
                    galleryAdapter.notifyDataSetChanged();
                    startUpAnimation();
                    iv_select_book.setImageResource(R.mipmap.down);
                }
            }
        });
        galleryAdapter.setOnItemLongClickListener(new GalleryAdapter.MyItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int postion) {
                if (postion != books.size() - 1){
                    updateBook(postion);
                }
            }
        });
        rv_select_book.setAdapter(galleryAdapter);
        //设置RecyclerView 条目的间距
        rv_select_book.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int pos = parent.getChildAdapterPosition(view);
                int spacingVal = 40;
                RecyclerView.Adapter adapter = rv_select_book.getAdapter();
                if (pos == adapter.getItemCount() - 1){
                    spacingVal = 0;
                }
                outRect.set(0, 0, spacingVal, 0);
            }
        });

    }

    private void deleteBook(final int position, final AlertDialog updateDialog){
        final AlertDialog dialog = new AlertDialog.Builder(HomepageActivity.this)
                .setView(R.layout.dialog_book_delete).create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        bt_book_delete_commit = (Button) dialog.findViewById(R.id.bt_book_delete_commit);
        bt_book_delete_cancel = (Button) dialog.findViewById(R.id.bt_book_delete_cancel);
        bt_book_delete_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        bt_book_delete_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bookId = books.get(position).getBookId();
                dbUtils.deleteFromBook(bookId);
                dialog.dismiss();
                updateDialog.dismiss();
                if(bookId == sp.getInt("bookId", -1)){
                    sp.edit().putInt("bookId", books.get(0).getBookId()).commit();
                }
                updateGalleryAdapter();
            }
        });
    }

    private void updateBook(final int postion){
        final AlertDialog dialog = new AlertDialog.Builder(HomepageActivity.this)
                .setView(R.layout.dialog_book_update).create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        tv_book_update_cancel = (TextView) dialog.findViewById(R.id.tv_book_update_cancel);
        tv_book_update_commit = (TextView) dialog.findViewById(R.id.tv_book_update_commit);
        et_book_update_name = (EditText) dialog.findViewById(R.id.et_book_update_name);
        tv_book_delete = (TextView) dialog.findViewById(R.id.tv_book_delete);

        et_book_update_name.setText(books.get(postion).getBookName());

        tv_book_update_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_book_update_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newBookName = et_book_update_name.getText().toString();
                if (TextUtils.isEmpty(newBookName)){
                    Toast.makeText(HomepageActivity.this, "账本名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbUtils.updateFromBook(books.get(postion).getBookId(), newBookName);
                dialog.dismiss();
                updateGalleryAdapter();
            }
        });

        tv_book_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook(postion, dialog);
            }
        });
    }

    private void addBook(){
        final AlertDialog dialog = new AlertDialog.Builder(HomepageActivity.this)
                .setView(R.layout.dialog_book_add).create();
        dialog.show();
        //将对话框window 背景设置为透明
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ll_select_teamBook = (LinearLayout) dialog.findViewById(R.id.ll_select_teamBook);
        ll_select_singleBook = (LinearLayout) dialog.findViewById(R.id.ll_select_singleBook);
        iv_select_teamBook = (ImageView) dialog.findViewById(R.id.iv_select_teamBook);
        iv_select_singleBook = (ImageView) dialog.findViewById(R.id.iv_select_singleBook);
        et_book_add_name = (EditText) dialog.findViewById(R.id.et_book_add_name);
        tv_book_add_cancel = (TextView) dialog.findViewById(R.id.tv_book_add_cancel);
        tv_book_add_commit = (TextView) dialog.findViewById(R.id.tv_book_add_commit);

        ll_select_teamBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = iv_select_teamBook.getVisibility();
                if(visibility == View.GONE){
                    iv_select_teamBook.setVisibility(View.VISIBLE);
                    iv_select_singleBook.setVisibility(View.GONE);
                }
            }
        });

        ll_select_singleBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = iv_select_singleBook.getVisibility();
                if(visibility == View.GONE){
                    iv_select_singleBook.setVisibility(View.VISIBLE);
                    iv_select_teamBook.setVisibility(View.GONE);
                }
            }
        });

        tv_book_add_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_book_add_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookName = et_book_add_name.getText().toString();
                int bookMode;
                if(iv_select_teamBook.getVisibility() == View.VISIBLE){
                    bookMode = 1;
                }else{
                    bookMode = 2;
                }
                dbUtils.insertToBook(bookName, bookMode);
                dialog.dismiss();
                updateGalleryAdapter();
            }
        });
    }

    private void updateGalleryAdapter(){
        books.clear();
        books.addAll(dbUtils.findAllFromBook());
        books.add(new AccountBook("新建账本", 0));
        galleryAdapter.notifyDataSetChanged();
        for(AccountBook book : books){
            if (book.getBookId() == sp.getInt("bookId", -1)){
                tv_show_bookName.setText(book.getBookName());
            }
        }
    }


    private void setTabsSelectedImg(int i) {
        resetImg();
        switch (i) {
            case 0:
                iv_homepageBottom_mingxi.setImageResource(R.mipmap.mingxi_lan);
                tv_homepageBottom_mingxi.setTextColor(getResources().getColor(R.color.blue_light));
                break;
            case 1:
                iv_homepageBottom_qianbao.setImageResource(R.mipmap.qianbao_lan);
                tv_homepageBottom_qianbao.setTextColor(getResources().getColor(R.color.blue_light));
                break;
            case 2:
                iv_homepageBottom_tixing.setImageResource(R.mipmap.tixing_lan);
                tv_homepageBottom_tixing.setTextColor(getResources().getColor(R.color.blue_light));
                break;
            case 3:
                iv_homepageBottom_zhongxin.setImageResource(R.mipmap.zhongxin_lan);
                tv_homepageBottom_zhongxin.setTextColor(getResources().getColor(R.color.blue_light));
                break;
        }

        viewPager.setCurrentItem(i);

    }

    private void initEvent() {
        ll_homepageBottom_mingxi.setOnClickListener(this);
        ll_homepageBottom_qianbao.setOnClickListener(this);
        ll_homepageBottom_tixing.setOnClickListener(this);
        ll_homepageBottom_zhongxin.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setTabsSelectedImg(position);
            }
        });
    }

    private void initViewPage() {
        fragments = new ArrayList<BaseFragment>();

        Fragment_mingxi fragment_mingxi = new Fragment_mingxi();
        Fragment_qianbao fragment_qianbao = new Fragment_qianbao();
        Fragment_tixing fragment_tixing = new Fragment_tixing();
        Fragment_zhongxin fragment_zhongxin = new Fragment_zhongxin();

        fragments.add(fragment_mingxi);
        fragments.add(fragment_qianbao);
        fragments.add(fragment_tixing);
        fragments.add(fragment_zhongxin);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return super.instantiateItem(container, position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    closeBook();
                }
                return false;
            }
        });
    }

    private void initView() {
        dbUtils = new AccountDBUtils(this);
        sp = getSharedPreferences("state", MODE_PRIVATE);
        books = dbUtils.findAllFromBook();
        books.add(new AccountBook("新建账本", 0));

        viewPager = (ViewPager) findViewById(R.id.vp_homepage);

        ll_homepageBottom_mingxi = (LinearLayout) findViewById(R.id.ll_homepageBottom_mingxi);
        ll_homepageBottom_qianbao = (LinearLayout) findViewById(R.id.ll_homepageBottom_qianbao);
        ll_homepageBottom_tixing = (LinearLayout) findViewById(R.id.ll_homepageBottom_tixing);
        ll_homepageBottom_zhongxin = (LinearLayout) findViewById(R.id.ll_homepageBottom_zhongxin);

        iv_homepageBottom_mingxi = (ImageView) findViewById(R.id.iv_homepageBottom_mingxi);
        iv_homepageBottom_qianbao = (ImageView) findViewById(R.id.iv_homepageBottom_qianbao);
        iv_homepageBottom_tixing = (ImageView) findViewById(R.id.iv_homepageBottom_tixing);
        iv_homepageBottom_zhongxin = (ImageView) findViewById(R.id.iv_homepageBottom_zhongxin);

        tv_homepageBottom_mingxi = (TextView) findViewById(R.id.tv_homepageBottom_mingxi);
        tv_homepageBottom_qianbao = (TextView) findViewById(R.id.tv_homepageBottom_qianbao);
        tv_homepageBottom_tixing = (TextView) findViewById(R.id.tv_homepageBottom_tixing);
        tv_homepageBottom_zhongxin = (TextView) findViewById(R.id.tv_homepageBottom_zhongxin);

        iv_account_add = (ImageView) findViewById(R.id.iv_account_add);
        iv_account_add.setOnClickListener(this);

        ll_book = (LinearLayout) toolbar.findViewById(R.id.ll_book);
        rv_select_book = (RecyclerView) toolbar.findViewById(R.id.rv_select_book);
        iv_select_book = (ImageView) toolbar.findViewById(R.id.iv_select_book);
        tv_show_bookName = (TextView) toolbar.findViewById(R.id.tv_show_bookName);
    }


    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.ll_homepageBottom_mingxi:
                setTabsSelectedImg(0);
                break;
            case R.id.ll_homepageBottom_qianbao:
                setTabsSelectedImg(1);
                break;
            case R.id.ll_homepageBottom_tixing:
                setTabsSelectedImg(2);
                break;
            case R.id.ll_homepageBottom_zhongxin:
                setTabsSelectedImg(3);
                break;
            case R.id.iv_account_add:
                startActivity(new Intent(this, AccountAddActivity.class));
                break;
            default:
                break;
        }

    }

    private void resetImg() {
        iv_homepageBottom_mingxi.setImageResource(R.mipmap.mingxi);
        iv_homepageBottom_qianbao.setImageResource(R.mipmap.qianbao);
        iv_homepageBottom_tixing.setImageResource(R.mipmap.tixing);
        iv_homepageBottom_zhongxin.setImageResource(R.mipmap.zhongxin);

        tv_homepageBottom_mingxi.setTextColor(getResources().getColor(R.color.gray));
        tv_homepageBottom_qianbao.setTextColor(getResources().getColor(R.color.gray));
        tv_homepageBottom_tixing.setTextColor(getResources().getColor(R.color.gray));
        tv_homepageBottom_zhongxin.setTextColor(getResources().getColor(R.color.gray));
    }

    public void closeBook(){
        int visibility = rv_select_book.getVisibility();
        if(visibility == ViewGroup.VISIBLE){
            startUpAnimation();
            iv_select_book.setImageResource(R.mipmap.down);
            setFragmentIsShowBook(visibility);
        }
    }
}
