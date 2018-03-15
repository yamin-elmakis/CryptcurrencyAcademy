package dev.yamin.cryptcurrencyacademy.network;

/**
 * Created by yuval on 24/01/2018.
 */

public interface GsonJsonParser<T,R> {
     T parseJsonToObj(String data);
     String parseObjToJson(R obj);
    }
