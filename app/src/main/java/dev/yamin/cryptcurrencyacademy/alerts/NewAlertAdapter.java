package dev.yamin.cryptcurrencyacademy.alerts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dev.yamin.cryptcurrencyacademy.R;
import lib.yamin.easylog.EasyLog;

public class NewAlertAdapter extends RecyclerView.Adapter<NewAlertAdapter.ViewHolder> {

    private final List<String> values;
    private final OnAlertCoinSelectedListener listener;

    public NewAlertAdapter(List<String> items, OnAlertCoinSelectedListener listener) {
        this.values = items;
        this.listener = listener;
        EasyLog.e("items: "+values.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_new_alert_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(values.get(position));
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View mView;
        final ImageView ivImage;
        final TextView tvName;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = view.findViewById(R.id.item_new_alert_tv);
            ivImage = view.findViewById(R.id.item_new_alert_iv);
            mView.setOnClickListener(this);
        }

        public void bind(String data) {
            tvName.setText(data);
//            mContentView.setText(data);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.OnAlertCoinSelected(values.get(getAdapterPosition()));
        }
    }

    public interface OnAlertCoinSelectedListener {
        void OnAlertCoinSelected(String data);
    }
}
