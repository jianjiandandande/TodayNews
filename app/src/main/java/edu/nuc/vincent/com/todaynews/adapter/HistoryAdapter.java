package edu.nuc.vincent.com.todaynews.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;

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
    public void bindData(BaseViewHolder viewHolder, final HistoryItem dataBean) {

        SwipeLayout swipeLayout = (SwipeLayout) viewHolder.getView(R.id.history_swipelayout);

        swipeLayout.addDrag(SwipeLayout.DragEdge.Left,viewHolder.getView(R.id.history_bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        LinearLayout linearLayout = (LinearLayout) viewHolder.getView(R.id.history_bottom_wrapper);


        if (dataBean.getContent().length()>30) {
            viewHolder.getTextView(R.id.history_content).setText(dataBean.getContent().substring(11, dataBean.getContent().length() - 1));
        }else
            viewHolder.getTextView(R.id.history_content).setText(dataBean.getContent());
        ImageView imageView = viewHolder.getImageView(R.id.history_image);
        Uri imageUri = null;

        if (dataBean.getImageUrl()!=null) {

            imageUri = Uri.parse(dataBean.getImageUrl());
        }else {
            imageUri = Uri.parse("https://p3.pstatp.com/large/pgc-image/15275844527347c09907875");
        }

        Glide.with(mContext).load(imageUri).into(imageView);

    }
}
