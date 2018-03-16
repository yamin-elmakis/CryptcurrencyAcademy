package dev.yamin.cryptcurrencyacademy.network.POJOS;

import java.util.ArrayList;

/**
 * Created by yuval on 15/03/2018.
 */

public class KLinesList {
    ArrayList<KLines> kLinesArrayList;

    public ArrayList<KLines> getkLinesArrayList() {
        return kLinesArrayList;
    }

    public void setkLinesArrayList(ArrayList<KLines> kLinesArrayList) {
        this.kLinesArrayList = kLinesArrayList;
    }
}
