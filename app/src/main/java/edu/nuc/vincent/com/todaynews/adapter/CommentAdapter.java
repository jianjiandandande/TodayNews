package edu.nuc.vincent.com.todaynews.adapter;

import android.content.Context;

import java.util.List;

import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.base.BaseViewHolder;
import edu.nuc.vincent.com.todaynews.base.SimpleAdapter;
import edu.nuc.vincent.com.todaynews.entity.Comment;

/**
 * Created by Vincent on 2018/6/27.
 */

public class CommentAdapter extends SimpleAdapter<Comment.DataBean> {
    public CommentAdapter(Context context, List<Comment.DataBean> datas, int layoutResId) {
        super(context, datas, layoutResId);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, Comment.DataBean dataBean) {

        viewHolder.getTextView(R.id.comment_username).setText(dataBean.getCommenterScreenName());
        viewHolder.getTextView(R.id.comment_date).setText(dataBean.getPublishDateStr());
        viewHolder.getTextView(R.id.comment_content).setText(dataBean.getContent());
    }
}
