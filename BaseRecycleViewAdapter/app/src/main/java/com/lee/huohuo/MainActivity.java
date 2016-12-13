package com.lee.huohuo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lee.huohuo.adapter.MainActivityAdapter;
import com.lee.huohuo.configadapter.FatherRecycleViewAdapter;
import com.lee.huohuo.model.MainActivityBean;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String TAG = "MainActivity";

    @ViewInject(R.id.mainActivity_RecyclerView)
    private RecyclerView mainActivity_RecyclerView;
    private LinearLayoutManager layoutManager;
    @ViewInject(R.id.mainActivity_SwipeRefreshLayout)
    private SwipeRefreshLayout mainActivity_SwipeRefreshLayout;
    private MainActivityAdapter mainActivity_Adapter;
    private List<MainActivityBean> mainActivityBean_list;

    private final int REFRESH_DATATYPE = 0;
    private final int UPDATE_DATATYPE = 1;

    private int LOAD_PAGE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        initValue();
        initListener();
        getData(REFRESH_DATATYPE);

    }

    private void initValue() {
        mainActivityBean_list = new ArrayList<>();
        mainActivity_Adapter = new MainActivityAdapter(mainActivityBean_list, MainActivity.this);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mainActivity_RecyclerView.setLayoutManager(layoutManager);
        mainActivity_RecyclerView.setAdapter(mainActivity_Adapter);
    }

    private void initListener() {
        mainActivity_SwipeRefreshLayout.setOnRefreshListener(this);
        mainActivity_Adapter.setOnItemClickListener(new FatherRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();

            }
        });

        //FooterView默认是显示的,当Adapter的bind开始绘制FooterView时，会回调用此接口
        mainActivity_Adapter.setOnGetDataListener(new FatherRecycleViewAdapter.OnGetDataListener() {
            @Override
            public void onGetData() {
                getData(UPDATE_DATATYPE);

            }
        });
    }

    private void getData(int type) {
        if (type == REFRESH_DATATYPE) {
            mainActivityBean_list.clear();
        }
        RequestParams params = new RequestParams("http://cn.bing.com/");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "MainActivity======onSuccess========" + result);
                String localResult = "[{\"username\":\"Tom\",\"password\":\"Tom123456\"},{\"username\":\"Jack\",\"password\":\"Jack123456\"},{\"username\":\"Zach\",\"password\":\"Zach123456\"},{\"username\":\"Jack\",\"password\":\"Jack123456\"},{\"username\":\"Zach\",\"password\":\"Zach123456\"},{\"username\":\"Jack\",\"password\":\"Jack123456\"},{\"username\":\"Zach\",\"password\":\"Zach123456\"},{\"username\":\"Jack\",\"password\":\"Jack123456\"},{\"username\":\"Zach\",\"password\":\"Zach123456\"}]";
                String localResult_SIZE_ZERO = "[]";

                List<MainActivityBean> temp_list = JSON.parseArray(localResult, MainActivityBean.class);
                mainActivityBean_list.addAll(temp_list);
                LOAD_PAGE++;

                //请自行加减Json更改判断的数字标识
                if (LOAD_PAGE == 3) {
                    mainActivity_Adapter.setShowFooter(false);
                } else {
                    mainActivity_Adapter.setShowFooter(true);
                }
                if (temp_list.size() == 0) {
                    mainActivity_Adapter.setShowEmpty(true);
                } else {
                    mainActivity_Adapter.setShowEmpty(false);
                }
                mainActivity_Adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "MainActivity======onError========" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG, "MainActivity======onCancelled========" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                mainActivity_SwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "MainActivity======onFinished========");
            }

            @Override
            public boolean onCache(String result) {
                Log.e(TAG, "MainActivity======onCache========" + result);
                return false;
            }
        });

    }


    @Override
    public void onRefresh() {
        getData(REFRESH_DATATYPE);

    }
}
