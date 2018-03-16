package dev.yamin.cryptcurrencyacademy.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lib.yamin.easylog.EasyLog;

/**
 * Created by yuval on 09/01/2018.
 */

public class RequestManager<T ,R> extends Request<T> {
    private static final String TAG = "RequestManager";
    private static final int MY_SOCKET_TIMEOUT_MS = 5000 ;
//    private static final String SESSION_TAG = "session";
  //  private static final String HEADER_ID = "set-cookie";
    //private static final String SEND_HEADER = "Cookie";
    private static final String PROTOCOL_CHARSET ="utf-8" ;
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private final Response.Listener<T> mListener;
    private JSONObject mRequestBody;
    private Context mContext;
    private GsonJsonParser<T,R> mConverter;

    private Map<String, String> mParams;
    private static Gson gson;

    static {
        gson = new Gson();
    }


    /**
     * Ctor
     * @param method
     * @param url
     * @param requestBody
     * @param listener
     * @param rListener
     */
    public RequestManager(int method, String url, R requestBody, Response.ErrorListener listener, Response.Listener<T> rListener, GsonJsonParser<T,R> converter) {
        super(method, url, listener);

        mParams = null;
        mRequestBody = null;

        if(method == Request.Method.GET){
            mParams = (HashMap<String,String>)requestBody;
        }
        else {
            mRequestBody = parseObjToJson(requestBody);
        }

        mListener = rListener;

        mConverter = converter;


        setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    /**
     *
     * @param data
     * @return
     */
    private T parseJsonToObj(String data){
        T item = null;
        if(data != null){
            JsonParser parser = new JsonParser();
            //JsonElement mJson = null;
            Type dataType;
            Gson gson = new Gson();
           // mJson = parser.parse(data);

            dataType = new TypeToken<T>(){}.getType();

            item = gson.fromJson(data,dataType);
        }
        return item;
    }

    private JSONObject parseObjToJson(R obj){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(gson.toJson(obj));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     *
     * @param response
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        //String session = response.headers.get(HEADER_ID);
        String jsonString = null;
//        if(session != null){
//            mContext.getSharedPreferences(SESSION_TAG,MODE_PRIVATE)
//                    .edit()
//                    .putString(HEADER_ID,session)
//                    .apply();
//        }

        try {
             jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            // parse json

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        T responseBody = mConverter == null ? parseJsonToObj(jsonString) : mConverter.parseJsonToObj(jsonString);

        return Response.success(responseBody,
                HttpHeaderParser.parseCacheHeaders(response));
    }

    /**
     *
     * @param response
     */
    @Override
    protected void deliverResponse(T response)
    {
        Log.d(TAG, "deliverResponse: "+ response);
        if(mListener != null)
            mListener.onResponse(response);
    }

//    /**
//     *
//     * @return
//     * @throws AuthFailureError
//     */
//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//            Log.d(TAG,"ASS");
//            HashMap<String,String> headers = new HashMap<String,String>();
//            String cookieInfo = mContext.getSharedPreferences(SESSION_TAG,MODE_PRIVATE).getString(HEADER_ID,"");
//
//            if(!TextUtils.isEmpty(cookieInfo))
//            {
//                headers.put(SEND_HEADER,cookieInfo);
//            }
//            return headers;
//    }



    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParams != null){
            EasyLog.e(mParams);
            return mParams;
        }
        return super.getParams();
    }

    @Override
    public String getUrl() {
        String url = super.getUrl();
        if (getMethod() == Method.GET) {
            url += "?" + getParamsAsString();
        }
        EasyLog.e(url);
        return url;
    }


    @Override
    public byte[] getBody() throws AuthFailureError {
        Log.d(TAG, "getBody: please help me");
//        Iterator<String> keys = mRequestBody.keys();
//        while (keys.hasNext()) {
//            String key = keys.next();
//            String data = null;
//            try {
//                 data = mRequestBody.getString(key);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            return mRequestBody == null ? null : mRequestBody.toString().getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    protected String getParamsAsString() {
        StringBuilder paramString = new StringBuilder();
        String paramsEncoding = getParamsEncoding();
        try {
            Set<Map.Entry<String, String>> paramSet = getParams().entrySet();
            for (Map.Entry<String, String> entry : paramSet) {
                paramString.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                paramString.append('=');
                paramString.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                paramString.append('&');
            }
            paramString.deleteCharAt(paramString.length()-1);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        } catch (AuthFailureError authFailureError) {
            throw new RuntimeException("getParamsAsString AuthFailure");
        }
        return paramString.toString();
    }
}

/*

    @Override
    public byte[] getBody() throws AuthFailureError {
        String paramsEncoding = getParamsEncoding();
        try {
            String encodedParamsStr = getParamsAsString();
            return encodedParamsStr.getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, e);
        }
    }
 */

