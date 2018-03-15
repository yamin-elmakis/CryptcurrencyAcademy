package dev.yamin.cryptcurrencyacademy.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yuval on 15/03/2018.
 */
// https://api.binance.com

// https://api.binance.com/api/v1/exchangeInfo

// https://api.binance.com/api/v1/ticker/24hr?symbol=LTCUSDT

// https://api.binance.com/api/v3/ticker/price?symbol=LTCUSDT

// https://api.binance.com/api/v1/klines?symbol=LTCUSDT&interval=5m&limit=5

public class VolleySingleton {
    private static final String TAG = "VolleySingleton";
    private static final String SERVER_URL = "https://api.binance.com";
    private static final String API_URL_V1 = SERVER_URL + "/api/v1/";
    private static final String API_URL_V3 = SERVER_URL + "/api/v3/";


    private static VolleySingleton mInstance;
    private static RequestQueue mRequestQueue;
    private static Context mCtx;

    private VolleySingleton(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();

//        CookieManager cookieManager = new CookieManager();
//        CookieHandler.setDefault(cookieManager);
    }

    public static String getApiUrlWithPathV1(String path) {
        return API_URL_V1 + path;
    }

    public static String getApiUrlWithPathV3(String path) {
        return API_URL_V3 + path;
    }

    public static String getServerUrl() {
        return SERVER_URL;
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            // Create an instance of the Http client.
            // We need this in order to access the cookie store
            // mHttpClient = new DefaultHttpClient();
            // create the request queue
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext() );
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
