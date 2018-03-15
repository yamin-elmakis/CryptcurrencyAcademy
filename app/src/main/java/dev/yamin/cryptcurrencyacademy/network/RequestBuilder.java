package dev.yamin.cryptcurrencyacademy.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.HashMap;

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


    public void execute(){
        VolleySingleton.getInstance(mContext).addToRequestQueue(mRequest);
    }

}
