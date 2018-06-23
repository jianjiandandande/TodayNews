package edu.nuc.vincent.com.todaynews.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.adapter.HomeAdapter;
import edu.nuc.vincent.com.todaynews.adapter.VideoAdapter;
import edu.nuc.vincent.com.todaynews.bean.HomeItem;
import edu.nuc.vincent.com.todaynews.bean.VideoItem;
import edu.nuc.vincent.com.todaynews.utils.ImageUtil;

/**
 * Created by Vincent on 2018/6/20.
 */

public class VideoTabFragment extends Fragment {

    private RecyclerView mRecycleView;
    private VideoAdapter mAdapter;
    private List<VideoItem> mDatas;
    private List<VideoItem> mAllDatas;
    private int itemCount = 5;

    private static final int DATA_LENGTH = 5;//每次显示的数据量

    private MaterialRefreshLayout mRefresh;

    private String mKey;//搜索新闻的关键词

    public static VideoTabFragment newInstance(String key) {

        VideoTabFragment homeTabFragment = new VideoTabFragment();

        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        homeTabFragment.setArguments(bundle);
        return homeTabFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_tab_fragment_video,container,false);

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
        for (int i = 0; i < 5; i++) {

            String videoUri = "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";
            Uri userIconUri = ImageUtil.getUri(getContext(),R.mipmap.ic_launcher);
            String username = "Vincent";
            Date sendDate = new Date();
            DateFormat df = DateFormat.getDateTimeInstance();
            String videoTitle = "我在看世界杯，哈哈哈！";

            Uri firstImage = Uri.parse("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg");
            VideoItem item = new VideoItem(videoUri,userIconUri,username,sendDate,videoTitle,firstImage,
                    "2万","2000","1000");
            mAllDatas.add(item);

        }

        for (int i = 0; i < 4; i++) {

            mDatas.add(mAllDatas.get(i));

        }
    }

    /**
     * 初始化布局
     * @param view
     */
    private void initView(View view) {


        mRefresh = (MaterialRefreshLayout)view.findViewById(R.id.video_refresh);
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

        mRecycleView = (RecyclerView)view.findViewById(R.id.video_recycleView);
        mAdapter = new VideoAdapter(getContext(),mDatas,R.layout.video_tab_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);

    }

    /**
     * 更新数据
     */
    private void updataDatas() {

        String videoUri = "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";
        Uri userIconUri = ImageUtil.getUri(getContext(),R.mipmap.ic_launcher);
        String username = "Vincent"+itemCount;
        Date sendDate = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String videoTitle = "我在看世界杯，哈哈哈！";

        Uri firstImage = Uri.parse("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg");
        VideoItem item = new VideoItem(videoUri,userIconUri,username,sendDate,videoTitle,firstImage,
                "2万","2000","1000");
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

        for (int i = mDatas.size(); i < mDatas.size() + 4; i++) {

            if (i < mAllDatas.size()) {
                mDatas.add(mDatas.size(),mAllDatas.get(i));
            } else {
                Toast.makeText(getContext(), "没有更多数据了!", Toast.LENGTH_SHORT).show();
                break;
            }

        }

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        mKey = bundle.getString("key");

    }
}
