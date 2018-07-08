package edu.nuc.vincent.com.todaynews.module.smallnew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.nuc.vincent.com.todaynews.GetDatas;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.adapter.CommentAdapter;
import edu.nuc.vincent.com.todaynews.entity.Comment;
import edu.nuc.vincent.com.todaynews.entity.Result;
import edu.nuc.vincent.com.todaynews.entity.User;
import edu.nuc.vincent.com.todaynews.module.news.NewsActivity;
import edu.nuc.vincent.com.todaynews.module.video.VideoActivity;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import edu.nuc.vincent.com.todaynews.utils.HttpHelper;
import edu.nuc.vincent.com.todaynews.utils.L;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SmallActivity extends AppCompatActivity {

    private static final String TAG = "SmallActivity";

    @InjectView(R.id.btn_back)
    ImageView btnBack;
    @InjectView(R.id.small_user_icon)
    CircleImageView smallUserIcon;
    @InjectView(R.id.small_username)
    TextView smallUsername;
    @InjectView(R.id.small_date)
    TextView smallDate;
    @InjectView(R.id.small_attention)
    Button smallAttention;
    @InjectView(R.id.small_content)
    TextView smallContent;
    @InjectView(R.id.small_skim_count)
    TextView smallSkimCount;
    @InjectView(R.id.small_transmit)
    LinearLayout smallTransmit;
    @InjectView(R.id.small_comment_count)
    TextView smallCommentCount;
    @InjectView(R.id.small_comment)
    LinearLayout smallComment;
    @InjectView(R.id.small_set_love_state)
    ImageView smallSetLoveState;
    @InjectView(R.id.small_set_love_count)
    TextView smallSetLoveCount;
    @InjectView(R.id.small_set_love)
    LinearLayout smallSetLove;
    @InjectView(R.id.small_comment_recycle)
    RecyclerView smallCommentRecycle;
    @InjectView(R.id.video_comment_edit)
    EditText videoCommentEdit;
    @InjectView(R.id.video_do_like)
    LikeButton videoDoLike;
    @InjectView(R.id.video_transmit_to_other)
    ImageView videoTransmitToOther;
    @InjectView(R.id.small_hint)
    TextView smallHint;

    private String mUsername;
    private String mDate;
    private String mContent;
    private String mSkimCount;
    private String mCommentCount;
    private String mSetLoveCount;

    private String mUid;
    private String mId;

    private int userId = 0;

    private boolean loginState;
    private boolean collectionState;

    private String mTitle;

    private Retrofit mRetrofit,mRetrofitService;

    private GetDatas mGetDatas,mGetDatasService;

    private List<Comment.DataBean> mComments;
    private CommentAdapter mCommentAdapter;

    private String iconUrl;

    private String uname;

    private static final int COMMENT_LENGTH = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small);
        ButterKnife.inject(this);

        mRetrofitService = new HttpHelper.Builder().baseUrl(Constant.WEB_BASE_URL).build();

        mGetDatasService = mRetrofitService.create(GetDatas.class);

        videoCommentEdit.setInputType(InputType.TYPE_NULL);

        videoDoLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeInsert();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeCancel();
            }
        });

        getIntentData();
        videoDoLike.setLiked(collectionState);
        setDatas();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://120.76.205.241:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGetDatas = mRetrofit.create(GetDatas.class);
        mComments = new ArrayList<>();


        initView();
        getUserInfo();
        getComment();

        getLoginState();
        insertSerVice();


    }

    /**
     * 取消收藏
     */
    private void likeCancel() {

        if (loginState) {
            Map<String, String> map = new HashMap<>();

            map.put("userId", String.valueOf(userId));
            map.put("id", mId);
            mGetDatasService.deleteCollection(map).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Toasty.error(SmallActivity.this,response.body().getMsg(),Toast.LENGTH_SHORT,true).show();
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });

        }


    }

    /**
     * 收藏
     */
    private void likeInsert() {

        if (loginState){
            Map<String, String> map = new HashMap<>();

            map.put("userId", String.valueOf(userId));
            map.put("id",mId);
            map.put("uid",mUid);
            map.put("content",mContent);
            map.put("imageUrl"," ");
            map.put("model","3");
            map.put("videoUrl"," ");
            map.put("author",mUsername);
            map.put("skimCount",mSkimCount);
            map.put("loveCount",mSetLoveCount);
            map.put("title",mTitle);
            map.put("commentCount",mCommentCount);
            mGetDatasService.addCollectionToWeb(map).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Toasty.success(SmallActivity.this,response.body().getMsg(),Toast.LENGTH_SHORT,true).show();
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });

        }else {
            Toasty.error(SmallActivity.this,"您还未登录",Toast.LENGTH_SHORT,true).show();
        }

    }

    /**
     * 获取登录状态
     */
    private void getLoginState(){
        SharedPreferences getData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        loginState = getData.getBoolean("loginState", false);
        userId = getData.getInt("userId",0);
    }


    /**
     * 插入到服务器
     */
    private void insertSerVice() {

        SharedPreferences getData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        boolean loginState = getData.getBoolean("loginState", false);

        int userId = 0;

        if (loginState == true) {

            userId = getData.getInt("userId",0);

        }




        Map<String,String> map = new HashMap<>();

        map.put("userId",String.valueOf(userId));
        map.put("id",mId);
        map.put("uid",mUid);
        map.put("content",mContent);
        map.put("imageUrl"," ");
        map.put("model","3");
        map.put("videoUrl"," ");
        map.put("author",mUsername);
        map.put("skimCount",mSkimCount);
        map.put("loveCount",mSetLoveCount);
        map.put("title",mTitle);
        map.put("commentCount",mCommentCount);

        mGetDatasService.addHistoryToWeb(map).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equals(Constant.ADD_HISTORY_SUCCESS_CODE)){

                        Toasty.success(SmallActivity.this,"历史添加成功", Toast.LENGTH_SHORT,true).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });

    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        smallCommentRecycle.setItemAnimator(new DefaultItemAnimator());
        smallCommentRecycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        smallCommentRecycle.setLayoutManager(layoutManager);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 获取Intent数据
     */
    private void getIntentData() {

        Intent intent = getIntent();
        mUsername = intent.getStringExtra("username");
        mDate = intent.getStringExtra("date");
        mContent = intent.getStringExtra("content");
        mSkimCount = intent.getStringExtra("skim_count");
        mCommentCount = intent.getStringExtra("comment_count");
        mSetLoveCount = intent.getStringExtra("set_love_count");
        mUid = intent.getStringExtra("uid");
        mId = intent.getStringExtra("id");
        mTitle = intent.getStringExtra("title");
        collectionState = intent.getBooleanExtra("collection_state",false);
    }

    /**
     * 设置数据
     */
    private void setDatas() {

        smallUsername.setText(mUsername);
        smallDate.setText(mDate);
        smallContent.setText(mContent);
        smallSkimCount.setText(mSkimCount);
        smallCommentCount.setText(mCommentCount);
        smallSetLoveCount.setText(mSetLoveCount);
    }

    @OnClick({R.id.btn_back, R.id.small_attention, R.id.small_set_love, R.id.video_transmit_to_other})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_back:

                break;
            case R.id.small_attention:
                if (loginState) {
                    String btn_content = smallAttention.getText().toString();

                    if (btn_content.equals("关注")) {
                        attention();
                        smallAttention.setText("已关注");
                        smallAttention.setTextColor(Color.BLACK);
                        smallAttention.setBackgroundColor(Color.WHITE);
                    } else {

                        cancelAttention();

                        smallAttention.setText("关注");
                        smallAttention.setTextColor(Color.WHITE);
                        smallAttention.setBackgroundColor(Color.parseColor("#f75959"));
                    }
                }else {
                    Toasty.error(SmallActivity.this, "您还未登录", Toast.LENGTH_SHORT, true).show();
                }

                break;
            case R.id.small_set_love:
                break;
            case R.id.video_transmit_to_other:
                break;
        }
    }


    private void getUserInfo() {

        Map<String, String> map = new HashMap<>();

        map.put("id", mUid);
        map.put("pageToken", "0");
        map.put("apikey", Constant.APIKEY);

        mGetDatas.getUserInfo(map).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {

                    User.DataBean bean = (User.DataBean) response.body().getData().get(0);

                    iconUrl = bean.getAvatarUrl();
                    uname = bean.getScreenName();
                    smallUsername.setText(bean.getScreenName());
                    Glide.with(SmallActivity.this).load(bean.getAvatarUrl()).into(smallUserIcon);

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void getComment() {
        Map<String, String> map = new HashMap<>();
        map.put("id", mId);
        map.put("pageToken", "1");
        map.put("apikey", Constant.APIKEY);

        mGetDatas.getComments(map).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {

                if (response.isSuccessful()) {

                    Comment comment = response.body();

                    for (int i = 0; i < COMMENT_LENGTH; i++) {

                        if (i < comment.getData().size()) {

                            mComments.add(comment.getData().get(i));
                        }

                    }

                    if (mComments.size() > 0) {

                        smallHint.setVisibility(View.GONE);
                        smallCommentRecycle.setVisibility(View.VISIBLE);
                        initAdapter();
                    }

                }

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }



    /**
     * 初始化Adapter
     */
    private void initAdapter() {

        Log.d(TAG, "initAdapter: mComments.size = "+mComments.size());
        mCommentAdapter = new CommentAdapter(this, mComments, R.layout.comment_item);

        smallCommentRecycle.setAdapter(mCommentAdapter);

    }

    /**
     * 取消关注
     */
    private void cancelAttention() {

        if (loginState) {

            Map<String, String> map = new HashMap<>();

            map.put("userId", String.valueOf(userId));
            map.put("uid", mUid);
            mGetDatasService.deleteAttention(map).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {

                        Toasty.success(SmallActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT, true).show();

                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toasty.error(SmallActivity.this, "关注失败", Toast.LENGTH_SHORT, true).show();
                }
            });

        } else {
            Toasty.error(SmallActivity.this, "您还未登录", Toast.LENGTH_SHORT, true).show();
        }
    }

    /**
     * 关注逻辑
     */
    private void attention() {

        if (loginState) {

            Map<String, String> map = new HashMap<>();

            map.put("userId", String.valueOf(userId));
            map.put("uid", mUid);
            map.put("iconUrl",iconUrl);
            map.put("uname", uname);
            L.d("userId="+userId+",uid = "+mUid+",iconUrl = "+iconUrl+",uname = "+uname);
            mGetDatasService.addAttentionToWeb(map).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {

                        Toasty.success(SmallActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT, true).show();

                    }
                }
                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toasty.error(SmallActivity.this, "关注失败", Toast.LENGTH_SHORT, true).show();
                }
            });

        } else {
            Toasty.error(SmallActivity.this, "您还未登录", Toast.LENGTH_SHORT, true).show();
        }

    }

}
