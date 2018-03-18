package dev.yamin.cryptcurrencyacademy.network;

import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import dev.yamin.cryptcurrencyacademy.base.App;
import dev.yamin.cryptcurrencyacademy.network.POJOS.KLines;
import dev.yamin.cryptcurrencyacademy.utils.AppUtils;
import dev.yamin.cryptcurrencyacademy.utils.DataUtils;

/**
 * Created by Yamin on 3/8/2018.
 */

// API
// https://api.binance.com
// https://api.binance.com/api/v1/exchangeInfo
// https://api.binance.com/api/v1/ticker/24hr?symbol=LTCUSDT
// https://api.binance.com/api/v3/ticker/price?symbol=LTCUSDT
// https://api.binance.com/api/v1/klines?symbol=LTCUSDT&interval=5m&limit=5

public class NetworkManager {

    private static final NetworkManager instance = new NetworkManager();

    public static NetworkManager getInstance() {
        return instance;
    }

    private NetworkManager() {  }

    public void sendKLinesRequest(String coinPair, @DataUtils.KLineInterval String interval, Response.Listener responseListener, Response.ErrorListener errorListener) {
        String limit = "100";
        switch (interval) {
            case DataUtils.KLINE_INTERVAL_D:
                break;
            case DataUtils.KLINE_INTERVAL_H:
                break;
            case DataUtils.KLINE_INTERVAL_M:
                break;
            case DataUtils.KLINE_INTERVAL_W:
                break;
        }

        RequestBuilder.getInstance(App.getContext()).GenerateKLinesRequest(coinPair, interval, limit, responseListener, errorListener, new GsonJsonParser<ArrayList<KLines>, Object>() {

            @Override
            public ArrayList<KLines> parseJsonToObj(String data) {
                ArrayList<KLines> _items = new ArrayList<>();

                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return _items;
                }
                KLines kLines;
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        ArrayList<String> item = AppUtils.getObjectFromStr(jsonArray.getJSONArray(i).toString(), new TypeToken<ArrayList<String>>() {
                        }.getType());

                        kLines = new KLines();
                        kLines.setArrayList(item);
                        _items.add(kLines);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return _items;
            }

            @Override
            public String parseObjToJson(Object obj) {
                return null;
            }
        });
    }

}
