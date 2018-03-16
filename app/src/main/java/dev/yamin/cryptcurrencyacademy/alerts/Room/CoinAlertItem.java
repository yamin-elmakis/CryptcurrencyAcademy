package dev.yamin.cryptcurrencyacademy.alerts.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by yuval on 16/03/2018.
 */
@Entity
public class CoinAlertItem {
    public CoinAlertItem(String symbol, String price, String maxPrice, String minPrice) {
        this.symbol = symbol;
        this.price = price;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "symbol")
    private String symbol;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "maxPrice")
    private String maxPrice;

    @ColumnInfo(name = "minPrice")
    private String minPrice;

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

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CoinAlertItem{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", price='" + price + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                ", minPrice='" + minPrice + '\'' +
                '}';
    }
}
