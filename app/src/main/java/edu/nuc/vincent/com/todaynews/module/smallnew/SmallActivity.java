package edu.nuc.vincent.com.todaynews.module.smallnew;

import android.content.Intent;
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
import edu.nuc.vincent.com.todaynews.utils.L;
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
    ImageView videoDoLike;
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

    private Retrofit mRetrofit;
    private GetDatas mGetDatas;

    private List<Comment.DataBean> mComments;
    private CommentAdapter mCommentAdapter;

    private static final int COMMENT_LENGTH = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small);
        ButterKnife.inject(this);

        videoCommentEdit.setInputType(InputType.TYPE_NULL);

        getIntentData();

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

                attention();
                Button btn_attention = (Button) findViewById(R.id.small_attention);
                btn_attention.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_attention.setText("已关注");
                btn_attention.setTextColor(Color.BLACK);

                break;
            case R.id.small_set_love:
                break;
            case R.id.video_transmit_to_other:
                break;
        }
    }

    /**
     * 关注逻辑
     */
    private void attention() {
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

}
