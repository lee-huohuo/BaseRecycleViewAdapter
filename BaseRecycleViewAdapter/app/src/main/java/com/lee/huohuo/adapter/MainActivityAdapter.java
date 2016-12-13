package com.lee.huohuo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lee.huohuo.R;
import com.lee.huohuo.configadapter.FatherRecycleViewAdapter;
import com.lee.huohuo.configadapter.ItemRecycleViewHolder;
import com.lee.huohuo.model.MainActivityBean;

import java.util.List;

/**
 * Created by lee
 * Date 16-12-13.
 */

public class MainActivityAdapter extends FatherRecycleViewAdapter {
    private List<MainActivityBean> mList;
    private Context mContext;

    public MainActivityAdapter(List<MainActivityBean> lists, Context context) {
        super(lists, context);
        mList = lists;
        mContext = context;
    }

    private class MainActivityViewHolder extends ItemRecycleViewHolder<MainActivityBean> {
        TextView itemMainActivityUserName_TextView, itemMainActivityPassWord_TextView;

        public MainActivityViewHolder(View v) {
            super(v);
            itemMainActivityUserName_TextView = (TextView) v.findViewById(R.id.itemMainActivityUserName_TextView);
            itemMainActivityPassWord_TextView = (TextView) v.findViewById(R.id.itemMainActivityPassWord_TextView);
        }

        @Override
        public void bindHolder(MainActivityBean bean) {
            itemMainActivityUserName_TextView.setText(bean.getUsername());
            itemMainActivityPassWord_TextView.setText(bean.getPassword());
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_mainactivity;
    }

    @Override
    protected ItemRecycleViewHolder getItemViewHolder(View view) {
        return new MainActivityViewHolder(view);
    }

}
