package edu.nuc.vincent.com.todaynews.module.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goach.refreshlayout.widget.PullRefreshLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.nuc.vincent.com.todaynews.GetDatas;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.adapter.VideoAdapter;
import edu.nuc.vincent.com.todaynews.base.BaseAdapter;
import edu.nuc.vincent.com.todaynews.bean.News;
import edu.nuc.vincent.com.todaynews.bean.Video;
import edu.nuc.vincent.com.todaynews.bean.VideoItem;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import edu.nuc.vincent.com.todaynews.utils.ImageUtil;
import edu.nuc.vincent.com.todaynews.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vincent on 2018/6/20.
 */

public class VideoTabFragment extends Fragment implements BaseAdapter.OnItemClickListener{

    private RecyclerView mRecycleView;
    private VideoAdapter mAdapter;
    private List<Video.DataBean> mDatas;

    private PullRefreshLayout mRefresh;

    private String mKey;//搜索新闻的关键词

    private Retrofit mRetrofit;

    private GetDatas mGetDatas;

    private boolean isInitDataed;
    private boolean isInitUIed;

    public static VideoTabFragment newInstance(String key) {

        VideoTabFragment homeTabFragment = new VideoTabFragment();

        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        homeTabFragment.setArguments(bundle);
        return homeTabFragment;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (!isInitDataed && isInitUIed) {
                loadDatas();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_tab_fragment_video,container,false);



        initView(view);

        if(mDatas==null||mDatas.size()==0) {
            mDatas = new ArrayList<>();

            L.d(mKey);
        }

        if (!isInitDataed && getUserVisibleHint()) {
            loadDatas();
        }
        return view;
    }



    /**
     * 初始化布局
     * @param view
     */
    private void initView(View view) {


        mRefresh = (PullRefreshLayout)view.findViewById(R.id.video_refresh);
        mRefresh.setTTRefreshLayoutListener(new PullRefreshLayout.TTRefreshLayoutListener() {

            @Override
            public void onTTRefresh() {

                loadDatas();

            }
        });

        mRecycleView = (RecyclerView)view.findViewById(R.id.video_recycleView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        mRecycleView.setLayoutManager(layoutManager);

        isInitUIed = true;

        if (mDatas!=null&&mDatas.size()>0) {

            initAdapter();
        }
    }

    private void initAdapter() {

        mAdapter = new VideoAdapter(getContext(),mDatas,R.layout.video_tab_item);

        mAdapter.setOnItemClickListener(this);
        mRecycleView.setAdapter(mAdapter);
    }


    /**
     * 加载数据
     */
    private void loadDatas() {


        isInitDataed = true;
        Map<String,String> map = new HashMap<>();
        map.put("pageToken", "1");
        map.put("catid", mKey);
        map.put("apikey", Constant.APIKEY);
        mGetDatas.getVideos(map).enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                if (response.isSuccessful()) {

                    Video video = response.body();

                    if (video.getData() == null) {

                        Toast.makeText(getContext(), "刷新数据太快", Toast.LENGTH_SHORT).show();
                    } else {

                        mDatas.clear();
                        mDatas = video.getData();


                        mRefresh.endRefresh();
                        initAdapter();
                    }


                } else {

                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {

            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        mKey = bundle.getString("key");


        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://api01.bitspaceman.com:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGetDatas = mRetrofit.create(GetDatas.class);



    }

    @Override
    public void onClick(View view, int position) {

        Video.DataBean item = mDatas.get(position);
        Intent intent = new Intent(getContext(), VideoActivity.class);
        intent.putExtra("video_uri",item.getVideoUrls().get(0));
        intent.putExtra("title",item.getTitle());
        intent.putExtra("love_count",String.valueOf(item.getLikeCount()));
        intent.putExtra("uid",item.getPosterId());
        intent.putExtra("id",item.getId());
        startActivity(intent);

    }
}
