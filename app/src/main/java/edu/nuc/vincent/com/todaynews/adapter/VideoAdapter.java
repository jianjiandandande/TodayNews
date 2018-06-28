package edu.nuc.vincent.com.todaynews.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.base.BaseViewHolder;
import edu.nuc.vincent.com.todaynews.base.SimpleAdapter;
import edu.nuc.vincent.com.todaynews.bean.Video;
import edu.nuc.vincent.com.todaynews.bean.VideoItem;

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
    public void bindData(BaseViewHolder viewHolder, Video.DataBean videoItem) {

        JZVideoPlayerStandard jzVideoPlayerStandard = ((JZVideoPlayerStandard)viewHolder.getView(R.id.video_item_player));

        jzVideoPlayerStandard.setUp(videoItem.getUrl().substring(0,videoItem.getUrl().indexOf("?")),
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                videoItem.getTitle());
        viewHolder.getImageView(R.id.video_item_user_icon);
        ImageView imageView = jzVideoPlayerStandard.thumbImageView;
        Glide.with(mContext).load(videoItem.getCoverUrl()).into(imageView);
        viewHolder.getTextView(R.id.video_item_user_name).setText(videoItem.getPosterScreenName());
       // viewHolder.getTextView(R.id.video_play_count).setText(videoItem.getViewCount());
        //viewHolder.getTextView(R.id.video_item_comment).setText(videoItem.getCommentCount());

    }
}
