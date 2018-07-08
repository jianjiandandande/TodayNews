package edu.nuc.vincent.com.todaynews.module.news;

import android.content.Intent;
import android.os.Bundle;
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


import com.goach.refreshlayout.widget.PullRefreshLayout;
import com.lieweisi.loadinglib.LoadingDialog;
import com.lieweisi.loadinglib.LoadingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.nuc.vincent.com.todaynews.GetDatas;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.adapter.NewsAdapter;
import edu.nuc.vincent.com.todaynews.base.BaseAdapter;
import edu.nuc.vincent.com.todaynews.entity.News;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import edu.nuc.vincent.com.todaynews.utils.HttpHelper;
import edu.nuc.vincent.com.todaynews.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vincent on 2018/6/20.
 */

public class NewsTabFragment extends Fragment implements BaseAdapter.OnItemClickListener {

    private static final String TAG = "NewsTabFragment";
    private RecyclerView mRecycleView;
    private NewsAdapter mAdapter;
    private List<News.DataBean> mDatas;

    private View mView;

    private PullRefreshLayout mRefresh;

    private String mKey;//搜索新闻的关键词


    private Retrofit mRetrofit;

    private GetDatas mGetDatas;

    private boolean isInitDataed;
    private boolean isInitUIed;

    private LoadingDialog mDialog;


    public static NewsTabFragment newInstance(String key) {

        NewsTabFragment newsTabFragment = new NewsTabFragment();

        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        newsTabFragment.setArguments(bundle);

        return newsTabFragment;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (!isInitDataed && isInitUIed) {
                loadData();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.layout_tab_fragment_home, container, false);

        initView(mView);

        if(mDatas==null||mDatas.size()==0) {
            mDatas = new ArrayList<>();

            L.d(mKey);
        }

        if (!isInitDataed && getUserVisibleHint()) {
            loadData();
        }

        return mView;
    }


    /**
     * 初始化布局
     *
     * @param view
     */
    private void initView(View view) {

        mRefresh = (PullRefreshLayout) view.findViewById(R.id.home_refresh);
        mRefresh.setTTRefreshLayoutListener(new PullRefreshLayout.TTRefreshLayoutListener() {
            @Override
            public void onTTRefresh() {
                loadData();
            }
        });


        mRecycleView = (RecyclerView) view.findViewById(R.id.home_recycleView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecycleView.setLayoutManager(layoutManager);

        isInitUIed = true;

        if (mDatas != null && mDatas.size() > 0) {
            initAdapter();
        }

    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {

        Log.d(TAG, "initAdapter: ready to init adapter");

        Log.d(TAG, "initAdapter: adapter is null");
        mAdapter = new NewsAdapter(getContext(), mDatas, R.layout.home_tab_item);

        Log.d(TAG, "initAdapter: adapter is not null");

        mAdapter.setOnItemClickListener(this);
        mRecycleView.setAdapter(mAdapter);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        mKey = bundle.getString("key");

        L.d(mKey + "onCreate");

        mRetrofit = new HttpHelper.Builder().baseUrl(Constant.API_BASE_URL).build();

    }


    /**
     * 加载网络数据
     */

    public void loadData() {

        isInitDataed = true;
        mDialog = LoadingUtil.show(mDialog,getContext(),LoadingUtil.TYPE_1);
        mDialog.setCancelable(false);
        mGetDatas = mRetrofit.create(GetDatas.class);
        L.d(mKey + "loadDatas");
        Map<String, String> map = new HashMap<>();
        map.put("pageToken", "0");
        map.put("contentType", "application/json");
        map.put("catid", mKey);
        map.put("apikey", Constant.APIKEY);

        mGetDatas.getNewsFromService(map).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, final Response<News> response) {


                if (response.isSuccessful()) {

                    News news = response.body();
                    Log.d(TAG, "loadDatas: loaddata is success" + mKey);
                    if (news.getData() == null) {
                        Log.d(TAG, "loadDatas: loaddata is null" + mKey);
                        Toast.makeText(getContext(), "刷新数据太快", Toast.LENGTH_SHORT).show();
                    } else {

                        mDatas.clear();
                        mDatas = news.getData();

                        Log.d(TAG, "loadDatas: loaddata is not null and size is " + mDatas.size() + mKey);
                        mRefresh.endRefresh();
                        initAdapter();
                        mDialog.dismiss();
                    }


                } else {

                }


            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {


            }
        });

    }


    @Override
    public void onClick(View view, int position) {

        News.DataBean bean = mDatas.get(position);
        Intent intent = new Intent(getContext(), NewsActivity.class);
        intent.putExtra("title", bean.getTitle());
        intent.putExtra("date", String.valueOf(bean.getPublishDateStr()));
        intent.putExtra("content", bean.getContent());
        intent.putExtra("image_uri", bean.getImageUrls() == null ?
                "https://p3.pstatp.com/large/pgc-image/15275844527347c09907875" : bean.getImageUrls().get(0));
        intent.putExtra("skim_count", String.valueOf(bean.getViewCount()));
        intent.putExtra("comment_count", String.valueOf(bean.getCommentCount()));
        intent.putExtra("set_love_count", String.valueOf(bean.getLikeCount()));
        intent.putExtra("uid", String.valueOf(bean.getPosterId()));
        intent.putExtra("id", bean.getId());
        intent.putExtra("collection_state", false);
        startActivity(intent);

    }

    @Override
    public void onPause() {
        super.onPause();
        onDestroy();
    }


}
