package dev.yamin.cryptcurrencyacademy.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.yamin.cryptcurrencyacademy.network.POJOS.Coin24Hr;
import dev.yamin.cryptcurrencyacademy.network.POJOS.KLines;
import lib.yamin.easylog.EasyLog;

/**
 * Created by yuval on 09/01/2018.
 */

public class RequestBuilder{
    private static final String TAG = "RequestBuilder";
    private static RequestBuilder mInstance;
    private static Context mContext;
    private static RequestManager mRequest;

    private RequestBuilder(Context context){
        mContext = context;
    }

    public static synchronized RequestBuilder getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestBuilder(context);
        }
        return mInstance;
    }

    public void GenerateKLinesRequest(String coinPair,String interval,String limit,Response.Listener responseListener, Response.ErrorListener errorListener,GsonJsonParser gsonJsonParser){
        Map<String,String> params = new HashMap<>();
        params.put("symbol",coinPair);
        params.put("interval",interval);
        params.put("limit",limit);

        EasyLog.e( VolleySingleton.getInstance(mContext).getApiUrlWithPathV1("klines"));

        RequestManager mRequest = new RequestManager<ArrayList<KLines>,Map<String,String>>(Request.Method.GET,
                VolleySingleton.getInstance(mContext).getApiUrlWithPathV1("klines"),params,errorListener
        ,responseListener,gsonJsonParser);

       execute(mRequest);
    }

    public void GenerateCoin24HrRequest(String symbol,Response.Listener responseListener, Response.ErrorListener errorListener,GsonJsonParser gsonJsonParser){
        Map<String,String> params = new HashMap<>();
        params.put("symbol",symbol);

        RequestManager mRequest =new RequestManager<Coin24Hr,Object>(Request.Method.GET,
                VolleySingleton.getInstance(mContext).getApiUrlWithPathV1("ticker/24hr"),params,errorListener
                ,responseListener,gsonJsonParser);

        execute(mRequest);
    }


    private void execute(RequestManager mRequest){
            VolleySingleton.getInstance(mContext).addToRequestQueue(mRequest);
    }

}
