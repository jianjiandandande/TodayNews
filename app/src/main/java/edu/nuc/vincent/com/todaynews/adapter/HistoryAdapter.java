package edu.nuc.vincent.com.todaynews.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.base.BaseViewHolder;
import edu.nuc.vincent.com.todaynews.base.SimpleAdapter;
import edu.nuc.vincent.com.todaynews.entity.HistoryItem;
import edu.nuc.vincent.com.todaynews.entity.Search;

/**
 * Created by Vincent on 2018/6/27.
 */

public class HistoryAdapter extends SimpleAdapter<HistoryItem> {
    private Context mContext;
    public HistoryAdapter(Context context, List<HistoryItem> datas, int layoutResId) {
        super(context, datas, layoutResId);
        mContext = context;
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, HistoryItem dataBean) {


        if (dataBean.getContent().length()>30) {
            viewHolder.getTextView(R.id.search_content).setText(dataBean.getContent().substring(11, dataBean.getContent().length() - 1));
        }else
            viewHolder.getTextView(R.id.search_content).setText(dataBean.getContent());
        ImageView imageView = viewHolder.getImageView(R.id.search_image);
        Uri imageUri = null;

        if (dataBean.getImageUrl()!=null) {

            imageUri = Uri.parse(dataBean.getImageUrl());
        }else {
            imageUri = Uri.parse("https://p3.pstatp.com/large/pgc-image/15275844527347c09907875");
        }

        Glide.with(mContext).load(imageUri).into(imageView);

    }
}
