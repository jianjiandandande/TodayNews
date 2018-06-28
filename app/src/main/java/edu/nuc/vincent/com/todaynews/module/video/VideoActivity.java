package edu.nuc.vincent.com.todaynews.module.video;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.nuc.vincent.com.todaynews.R;

public class VideoActivity extends AppCompatActivity {

    @InjectView(R.id.video_player)
    JZVideoPlayerStandard videoPlayer;
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
    ImageView videoDoLike;
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

    private String mVideoUri;

    private String mTitle;

    private String mLoveCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.inject(this);

        getIntentData();

        setDatas();

        videoCommentEdit.setInputType(InputType.TYPE_NULL);

        videoPlayer.backButton.setVisibility(View.VISIBLE);
        videoPlayer.onStateNormal();
        videoPlayer.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    }

    /**
     * 设置数据
     */
    private void setDatas() {

        videoTitle.setText(mTitle);
        videoPlayer.setUp(mVideoUri,
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                mTitle);
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
     * 关注逻辑
     */
    private void attention() {

    }
}
