package edu.nuc.vincent.com.todaynews.module.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.goach.refreshlayout.widget.PullRefreshLayout;
import com.lieweisi.loadinglib.LoadingDialog;
import com.lieweisi.loadinglib.LoadingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import edu.nuc.vincent.com.todaynews.GetDatas;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.adapter.HistoryAdapter;
import edu.nuc.vincent.com.todaynews.base.BaseAdapter;
import edu.nuc.vincent.com.todaynews.entity.HistoryItem;
import edu.nuc.vincent.com.todaynews.entity.News;
import edu.nuc.vincent.com.todaynews.module.news.NewsActivity;
import edu.nuc.vincent.com.todaynews.module.smallnew.SmallActivity;
import edu.nuc.vincent.com.todaynews.module.video.VideoActivity;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import edu.nuc.vincent.com.todaynews.utils.HttpHelper;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CollectionActivity extends AppCompatActivity implements BaseAdapter.OnItemClickListener{

    @InjectView(R.id.btn_back)
    ImageView btnBack;
    @InjectView(R.id.collection_recycle)
    RecyclerView mRecycleView;
    @InjectView(R.id.collection_refresh)
    PullRefreshLayout mRefresh;

    private HistoryAdapter mAdapter;
    private List<HistoryItem> mDatas;

    private Retrofit mRetrofit;

    private GetDatas mGetDatas;

    private LoadingDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.inject(this);

        initView();
        mRetrofit = new HttpHelper.Builder().baseUrl(Constant.WEB_BASE_URL).build();
        mGetDatas = mRetrofit.create(GetDatas.class);

        if (mDatas == null || mDatas.size() == 0) {
            mDatas = new ArrayList<>();
        }

        loadData();
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }

    /**
     * 初始化布局
     */
    private void initView() {

        mRefresh = (PullRefreshLayout) findViewById(R.id.collection_refresh);
        mRefresh.setTTRefreshLayoutListener(new PullRefreshLayout.TTRefreshLayoutListener() {
            @Override
            public void onTTRefresh() {
                loadData();
            }
        });


        mRecycleView = (RecyclerView) findViewById(R.id.collection_recycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecycleView.setLayoutManager(layoutManager);


        if (mDatas != null && mDatas.size() > 0) {
            initAdapter();
        }

    }

    private void loadData() {
        mDialog = LoadingUtil.show(mDialog, this, LoadingUtil.TYPE_1);
        mDialog.setCancelable(false);
        mGetDatas = mRetrofit.create(GetDatas.class);
        SharedPreferences getData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        boolean loginState = getData.getBoolean("loginState", false);

        int userId = 0;

        if (loginState == true) {

            userId = getData.getInt("userId", 0);


            Map<String, String> map = new HashMap<>();
            map.put("userId", String.valueOf(userId));

            mGetDatas.queryCollection(map).enqueue(new Callback<List<HistoryItem>>() {
                @Override
                public void onResponse(Call<List<HistoryItem>> call, final Response<List<HistoryItem>> response) {


                    if (response.isSuccessful()) {

                        if (response.body() == null) {

                            Toast.makeText(CollectionActivity.this, "刷新数据太快", Toast.LENGTH_SHORT).show();
                        } else {

                            mDatas.clear();
                            mDatas = response.body();

                            mRefresh.endRefresh();
                            initAdapter();
                            mDialog.dismiss();
                        }


                    } else {

                    }

                }

                @Override
                public void onFailure(Call<List<HistoryItem>> call, Throwable t) {
                    Toasty.error(CollectionActivity.this, "获取历史失败", Toast.LENGTH_SHORT, true);
                }
            });
        } else {
            mDialog.dismiss();
            Toasty.error(CollectionActivity.this, "您还未登录", Toast.LENGTH_SHORT, true);
        }

    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {


        mAdapter = new HistoryAdapter(CollectionActivity.this, mDatas, R.layout.history_item);

        mAdapter.setOnItemClickListener(this);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view, int position) {

        HistoryItem item= mDatas.get(position);
        Intent intent = null;
        if (item.getModel().equals("1")){

            intent = new Intent(this, NewsActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("date", item.getAuthor());
            intent.putExtra("content", item.getContent());
            intent.putExtra("image_uri", item.getImageUrl() == null ?
                    "https://p3.pstatp.com/large/pgc-image/15275844527347c09907875" : item.getImageUrl());
            intent.putExtra("skim_count", item.getSkimCount());
            intent.putExtra("comment_count", item.getCommentCount());
            intent.putExtra("set_love_count", item.getLoveCount());
            intent.putExtra("uid", item.getUid());
            intent.putExtra("id", item.getId());
            intent.putExtra("collection_state", true);


        }else if (item.getModel().equals("2")){
            intent = new Intent(this, VideoActivity.class);
            intent.putExtra("video_uri",item.getVideoUrl());
            intent.putExtra("title",item.getTitle());
            intent.putExtra("love_count",item.getLoveCount());
            intent.putExtra("uid",item.getUid());
            intent.putExtra("id",item.getId());
            intent.putExtra("collection_state", true);

        }else if(item.getModel().equals("3")){
            intent = new Intent(this, SmallActivity.class);
            intent.putExtra("date",item.getAuthor());
            intent.putExtra("content",item.getContent());
            intent.putExtra("skim_count",item.getSkimCount());
            intent.putExtra("comment_count",item.getCommentCount());
            intent.putExtra("set_love_count","10");
            intent.putExtra("uid","央视新闻");
            intent.putExtra("id",item.getId());
            intent.putExtra("collection_state", true);
        }

        startActivity(intent);
    }
}
