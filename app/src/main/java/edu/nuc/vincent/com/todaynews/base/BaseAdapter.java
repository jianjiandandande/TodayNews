package edu.nuc.vincent.com.todaynews.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by H on 2018/4/25.
 */

public abstract class BaseAdapter<T,H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder>{


    protected List<T> mDatas;
    protected LayoutInflater  mInflater;
    protected Context mContext;
    protected int mLayoutResId;

    private OnItemClickListener mListener;


    public interface OnItemClickListener{

        public void onClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.mListener = listener;
    }


    public BaseAdapter(Context context,List<T> datas,int layoutResId){

        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutResId = layoutResId;

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(mLayoutResId,null,false);

        return new BaseViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        T t = getItem(position);

        bindData(holder,t);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public T getItem(int position){
        return mDatas.get(position);
    }

    public abstract void bindData(BaseViewHolder viewHolder,T t);
}
