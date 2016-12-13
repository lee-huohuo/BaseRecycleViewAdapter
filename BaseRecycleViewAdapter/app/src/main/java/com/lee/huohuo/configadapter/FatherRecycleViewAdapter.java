package com.lee.huohuo.configadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lee.huohuo.R;

import java.util.List;

/**
 * Created by Lee
 * Date 16-12-13
 */

public abstract class FatherRecycleViewAdapter<T, M extends ItemRecycleViewHolder> extends RecyclerView.Adapter {
    private final int INDEX_CONTENT = 0;
    private final int INDEX_FOOTER = 1;
    private final int INDEX_EMPTY = 2;
    private boolean isShowFooter = true;
    private boolean isShowEmpty = false;

    public boolean isShowFooter() {
        return isShowFooter;
    }

    public void setShowFooter(boolean showFooter) {
        isShowFooter = showFooter;
    }

    public boolean isShowEmpty() {
        return isShowEmpty;
    }

    public void setShowEmpty(boolean showEmpty) {
        isShowEmpty = showEmpty;
    }

    private List<T> mList;
    private Context mContext;


    public FatherRecycleViewAdapter(List<T> lists, Context context) {
        mList = lists;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup group, int position) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (position) {
            case INDEX_CONTENT:
                view = LayoutInflater.from(mContext).inflate(getItemLayoutId(), group, false);
                viewHolder = getItemViewHolder(view);
                break;
            case INDEX_FOOTER:
                //如果其子类传了布局ID,那么使用它,否则使用默认布局
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_view_footer, group, false);
                viewHolder = new _FooterViewHolder(view);

                break;
            case INDEX_EMPTY:
                //同上
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_view_empty, group, false);
                viewHolder = new _EMPTYViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case INDEX_CONTENT:
                ItemRecycleViewHolder itemRecycleViewHolder = (ItemRecycleViewHolder) holder;
                itemRecycleViewHolder.bindHolder(mList.get(position));
                itemRecycleViewHolder.getItemRecycleView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.OnItemClick(position);
                        }
                    }
                });

                break;
            case INDEX_FOOTER:
                _FooterViewHolder footerViewHolder = (_FooterViewHolder) holder;
                if (isShowFooter()) {
                    footerViewHolder.viewFooterDoingUpData_LinearLayout.setVisibility(View.VISIBLE);
                    footerViewHolder.viewFooterNotMoreData_TextView.setVisibility(View.GONE);
                    mOnGetDataListener.onGetData();
                } else {
                    footerViewHolder.viewFooterDoingUpData_LinearLayout.setVisibility(View.GONE);
                    footerViewHolder.viewFooterNotMoreData_TextView.setVisibility(View.VISIBLE);
                }
                break;
            case INDEX_EMPTY:
                _EMPTYViewHolder emptyViewHolder = (_EMPTYViewHolder) holder;
                if (isShowEmpty()) {
                    emptyViewHolder.view.setVisibility(View.VISIBLE);
                } else {
                    emptyViewHolder.view.setVisibility(View.GONE);
                }
                break;
        }


    }


    private class _FooterViewHolder extends RecyclerView.ViewHolder {

        View view;
        LinearLayout viewFooterDoingUpData_LinearLayout;
        TextView viewFooterNotMoreData_TextView;

        public _FooterViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.load_layout);
            viewFooterDoingUpData_LinearLayout = (LinearLayout) itemView.findViewById(R.id.viewFooterDoingUpData_LinearLayout);
            viewFooterNotMoreData_TextView = (TextView) itemView.findViewById(R.id.viewFooterNotMoreData_TextView);

        }
    }

    private class _EMPTYViewHolder extends RecyclerView.ViewHolder {

        View view;

        public _EMPTYViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.viewEmpty_LinearLayout);

        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() == 0) {
            return mList.size() + 1;
        }
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() == 0) {
            return INDEX_EMPTY;
        }
        if (mList.size() == position) {
            return INDEX_FOOTER;
        }
        return INDEX_CONTENT;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnGetDataListener {
        void onGetData();
    }

    private OnGetDataListener mOnGetDataListener;

    public void setOnGetDataListener(OnGetDataListener mOnGetDataListener) {
        this.mOnGetDataListener = mOnGetDataListener;
    }


    protected abstract int getItemLayoutId();

    protected abstract M getItemViewHolder(View view);
}
