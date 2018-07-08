package edu.nuc.vincent.com.todaynews.module.video;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
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
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

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
import edu.nuc.vincent.com.todaynews.module.smallnew.SmallActivity;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import edu.nuc.vincent.com.todaynews.utils.HttpHelper;
import edu.nuc.vincent.com.todaynews.utils.L;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoActivity extends AppCompatActivity {

    @InjectView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;
    @InjectView(R.id.video_title)
    TextView videoTitle;
    @InjectView(R.id.video_set_love_state)
    ImageView videoSetLoveState;
    @InjectView(R.id.video_set_love_count)
    TextView videoSetLoveCount;
    @InjectView(R.id.video_set_love)
    LinearLayout videoSetLove;
    @InjectView(R.id.video_transmit)
    LinearLayout videoTransmit;
    @InjectView(R.id.video_comment_recycle)
    RecyclerView videoCommentRecycle;
    @InjectView(R.id.video_comment_edit)
    EditText videoCommentEdit;
    @InjectView(R.id.video_do_like)
    LikeButton videoDoLike;
    @InjectView(R.id.video_transmit_to_other)
    ImageView videoTransmitToOther;
    @InjectView(R.id.video_user_icon)
    CircleImageView videoUserIcon;
    @InjectView(R.id.video_username)
    TextView videoUsername;
    @InjectView(R.id.video_date)
    TextView videoDate;
    @InjectView(R.id.video_attention)
    Button videoAttention;
    @InjectView(R.id.video_hint)
    TextView videoHint;

    private String iconUrl;

    private String uname;

    private String mVideoUri;

    private String mTitle;

    private String mLoveCount;

    private String mUid;

    private String mId;

    private int userId = 0;

    private boolean loginState;
    private boolean collectionState;

    private String mImageUrl;

    private String mSkimCount;

    private String mCommentCount;

    private OrientationUtils orientationUtils;

    private Retrofit mRetrofit,mRetrofitService,mReteofitLove;

    private GetDatas mGetDatas,mGetDatasService,mGetDatesLove;

    private List<Comment.DataBean> mComments;
    private CommentAdapter mCommentAdapter;

    private static final int COMMENT_LENGTH = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.inject(this);

        mReteofitLove = new HttpHelper.Builder().baseUrl(Constant.WEB_BASE_URL).build();
        mGetDatesLove = mReteofitLove.create(GetDatas.class);

        GSYVideoManager.instance().setVideoType(this, GSYVideoType.SYSTEMPLAYER);
        getIntentData();
        videoDoLike.setLiked(collectionState);
        setDatas();

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

        videoCommentEdit.setInputType(InputType.TYPE_NULL);

        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        orientationUtils = new OrientationUtils(this, videoPlayer);
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPlayer.startWindowFullscreen(VideoActivity.this, false, true);
            }
        });

        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();



        videoCommentEdit.setInputType(InputType.TYPE_NULL);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://120.76.205.241:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGetDatas = mRetrofit.create(GetDatas.class);
        mComments = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        videoCommentRecycle.setItemAnimator(new DefaultItemAnimator());
        videoCommentRecycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        videoCommentRecycle.setLayoutManager(layoutManager);

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
            mGetDatesLove.deleteCollection(map).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Toasty.error(VideoActivity.this,response.body().getMsg(),Toast.LENGTH_SHORT,true).show();
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
            map.put("id", mId);
            map.put("uid", mUid);
            map.put("content", " ");
            map.put("imageUrl", mImageUrl);
            map.put("model", "1");
            map.put("videoUrl", "");
            map.put("author", "");
            map.put("skimCount", mSkimCount);
            map.put("loveCount", mLoveCount);
            map.put("title", mTitle);
            map.put("commentCount", mCommentCount);
            mGetDatesLove.addCollectionToWeb(map).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Toasty.success(VideoActivity.this,response.body().getMsg(),Toast.LENGTH_SHORT,true).show();
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });

        }else {
            Toasty.error(VideoActivity.this,"您还未登录",Toast.LENGTH_SHORT,true).show();
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

        mRetrofitService = new HttpHelper.Builder().baseUrl(Constant.WEB_BASE_URL).build();

        mGetDatasService = mRetrofitService.create(GetDatas.class);

        Map<String,String> map = new HashMap<>();

        map.put("userId",String.valueOf(userId));
        map.put("id",mId);
        map.put("uid",mUid);
        map.put("content"," ");
        map.put("imageUrl",mImageUrl);
        map.put("model","2");
        map.put("videoUrl",mVideoUri);
        map.put("author"," ");
        map.put("skimCount",mSkimCount);
        map.put("loveCount",mLoveCount);
        map.put("title",mTitle);
        map.put("commentCount",mCommentCount);

        L.d("userId = "+userId+",id = "+mId+",uid = "+mUid+", content = "+
                " "+",imageUrl = "+mImageUrl+",model = "+"2"+", videoUrl = "+
                mVideoUri+",author = "+" "+", skimCount = "+mSkimCount+",loveCount"+mLoveCount+",title = "+mTitle+",commentCount = "+mCommentCount);

        mGetDatasService.addHistoryToWeb(map).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equals(Constant.ADD_HISTORY_SUCCESS_CODE)){

                        Toasty.success(VideoActivity.this,"历史添加成功", Toast.LENGTH_SHORT,true).show();
                    }
                }else {
                    Toasty.error(VideoActivity.this,"历史添加失败111", Toast.LENGTH_SHORT,true).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toasty.error(VideoActivity.this,"历史添加失败", Toast.LENGTH_SHORT,true).show();
            }
        });

    }

    /**
     * 获取intent的值
     */
    private void getIntentData() {

        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mVideoUri = intent.getStringExtra("video_uri");
        mLoveCount = intent.getStringExtra("love_count");
        mUid = intent.getStringExtra("uid");
        mId = intent.getStringExtra("id");
        mImageUrl = intent.getStringExtra("imageUrl");
        mSkimCount = intent.getStringExtra("skim_count");
        mCommentCount = intent.getStringExtra("comment_count");
        collectionState = intent.getBooleanExtra("collection_state",false);
    }

    /**
     * 设置数据
     */
    private void setDatas() {

        videoTitle.setText(mTitle);
        videoPlayer.setUp(mVideoUri,
                true, mTitle);
        videoSetLoveCount.setText(mLoveCount);

    }

    @OnClick(R.id.video_attention)
    public void onClick() {
        if (loginState) {
            String btn_content = videoAttention.getText().toString();

            if (btn_content.equals("关注")) {
                attention();
                videoAttention.setText("已关注");
                videoAttention.setTextColor(Color.BLACK);
                videoAttention.setBackgroundColor(Color.WHITE);
            } else {

                cancelAttention();

                videoAttention.setText("关注");
                videoAttention.setTextColor(Color.WHITE);
                videoAttention.setBackgroundColor(Color.parseColor("#f75959"));
            }
        }else {
            Toasty.error(VideoActivity.this, "您还未登录", Toast.LENGTH_SHORT, true).show();
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        Map<String, String> map = new HashMap<>();

        map.put("id", mUid);
        map.put("apikey", Constant.APIKEY);

        mGetDatas.getVideoUserInfo(map).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {

                    User.DataBean bean = (User.DataBean) response.body().getData().get(0);

                    iconUrl = bean.getAvatarUrl();
                    uname = bean.getScreenName();

                    videoUsername.setText(bean.getScreenName());
                    Glide.with(VideoActivity.this).load(bean.getAvatarUrl()).into(videoUserIcon);

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }

    /**
     * 获取评论
     */
    private void getComment() {

        Map<String, String> map = new HashMap<>();
        map.put("id", mId);
        map.put("apikey", Constant.APIKEY);

        mGetDatas.getVideoComments(map).enqueue(new Callback<Comment>() {
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


                        videoHint.setVisibility(View.GONE);
                        videoCommentRecycle.setVisibility(View.VISIBLE);
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

        mCommentAdapter = new CommentAdapter(this, mComments, R.layout.comment_item);

        videoCommentRecycle.setAdapter(mCommentAdapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
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

                        Toasty.success(VideoActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT, true).show();

                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toasty.error(VideoActivity.this, "关注失败", Toast.LENGTH_SHORT, true).show();
                }
            });

        } else {
            Toasty.error(VideoActivity.this, "您还未登录", Toast.LENGTH_SHORT, true).show();
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

                        Toasty.success(VideoActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT, true).show();

                    }
                }
                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toasty.error(VideoActivity.this, "关注失败", Toast.LENGTH_SHORT, true).show();
                }
            });

        } else {
            Toasty.error(VideoActivity.this, "您还未登录", Toast.LENGTH_SHORT, true).show();
        }

    }
}

