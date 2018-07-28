package dev.yamin.cryptcurrencyacademy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.yamin.easylog.EasyLog;

/**
 * Created by yuvalmetal on 27/10/2017.
 */

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Serializable {
    private List<T> mDataSet;
    private Context mContext;
    private OnViewHolderClick<T> mListener;


    public interface OnViewHolderClick<T> {
        void onClick(View view, int position, T item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Map<Integer, View> views;

        public ViewHolder(View view, OnViewHolderClick listener) {
            super(view);
            views = new HashMap<>();
            views.put(0, view);

            if (listener != null)
                view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
                mListener.onClick(v, getAdapterPosition(), getItem(getAdapterPosition()));
        }

        public void initViewList(int[] idList) {
            for (int id : idList)
                initViewById(id);
        }

        public void initViewById(int id) {
            View view = (getView() != null ? getView().findViewById(id) : null);

            if (view != null)
                views.put(id, view);
        }

        public View getView() {
            return getView(0);
        }

        public View getView(int id) {
            if (views.containsKey(id))
                return views.get(id);
            else
                initViewById(id);

            return views.get(id);
        }
    }
    protected abstract View createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bindView(T item, RecyclerViewAdapter.ViewHolder viewHolder);

    public RecyclerViewAdapter(Context context) {
        this(context, null);
    }

    public RecyclerViewAdapter(Context context, OnViewHolderClick<T> listener) {
        super();
        this.mContext = context;
        this.mListener = listener;
        mDataSet = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(createView(mContext, viewGroup, viewType), mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        bindView(getItem(position), holder);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public T getItem(int index) {
        return ((mDataSet != null && index < mDataSet.size()) ? mDataSet.get(index) : null);
    }

    public Context getContext() {
        return mContext;
    }

    public void setList(List<T> list) {
        mDataSet = list;
    }

    public List<T> getList() {
        return mDataSet;
    }

    public void setClickListener(OnViewHolderClick listener) {
        this.mListener = listener;
    }

    public void addAll(List<T> list) {
        EasyLog.d(list);
        mDataSet.addAll(list);
        notifyDataSetChanged();
    }

    public void reset() {
        mDataSet.clear();
        notifyDataSetChanged();
    }
}

