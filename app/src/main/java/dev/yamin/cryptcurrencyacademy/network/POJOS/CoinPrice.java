package dev.yamin.cryptcurrencyacademy.network.POJOS;

/**
 * Created by yuval on 15/03/2018.
 */

// https://api.binance.com/api/v3/ticker/price?symbol=LTCUSDT
public class CoinPrice {
    private String symbol;
    private String price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
