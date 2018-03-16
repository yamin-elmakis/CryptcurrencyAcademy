package dev.yamin.cryptcurrencyacademy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.network.POJOS.Coin24Hr;
import dev.yamin.cryptcurrencyacademy.utils.DataUtils;

/**
 * Created by yuval on 16/03/2018.
 */

public class CoinsAdapter extends RecyclerViewAdapter<Coin24Hr> {

    public CoinsAdapter(Context context, OnViewHolderClick<Coin24Hr> listener) {
        super(context, listener);
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        // create a new view
    View v = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_coin_view, viewGroup, false);
    // set the view's size, margins, paddings and layout parameters

        return v;    }

    @Override
    protected void bindView(Coin24Hr item, RecyclerViewAdapter.ViewHolder viewHolder) {
        if (item != null) {
            double percent = Double.parseDouble(item.getPriceChangePercent());

            TextView coinName = (TextView) viewHolder.getView(R.id.coin_name);
            TextView coinPrice = (TextView) viewHolder.getView(R.id.coin_price);
            TextView coinPercent = (TextView) viewHolder.getView(R.id.percent);

            ImageView coinArrowImage = (ImageView) viewHolder.getView(R.id.coin_arrow);
            ImageView coinSymbol = (ImageView) viewHolder.getView(R.id.coin_image);

            String name = DataUtils.PairToName(item.getSymbol());
            coinName.setText(name);
            coinPrice.setText(item.getLastPrice() + (DataUtils.symbolToName(DataUtils.Bitcoin).equals(name) ? "$" : " BTC"));
            coinPercent.setText(item.getPriceChangePercent() + " %");
            int symbol = DataUtils.imagebySymbol(DataUtils.PairToSymbol(item.getSymbol()));
            coinSymbol.setImageResource(symbol);

            if (percent < 0) {
                coinArrowImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_arrow_down));
            } else {
                coinArrowImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_arrow_up));
            }
        }
    }

}
