package edu.nuc.vincent.com.todaynews.module.smallnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import edu.nuc.vincent.com.todaynews.adapter.EssayAdapter;
import edu.nuc.vincent.com.todaynews.base.BaseAdapter;
import edu.nuc.vincent.com.todaynews.entity.Essay;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vincent on 2018/6/20.
 */

public class SmallNewsFragment extends Fragment implements BaseAdapter.OnItemClickListener{

    private RecyclerView mRecycleView;
    private EssayAdapter mAdapter;
    private List<Essay.DataBean> mDatas;

    private Retrofit mRetrofit;

    private GetDatas mGetDatas;

    private View mView;

    private PullRefreshLayout mRefresh;

    private String uid = "4492956276_4492956276";

    private LoadingDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.layout_fragment_small_news,container,false);

        initView(mView);

        if(mDatas==null||mDatas.size()==0) {
            mDatas = new ArrayList<>();
            loadDatas();

        }

        return mView;
    }


    /**
     * 初始化布局
     * @param view
     */
    private void initView(View view) {

        mRefresh = (PullRefreshLayout)view.findViewById(R.id.small_refresh);
        mRefresh.setTTRefreshLayoutListener(new PullRefreshLayout.TTRefreshLayoutListener() {
            @Override
            public void onTTRefresh() {
                loadDatas();
                mRefresh.endRefresh();

            }
        });

        mRecycleView = (RecyclerView)view.findViewById(R.id.small_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        if (mDatas!=null&&mDatas.size()>0) {
            initAdapter();
        }

    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {

        if (mAdapter==null) {
            mAdapter = new EssayAdapter(getContext(), mDatas, R.layout.small_item);
            mAdapter.setOnItemClickListener(this);
            mRecycleView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 加载网络数据
     */
    private void loadDatas() {

        mDialog = LoadingUtil.show(mDialog,getContext(),LoadingUtil.TYPE_1);
        mDialog.setCancelable(false);

        mGetDatas = mRetrofit.create(GetDatas.class);

        Map<String, String> map = new HashMap<>();
        map.put("pageToken", "0");
        map.put("uid", "4492956276_4492956276");
        map.put("apikey", Constant.APIKEY);


        mGetDatas.getEssayFromService(map).enqueue(new Callback<Essay>() {
            @Override
            public void onResponse(Call<Essay> call, final Response<Essay> response) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {


                            Essay essay = response.body();
                            if (essay.getData()==null){
                                Toast.makeText(getContext(), "刷新数据太快", Toast.LENGTH_SHORT).show();
                            }else {

                                mDatas = essay.getData();

                                initAdapter();
                                mDialog.dismiss();
                            }

                        } else {

                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<Essay> call, Throwable t) {


            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://api01.bitspaceman.com:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void onClick(View view, int position) {

        Essay.DataBean item = mDatas.get(position);

        Intent intent = new Intent(getContext(), SmallActivity.class);

        intent.putExtra("username",item.getPosterScreenName());
        intent.putExtra("date",item.getPublishDateStr());
        intent.putExtra("content",item.getContent());
        intent.putExtra("skim_count",String.valueOf(item.getViewCount()));
        intent.putExtra("comment_count",String.valueOf(item.getCommentCount()));
        intent.putExtra("set_love_count","10");
        intent.putExtra("uid",uid);
        intent.putExtra("id",item.getId());
        intent.putExtra("title","");
        intent.putExtra("collection_state", false);

        startActivity(intent);

    }
}
