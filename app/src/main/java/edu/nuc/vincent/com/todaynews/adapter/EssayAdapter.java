package edu.nuc.vincent.com.todaynews.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.base.BaseViewHolder;
import edu.nuc.vincent.com.todaynews.base.SimpleAdapter;
import edu.nuc.vincent.com.todaynews.bean.Essay;
import edu.nuc.vincent.com.todaynews.bean.News;

/**
 * Created by Vincent on 2018/6/21.
 */

public class EssayAdapter extends SimpleAdapter<Essay.DataBean>{

    private Context mContext;
    public EssayAdapter(Context context, List<Essay.DataBean> datas, int layoutResId) {
        super(context, datas, layoutResId);
        this.mContext = context;
    }


    @Override
    public void bindData(BaseViewHolder viewHolder,  Essay.DataBean essayItem) {

        viewHolder.getTextView(R.id.small_item_username).setText(essayItem.getPosterScreenName());
        viewHolder.getTextView(R.id.small_item_date).setText(essayItem.getPublishDateStr());
        viewHolder.getTextView(R.id.small_item_content).setText(essayItem.getContent());
        viewHolder.getTextView(R.id.small_item_skim_count).setText(String.valueOf(essayItem.getViewCount()));
        viewHolder.getTextView(R.id.small_item_comment_count).setText(String.valueOf(essayItem.getCommentCount()));
//        viewHolder.getTextView(R.id.samll_item_set_love_count).setText(String.valueOf(essayItem.get()));
    }
}
