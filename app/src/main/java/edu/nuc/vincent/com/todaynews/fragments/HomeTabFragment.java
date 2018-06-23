package edu.nuc.vincent.com.todaynews.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.adapter.HomeAdapter;
import edu.nuc.vincent.com.todaynews.bean.HomeItem;
import edu.nuc.vincent.com.todaynews.utils.ImageUtil;

/**
 * Created by Vincent on 2018/6/20.
 */

public class HomeTabFragment extends Fragment {

    private RecyclerView mRecycleView;
    private HomeAdapter mAdapter;
    private List<HomeItem> mDatas;
    private List<HomeItem> mAllDatas;
    private int itemCount = 5;

    private static final int DATA_LENGTH = 5;//每次显示的数据量

    private MaterialRefreshLayout mRefresh;

    private String mKey;//搜索新闻的关键词

    public static HomeTabFragment newInstance(String key) {

        HomeTabFragment homeTabFragment = new HomeTabFragment();

        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        homeTabFragment.setArguments(bundle);
        return homeTabFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_tab_fragment_home, container, false);

        initDatas();

        initView(view);

        return view;
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        mDatas = new ArrayList<>();
        mAllDatas = new ArrayList<>();
        for (int i = 0; i < DATA_LENGTH; i++) {


            Uri userIconUri = ImageUtil.getUri(getContext(), R.mipmap.ic_launcher);
            String username = "Vincent--" + i;
            Date sendDate = new Date();
            DateFormat df = DateFormat.getDateTimeInstance();
            String sendContent = "我在看世界杯，哈哈哈！";
            Uri imageUri = ImageUtil.getUri(getContext(), R.drawable.image);
            HomeItem item = new HomeItem(userIconUri, username, sendDate, sendContent, imageUri);
            mAllDatas.add(item);

        }

        for (int i = 0; i < 4; i++) {

            mDatas.add(mAllDatas.get(i));

        }
    }

    /**
     * 初始化布局
     *
     * @param view
     */
    private void initView(View view) {

        mRefresh = (MaterialRefreshLayout) view.findViewById(R.id.home_refresh);
        mRefresh.setLoadMore(true);
        mRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                updataDatas();
                mRefresh.finishRefresh();


            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {


                loadDatas();
                mRefresh.finishRefreshLoadMore();

            }
        });

        mRecycleView = (RecyclerView) view.findViewById(R.id.home_recycleView);
        mAdapter = new HomeAdapter(getContext(), mDatas, R.layout.home_tab_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        mKey = bundle.getString("key");

    }

    /**
     * 更新数据
     */
    private void updataDatas() {
        Uri userIconUri = ImageUtil.getUri(getContext(), R.mipmap.ic_launcher);
        String username = "Vincent--" + itemCount;
        Date sendDate = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String sendContent = "我也在看世界杯，哈哈哈！";
        Uri imageUri = ImageUtil.getUri(getContext(), R.drawable.image);
        HomeItem item = new HomeItem(userIconUri, username, sendDate, sendContent, imageUri);

        mAllDatas.add(0, item);

        mDatas.clear();

        for (int i = 0; i < 4; i++) {

            mDatas.add(mAllDatas.get(i));

        }

        mAdapter.notifyDataSetChanged();

        itemCount++;

    }

    /**
     * 加载数据
     */
    private void loadDatas() {

        for (int i = mDatas.size(); i < mDatas.size()+ 4; i++) {

            if (i < mAllDatas.size()) {
                mDatas.add(mAllDatas.get(i));
            } else {
                Toast.makeText(getContext(), "没有更多数据了!", Toast.LENGTH_SHORT).show();
                break;
            }

        }

        mAdapter.notifyDataSetChanged();

    }


}
