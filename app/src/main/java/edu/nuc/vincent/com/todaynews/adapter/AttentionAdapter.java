package edu.nuc.vincent.com.todaynews.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.base.BaseViewHolder;
import edu.nuc.vincent.com.todaynews.base.SimpleAdapter;
import edu.nuc.vincent.com.todaynews.entity.AttentionItem;

/**
 * Created by Vincent on 2018/7/4.
 */

public class AttentionAdapter extends SimpleAdapter<AttentionItem> {

    private Context mContext;

    public AttentionAdapter(Context context, List<AttentionItem> datas, int layoutResId) {
        super(context, datas, layoutResId);
        mContext = context;
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, AttentionItem attentionItem) {

        CircleImageView icon = viewHolder.getCircleImageView(R.id.attention_icon);

        Glide.with(mContext).load(attentionItem.getIconUrl()).into(icon);

        viewHolder.getTextView(R.id.attention_name).setText(attentionItem.getUname());

    }
}
