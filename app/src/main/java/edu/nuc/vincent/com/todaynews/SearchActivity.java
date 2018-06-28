package edu.nuc.vincent.com.todaynews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import edu.nuc.vincent.com.todaynews.adapter.SearchAdapter;
import edu.nuc.vincent.com.todaynews.base.BaseAdapter;
import edu.nuc.vincent.com.todaynews.bean.Search;
import edu.nuc.vincent.com.todaynews.module.home.HomeActivity;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity implements BaseAdapter.OnItemClickListener{

    private static final String TAG = "SearchActivity";

    @InjectView(R.id.search_back)
    ImageView searchBack;
    @InjectView(R.id.search_edit)
    EditText searchEdit;
    @InjectView(R.id.search_recycleview)
    RecyclerView searchRecycleview;
    @InjectView(R.id.text_search)
    TextView textSearch;

    private List<Search.DataBean> mDatas;
    private SearchAdapter mAdapter;

    private Retrofit mRetrofit;
    private GetDatas mGetDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        initView();
    }

    private void initView() {

        searchRecycleview = (RecyclerView)findViewById(R.id.search_recycleview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        searchRecycleview.setItemAnimator(new DefaultItemAnimator());
        searchRecycleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        searchRecycleview.setLayoutManager(layoutManager);
    }

    @OnClick({R.id.search_back,R.id.text_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                finish();
                break;
            case R.id.text_search:
                searchData();
                break;
        }
    }

    /**
     * 查询数据
     */
    private void searchData() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://120.76.205.241:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGetDatas = mRetrofit.create(GetDatas.class);


        Map<String, String> map = new HashMap<>();
        map.put("pageToken", "0");
        map.put("kw", searchEdit.getText().toString());
        map.put("apikey", Constant.APIKEY);

        mDatas = new ArrayList<>();

        mGetDatas.getSearchFromService(map).enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, final Response<Search> response) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {

                            Search search = response.body();

                            if (search.getData() == null) {

                                Toast.makeText(SearchActivity.this, "刷新数据太快", Toast.LENGTH_SHORT).show();
                            } else {

                                mDatas.clear();
                                mDatas = search.getData();


                                initAdapter();
                            }


                        } else {

                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {


            }
        });


    }

    private void initAdapter() {

        Log.d(TAG, "initAdapter: ready to init adapter");

        Log.d(TAG, "initAdapter: adapter is null");
        mAdapter = new SearchAdapter(this, mDatas, R.layout.search_item);

        Log.d(TAG, "initAdapter: adapter is not null");

        mAdapter.setOnItemClickListener(this);
        searchRecycleview.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View view, int position) {
        Search.DataBean bean = mDatas.get(position);
        Intent intent = new Intent(this, HomeActivity.class);
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
        startActivity(intent);
        finish();
    }
}
