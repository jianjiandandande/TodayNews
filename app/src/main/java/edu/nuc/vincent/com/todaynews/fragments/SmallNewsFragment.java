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
import edu.nuc.vincent.com.todaynews.bean.HomeItem;
import edu.nuc.vincent.com.todaynews.utils.ImageUtil;

/**
 * Created by Vincent on 2018/6/20.
 */

public class SmallNewsFragment extends Fragment {

    private RecyclerView mRecycleView;
    private HomeAdapter mAdapter;
    private List<HomeItem> mDatas;
    private List<HomeItem> mAllDatas;
    private int itemCount = 5;

    private static final int DATA_LENGTH = 5;//每次显示的数据量

    private MaterialRefreshLayout mRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_small_news,container,false);

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


            Uri userIconUri = ImageUtil.getUri(getContext(),R.mipmap.ic_launcher);
            String username = "Vincent";
            Date sendDate = new Date();
            DateFormat df = DateFormat.getDateTimeInstance();
            String sendContent = "根据各地此前公布的时间,今日起,各地将陆续开通2018年高考成绩的查询通道,高考成绩公布后,\n" +
                    "\" +\n" +"高校的招录工作也逐渐拉开大幕。成绩公布后,考生填报志愿需要注意啥?今年高校招录政策有啥变化?资料图:2018年高考结束,家长为刚刚走出考场的孩子拍照留念。 王以照 摄\n" +
                    "\n" +
                    "各地高考成绩将陆续公布\n" +
                    "\n" +
                    "今年全国高考结束之后,各省份陆续公布了高考成绩查询的时间,今天开始,各地的高考成绩将陆续公布。\n" +
                    "\n" +
                    "其中,最早公布高考成绩的省份有甘肃和四川。根据甘肃省教育考试院发布的消息,甘肃的高考成绩将于今日14时左右公布,而四川的高考成绩公布时间则为今天晚上。\n" +
                    "\n" +
                    "此外,根据各地此前公布的消息,北京、上海、安徽、湖北、内蒙古等地的高考成绩将于23日公布；天津、吉林、山西、黑龙江等地则将放榜时间定于24日；河南、西藏等地的考生则可于25日查询高考成绩；湖南将于本月26日公布高考成绩和录取控制分数线。";
            Uri imageUri = ImageUtil.getUri(getContext(),R.drawable.ic_small_news);
            HomeItem item = new HomeItem(userIconUri,username,sendDate,sendContent,imageUri);
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

        mRefresh = (MaterialRefreshLayout)view.findViewById(R.id.small_refresh);
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

        mRecycleView = (RecyclerView)view.findViewById(R.id.small_recycleView);
        mAdapter = new HomeAdapter(getContext(),mDatas,R.layout.home_tab_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);

    }

    /**
     * 更新数据
     */
    private void updataDatas() {

        Uri userIconUri = ImageUtil.getUri(getContext(),R.mipmap.ic_launcher);
        String username = "Vincent"+itemCount;
        Date sendDate = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String sendContent = "根据各地此前公布的时间,今日起,各地将陆续开通2018年高考成绩的查询通道,高考成绩公布后,\n" +
                "\" +\n" +"高校的招录工作也逐渐拉开大幕。成绩公布后,考生填报志愿需要注意啥?今年高校招录政策有啥变化?资料图:2018年高考结束,家长为刚刚走出考场的孩子拍照留念。 王以照 摄\n" +
                "\n" +
                "各地高考成绩将陆续公布\n" +
                "\n" +
                "今年全国高考结束之后,各省份陆续公布了高考成绩查询的时间,今天开始,各地的高考成绩将陆续公布。\n" +
                "\n" +
                "其中,最早公布高考成绩的省份有甘肃和四川。根据甘肃省教育考试院发布的消息,甘肃的高考成绩将于今日14时左右公布,而四川的高考成绩公布时间则为今天晚上。\n" +
                "\n" +
                "此外,根据各地此前公布的消息,北京、上海、安徽、湖北、内蒙古等地的高考成绩将于23日公布；天津、吉林、山西、黑龙江等地则将放榜时间定于24日；河南、西藏等地的考生则可于25日查询高考成绩；湖南将于本月26日公布高考成绩和录取控制分数线。";
        Uri imageUri = ImageUtil.getUri(getContext(),R.drawable.ic_small_news);
        HomeItem item = new HomeItem(userIconUri,username,sendDate,sendContent,imageUri);
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

        for (int i = mDatas.size() ; i < mDatas.size() + 4; i++) {

            if (i < mAllDatas.size()) {
                mDatas.add(mDatas.size(),mAllDatas.get(i));
            } else {
                Toast.makeText(getContext(), "没有更多数据了!", Toast.LENGTH_SHORT).show();
                break;
            }

        }

        mAdapter.notifyDataSetChanged();


    }
}
