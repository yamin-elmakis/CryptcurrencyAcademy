package dev.yamin.cryptcurrencyacademy.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by Yamin on 3/16/2018.
 */

public class DataUtils {

    public static final String Litecoin = "LTC";
    public static final String Bitcoin = "BTC";
    public static final String Ethereum = "ETH";
    public static final String Ripple = "XRP";
    public static final String Bitcoin_Cash = "BCH";
    public static final String Monero = "XMR";
    public static final String Dash = "DASH";
    public static final String Neo = "NEO";

    private DataUtils() {}

    @NonNull
    public ArrayList<String> getSupportedCoins() {
        ArrayList<String> coins = new ArrayList<>(10);
        coins.add("");

        return coins;
    }
}
