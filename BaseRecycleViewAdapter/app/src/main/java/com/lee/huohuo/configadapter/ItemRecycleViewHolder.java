package com.lee.huohuo.configadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Lee
 * @function 跟FatherRecycleadapter公用
 * @date ：16/12/12
 * @mail 1042372838@qq.com
 */
public abstract class ItemRecycleViewHolder<T> extends RecyclerView.ViewHolder {
    private View mView;

    public ItemRecycleViewHolder(View v) {
        super(v);
        mView = v;
    }

    public View getItemRecycleView() {
        return mView;
    }

    abstract public void bindHolder(T t);

}
