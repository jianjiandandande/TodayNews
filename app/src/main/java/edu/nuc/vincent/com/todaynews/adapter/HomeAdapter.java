package edu.nuc.vincent.com.todaynews.adapter;

import android.content.Context;

import java.text.DateFormat;
import java.util.List;

import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.base.BaseViewHolder;
import edu.nuc.vincent.com.todaynews.base.SimpleAdapter;
import edu.nuc.vincent.com.todaynews.bean.HomeItem;

/**
 * Created by Vincent on 2018/6/21.
 */

public class HomeAdapter extends SimpleAdapter<HomeItem>{
    public HomeAdapter(Context context, List<HomeItem> datas, int layoutResId) {
        super(context, datas, layoutResId);
    }


    @Override
    public void bindData(BaseViewHolder viewHolder, HomeItem homeItem) {

        viewHolder.getCircleImageView(R.id.home_item_user_icon).setImageURI(homeItem.getUserIconUri());
        viewHolder.getTextView(R.id.home_item_username).setText(homeItem.getUsername());
        DateFormat df = DateFormat.getDateTimeInstance();
        viewHolder.getTextView(R.id.home_item_date).setText(df.format(homeItem.getSendDate()));
        viewHolder.getTextView(R.id.home_item_text).setText(homeItem.getSendContent().toString());
        viewHolder.getImageView(R.id.home_item_image).setImageURI(homeItem.getSendImageUri());
    }
}
