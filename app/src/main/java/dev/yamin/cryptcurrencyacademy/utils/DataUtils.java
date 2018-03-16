package dev.yamin.cryptcurrencyacademy.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by Yamin on 3/16/2018.
 */

public class DataUtils {

    public static final String USDT = "USDT";
    public static final String Bitcoin = "BTC";
    public static final String Ethereum = "ETH";
    public static final String Ripple = "XRP";
    public static final String Bitcoin_Cash = "BCH";
    public static final String Litecoin = "LTC";
    public static final String Neo = "NEO";
    public static final String Monero = "XMR";
    public static final String Dash = "DASH";
    public static final String ZCash = "ZEC";
    public static final String Steem = "STEEM";

    private DataUtils() {}

    @NonNull
    public static ArrayList<String> getSupportedCoins() {
        ArrayList<String> coins = new ArrayList<>(10);
        coins.add(Bitcoin);
        coins.add(Ethereum);
        coins.add(Ripple);
        coins.add(Bitcoin_Cash);
        coins.add(Litecoin);
        coins.add(Neo);
        coins.add(Monero);
        coins.add(Dash);
        coins.add(ZCash);
        coins.add(Steem);
        return coins;
    }

    @Nullable
    public static String symbolToName(String symbol) {
        switch (symbol) {
            case Bitcoin:
                return "Bitcoin";
            case Ethereum:
                return "Ethereum";
            case Ripple:
                return "Ripple";
                case Bitcoin_Cash:
                return "Bitcoin Cash";
            case Litecoin:
                return "Litecoin";
            case Neo:
                return "Neo";
            case Monero:
                return "Monero";
            case Dash:
                return "Dash";
            case ZCash:
                return "ZCash";
            case Steem:
                return "Steem";
            default:
                return null;
        }
    }

    @Nullable
    public static String symbolToPair(String symbol) {
        switch (symbol) {
            case Bitcoin:
                return Bitcoin + USDT;
            case Ethereum:
                return Ethereum + Bitcoin;
            case Ripple:
                return Ripple + Bitcoin;
                case Bitcoin_Cash:
                return Bitcoin_Cash + Bitcoin;
            case Litecoin:
                return Litecoin+ Bitcoin;
            case Neo:
                return Neo + Bitcoin;
            case Monero:
                return Monero+ Bitcoin;
            case Dash:
                return Dash+ Bitcoin;
            case ZCash:
                return ZCash+ Bitcoin;
            case Steem:
                return Steem+ Bitcoin;
            default:
                return null;
        }
    }

    @Nullable
    public static String PairToName(String pair) {
        switch (pair) {
            case Bitcoin + USDT:
                return symbolToName(Bitcoin);

                case Ethereum + Bitcoin:
                return symbolToName(Ethereum);

            case Ripple + Bitcoin:
                return symbolToName(Ripple);

                case Bitcoin_Cash + Bitcoin:
                return symbolToName(Bitcoin_Cash);

            case Litecoin+ Bitcoin:
                return symbolToName(Litecoin);

            case Neo + Bitcoin:
                return symbolToName(Neo);

            case Monero+ Bitcoin:
                return symbolToName(Monero);

            case Dash+ Bitcoin:
                return symbolToName(Dash);

            case ZCash+ Bitcoin:
                return symbolToName(ZCash);

            case Steem+ Bitcoin:
                return symbolToName(Steem);

            default:
                return null;
        }
    }
}
