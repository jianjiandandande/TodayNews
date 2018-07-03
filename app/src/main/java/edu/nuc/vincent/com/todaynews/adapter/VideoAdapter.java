package edu.nuc.vincent.com.todaynews.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.base.BaseViewHolder;
import edu.nuc.vincent.com.todaynews.base.SimpleAdapter;
import edu.nuc.vincent.com.todaynews.entity.Video;
import edu.nuc.vincent.com.todaynews.utils.L;

/**
 * Created by Vincent on 2018/6/22.
 */

public class VideoAdapter extends SimpleAdapter<Video.DataBean> {

    private Context mContext;

    public VideoAdapter(Context context, List<Video.DataBean> datas, int layoutResId) {
        super(context, datas, layoutResId);
        mContext = context;
    }

    @Override
    public void bindData(final BaseViewHolder viewHolder, Video.DataBean videoItem) {

        GSYVideoManager.instance().setVideoType(mContext, GSYVideoType.SYSTEMPLAYER);
        final StandardGSYVideoPlayer jzVideoPlayerStandard = ((StandardGSYVideoPlayer)viewHolder.getView(R.id.video_item_player));
        L.d("url = "+videoItem.getUrl()+" title = "+videoItem.getTitle());
        jzVideoPlayerStandard.setUp(videoItem.getVideoUrls().get(0),true,
                videoItem.getTitle());

        jzVideoPlayerStandard.getTitleTextView().setVisibility(View.VISIBLE);
        jzVideoPlayerStandard.getBackButton().setVisibility(View.GONE);

        jzVideoPlayerStandard.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jzVideoPlayerStandard.startWindowFullscreen(mContext, false, true);
            }
        });
        //全屏动画
        jzVideoPlayerStandard.setShowFullAnimation(true);

        viewHolder.getImageView(R.id.video_item_user_icon);

        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(videoItem.getCoverUrl()).into(imageView);
        jzVideoPlayerStandard.setThumbImageView(imageView);

        viewHolder.getTextView(R.id.video_item_user_name).setText(videoItem.getPosterScreenName());

        jzVideoPlayerStandard.startPlayLogic();

        viewHolder.getTextView(R.id.video_item_play_count).setText(videoItem.getViewCount()+"次播放");
        viewHolder.getTextView(R.id.video_item_comment).setText(videoItem.getCommentCount()+"");

    }
}
