package edu.nuc.vincent.com.todaynews.module.home;

import android.content.Intent;
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
import edu.nuc.vincent.com.todaynews.bean.Comment;
import edu.nuc.vincent.com.todaynews.bean.User;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    @InjectView(R.id.btn_back)
    ImageView btnBack;
    @InjectView(R.id.home_title)
    TextView homeTitle;
    @InjectView(R.id.home_date)
    TextView homeDate;
    @InjectView(R.id.home_content)
    TextView homeContent;
    @InjectView(R.id.home_image)
    ImageView homeImage;
    @InjectView(R.id.home_skim_count)
    TextView homeSkimCount;
    @InjectView(R.id.home_transmit)
    LinearLayout homeTransmit;
    @InjectView(R.id.home_comment_count)
    TextView homeCommentCount;
    @InjectView(R.id.home_comment)
    LinearLayout homeComment;
    @InjectView(R.id.home_set_love_state)
    ImageView homeSetLoveState;
    @InjectView(R.id.home_set_love_count)
    TextView homeSetLoveCount;
    @InjectView(R.id.home_set_love)
    LinearLayout homeSetLove;
    @InjectView(R.id.home_comment_recycle)
    RecyclerView homeCommentRecycle;
    @InjectView(R.id.home_hint)
    TextView homeHint;
    @InjectView(R.id.video_comment_edit)
    EditText videoCommentEdit;
    @InjectView(R.id.video_do_like)
    ImageView videoDoLike;
    @InjectView(R.id.video_transmit_to_other)
    ImageView videoTransmitToOther;
    @InjectView(R.id.home_user_icon)
    CircleImageView homeUserIcon;
    @InjectView(R.id.home_username)
    TextView homeUsername;
    @InjectView(R.id.home_attention)
    Button homeAttention;


    private String mTitle;

    private String mDate;

    private String mContent;

    private String mImageUri;

    private String mSkimCount;

    private String mCommentCount;

    private String mSetLoveCount;

    private String mId;
    private String mUid;


    private TextView mTitleView;

    private TextView mDataView;

    private TextView mContentView;

    private ImageView mImage;

    private TextView mSkimCountView;

    private TextView mCommentCountView;

    private TextView mSetLoveCountView;

    private LinearLayout mSkim, mComment, mSetLove;

    private ImageView mSetLoveState;

    private Retrofit mRetrofit;

    private GetDatas mGetDatas;

    private List<Comment.DataBean> mComments;
    private CommentAdapter mCommentAdapter;

    private static final int COMMENT_LENGTH = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        getIntentData();

        initView();

        videoCommentEdit.setInputType(InputType.TYPE_NULL);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://120.76.205.241:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGetDatas = mRetrofit.create(GetDatas.class);
        mComments = new ArrayList<>();

        getUserInfo();
        getComment();
    }

    /**
     * 获取intent中的数据
     */
    private void getIntentData() {

        Intent intent = getIntent();

        mTitle = intent.getStringExtra("title");
        mDate = intent.getStringExtra("date");
        mContent = intent.getStringExtra("content");
        mImageUri = intent.getStringExtra("image_uri");
        mSkimCount = intent.getStringExtra("skim_count");
        mCommentCount = intent.getStringExtra("comment_count");
        mSetLoveCount = intent.getStringExtra("set_love_count");
        mUid = intent.getStringExtra("uid");
        mId = intent.getStringExtra("id");

    }

    /**
     * 初始化View
     */
    private void initView() {

        mTitleView = (TextView) this.findViewById(R.id.home_title);
        mDataView = (TextView) this.findViewById(R.id.home_date);
        mContentView = (TextView) this.findViewById(R.id.home_content);
        mImage = (ImageView) this.findViewById(R.id.home_image);
        mSkimCountView = (TextView) this.findViewById(R.id.home_skim_count);
        mCommentCountView = (TextView) this.findViewById(R.id.home_comment_count);
        mSetLoveCountView = (TextView) this.findViewById(R.id.home_set_love_count);

        mSkim = (LinearLayout) this.findViewById(R.id.home_transmit);
        mComment = (LinearLayout) this.findViewById(R.id.home_comment);
        mSetLove = (LinearLayout) this.findViewById(R.id.home_set_love);

        mSetLoveState = this.findViewById(R.id.home_set_love_state);


        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mTitleView.setText(mTitle);
        mDataView.setText(mDate);

        if (mContent.length() > 30) {
            mContentView.setText(mContent.substring(11, mContent.length() - 1));
        } else
            mContentView.setText(mContent);
        Glide.with(this).load(mImageUri).into(mImage);
        mSkimCountView.setText(mSkimCount);
        mCommentCountView.setText(mCommentCount);
        mSetLoveCountView.setText(mSetLoveCount);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        homeCommentRecycle.setItemAnimator(new DefaultItemAnimator());
        homeCommentRecycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        homeCommentRecycle.setLayoutManager(layoutManager);



    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        Map<String,String> map = new HashMap<>();

        map.put("id",mUid);
        map.put("pageToken","0");
        map.put("apikey", Constant.APIKEY);

        mGetDatas.getUserInfo(map).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()){

                    User.DataBean bean = (User.DataBean) response.body().getData().get(0);

                    homeUsername.setText(bean.getScreenName());
                    Glide.with(HomeActivity.this).load(bean.getAvatarUrl()).into(homeUserIcon);

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

        Map<String,String> map = new HashMap<>();
        map.put("id",mId);
        map.put("pageToken","1");
        map.put("apikey",Constant.APIKEY);

        mGetDatas.getComments(map).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {

                if (response.isSuccessful()){

                    Comment comment = response.body();

                    if (comment.getData()!=null) {

                        for (int i = 0; i < COMMENT_LENGTH; i++) {

                            if (i < comment.getData().size()) {

                                mComments.add(comment.getData().get(i));
                            }

                        }
                    }

                    if (mComments.size()>0){


                        homeHint.setVisibility(View.GONE);
                        homeCommentRecycle.setVisibility(View.VISIBLE);
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

        homeCommentRecycle.setAdapter(mCommentAdapter);
    }

    @OnClick(R.id.home_attention)
    public void onClick() {

        attention();
    }

    /**
     * 关注逻辑
     */
    private void attention() {
    }
}
