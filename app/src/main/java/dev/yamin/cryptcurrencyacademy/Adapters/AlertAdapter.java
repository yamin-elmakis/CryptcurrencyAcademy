package dev.yamin.cryptcurrencyacademy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.alerts.Room.CoinAlertItem;

/**
 * Created by yuval on 16/03/2018.
 */

public class AlertAdapter extends RecyclerViewAdapter<CoinAlertItem> {
    public AlertAdapter(Context context, OnViewHolderClick<CoinAlertItem> listener) {
        super(context, listener);
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_alert_view, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        return v;       }

    @Override
    protected void bindView(CoinAlertItem item, RecyclerViewAdapter.ViewHolder viewHolder) {
            if(item != null){
                TextView coinName = (TextView) viewHolder.getView(R.id.coin_name);
                TextView maxPrice = (TextView) viewHolder.getView(R.id.max_price);
                TextView minPrice = (TextView) viewHolder.getView(R.id.min_price);

                coinName.setText(item.getSymbol());
                maxPrice.setText(item.getMaxPrice());
                minPrice.setText(item.getMinPrice());
            }
    }
}
