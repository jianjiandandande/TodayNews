package edu.nuc.vincent.com.todaynews.adapter;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.base.BaseViewHolder;
import edu.nuc.vincent.com.todaynews.base.SimpleAdapter;
import edu.nuc.vincent.com.todaynews.bean.VideoItem;

/**
 * Created by Vincent on 2018/6/22.
 */

public class VideoAdapter extends SimpleAdapter<VideoItem> {

    private Context mContext;

    public VideoAdapter(Context context, List<VideoItem> datas, int layoutResId) {
        super(context, datas, layoutResId);
        mContext = context;
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, VideoItem videoItem) {

        JZVideoPlayerStandard jzVideoPlayerStandard = ((JZVideoPlayerStandard)viewHolder.getView(R.id.video_item_player));

        jzVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg"));

        jzVideoPlayerStandard.setUp("http://mvpc.eastday.com/vdianying/20171208/20171208120614480680584_1_06400360.mp4",
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "侏罗纪世界2");
        viewHolder.getImageView(R.id.video_item_user_icon).setImageURI(videoItem.getUserIconUri());
        viewHolder.getTextView(R.id.video_item_user_name).setText(videoItem.getUsername());
        viewHolder.getTextView(R.id.video_play_count).setText(videoItem.getPlayCount());
        viewHolder.getTextView(R.id.video_item_comment).setText(videoItem.getCommentCount());


    }
}
