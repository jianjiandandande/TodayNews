package edu.nuc.vincent.com.todaynews.base;

import android.content.Context;

import java.util.List;

/**
 * Created by H on 2018/4/25.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {

    public SimpleAdapter(Context context, List<T> datas, int layoutResId) {
        super(context, datas, layoutResId);
    }


}
