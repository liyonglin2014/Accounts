package com.liyonglin.accounts.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.bean.AccountBook;

import java.util.List;

/**
 * Created by 永霖 on 2016/7/25.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ItemViewHolder > {

    private List<AccountBook> lists;
    private Context mContext;
    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;

    public GalleryAdapter(Context context, List<AccountBook> datats){
        mContext = context;
        lists = datats;
    }

    public class ItemViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView tv_rv_item_bookname;
        RelativeLayout rl_rv_item_book;
        ImageView iv_gou_book;
        private MyItemClickListener mListener;
        private MyItemLongClickListener mLongClickListener;

        public ItemViewHolder (View view, MyItemClickListener listener,
                               MyItemLongClickListener longClickListener){
            super(view);
            tv_rv_item_bookname = (TextView) view.findViewById(R.id.tv_rv_item_bookname);
            rl_rv_item_book = (RelativeLayout) view.findViewById(R.id.rl_rv_item_book);
            iv_gou_book = (ImageView) view.findViewById(R.id.iv_gou_book);
            this.mListener = listener;
            this.mLongClickListener = longClickListener;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onItemClick(view,getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(mLongClickListener != null){
                mLongClickListener.onItemLongClick(view, getAdapterPosition());
            }
            return true;
        }
    }

    @Override
    public int getItemCount()
    {
        return lists.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        ItemViewHolder v = new ItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_item_book, viewGroup, false),
                this.mItemClickListener, this.mItemLongClickListener);

        return v;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder viewHolder, final int i){
        viewHolder.tv_rv_item_bookname.setText(lists.get(i).getBookName());
        if (lists.get(i).getBookMode() == 0){
            viewHolder.tv_rv_item_bookname.setTextColor(mContext.getResources().getColor(R.color.gray));
            viewHolder.rl_rv_item_book.setBackgroundResource(R.mipmap.add_book);
        }else{
            viewHolder.tv_rv_item_bookname.setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.rl_rv_item_book.setBackgroundResource(R.mipmap.book);
        }
        int bookId = mContext.getSharedPreferences("state", Context.MODE_PRIVATE).getInt("bookId", -1);
        if(bookId == lists.get(i).getBookId()){
            viewHolder.iv_gou_book.setVisibility(View.VISIBLE);
        }else {
            viewHolder.iv_gou_book.setVisibility(View.GONE);
        }
    }

    public interface MyItemClickListener {
        public void onItemClick(View view,int postion);
    }

    public interface MyItemLongClickListener {
        public void onItemLongClick(View view,int postion);
    }

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }
}
