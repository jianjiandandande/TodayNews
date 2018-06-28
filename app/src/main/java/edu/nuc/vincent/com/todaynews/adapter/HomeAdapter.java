package edu.nuc.vincent.com.todaynews.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.base.BaseViewHolder;
import edu.nuc.vincent.com.todaynews.base.SimpleAdapter;
import edu.nuc.vincent.com.todaynews.bean.News;

/**
 * Created by Vincent on 2018/6/21.
 */

public class HomeAdapter extends SimpleAdapter<News.DataBean>{

    private Context mContext;
    public HomeAdapter(Context context, List<News.DataBean> datas, int layoutResId) {
        super(context, datas, layoutResId);
        this.mContext = context;
    }


    @Override
    public void bindData(BaseViewHolder viewHolder,  News.DataBean homeItem) {

        viewHolder.getTextView(R.id.home_item_username).setText(homeItem.getTitle());
        viewHolder.getTextView(R.id.home_item_date).setText(homeItem.getPublishDateStr());
        if (homeItem.getContent().length()>30) {
            viewHolder.getTextView(R.id.home_item_text).setText(homeItem.getContent().substring(11, homeItem.getContent().length() - 1));
        }else
            viewHolder.getTextView(R.id.home_item_text).setText(homeItem.getContent());
        ImageView imageView = viewHolder.getImageView(R.id.home_item_image);

        Uri imageUri = null;

        if (homeItem.getImageUrls()!=null) {

            imageUri = Uri.parse(homeItem.getImageUrls().get(0));
        }else {
            imageUri = Uri.parse("https://p3.pstatp.com/large/pgc-image/15275844527347c09907875");
        }

        Glide.with(mContext).load(imageUri).into(imageView);

        viewHolder.getTextView(R.id.home_item_skim_count).setText(String.valueOf(homeItem.getViewCount()));
        viewHolder.getTextView(R.id.home_item_comment_count).setText(String.valueOf(homeItem.getCommentCount()));
        viewHolder.getTextView(R.id.home_item_set_love_count).setText(String.valueOf(homeItem.getLikeCount()));
    }
}
