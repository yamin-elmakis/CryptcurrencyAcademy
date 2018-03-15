package dev.yamin.cryptcurrencyacademy.network.POJOS;

import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;

import java.util.ArrayList;

/**
 * Created by yuval on 15/03/2018.
 */

public class KLines {
    private static final int OPEN_TIME = 1;
    private static final int OPEN = 2;
    private static final int HIGH = 3;
    private static final int LOW = 4;
    private static final int CLOSE = 5;
    private static final int VOLUME = 6;
    private static final int CLOSE_TIME = 7;
    private static final int Quote_ASSET_VOLUME = 8;
    private static final int NUMBER_OF_TRADES = 9;
    private static final int TAKER_BUY_BASE = 10;
    private static final int TAKER_BUY_QUOTE = 11;
    private static final int IGNORE = 12;

    ArrayList<String> arrayList;

    public boolean isValid(int validSize){
        if(arrayList == null){
            return false;
        }

        if(arrayList.size() < validSize)
        {
            return  false;
        }

        return true;
    }

    public long getOpenTime(){
        if(isValid(OPEN_TIME)) {
            return Long.parseLong(arrayList.get(0));
        }
        return -1;
    }
}
