package edu.nuc.vincent.com.todaynews.module.video;

import android.content.Intent;
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
import edu.nuc.vincent.com.todaynews.entity.User;
import edu.nuc.vincent.com.todaynews.utils.Constant;
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

    private String mVideoUri;

    private String mTitle;

    private String mLoveCount;

    private String mUid;

    private String mId;

    private OrientationUtils orientationUtils;

    private Retrofit mRetrofit;

    private GetDatas mGetDatas;

    private List<Comment.DataBean> mComments;
    private CommentAdapter mCommentAdapter;

    private static final int COMMENT_LENGTH = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.inject(this);
        GSYVideoManager.instance().setVideoType(this, GSYVideoType.SYSTEMPLAYER);
        getIntentData();

        setDatas();

        videoDoLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

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

        attention();
        Button btn_attention = (Button) findViewById(R.id.video_attention);
        btn_attention.setBackgroundColor(Color.parseColor("#ffffff"));
        btn_attention.setText("已关注");
        btn_attention.setTextColor(Color.BLACK);
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

    /**
     * 关注逻辑
     */
    private void attention() {

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
}

