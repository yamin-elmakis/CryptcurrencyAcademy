package dev.yamin.cryptcurrencyacademy.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yamin on 3/15/2018.
 */

public class AlertCoinsContent {
    public static final List<String> ITEMS = new ArrayList<String>();

    public static final Map<String, String> ITEM_MAP = new HashMap<String, String>();

    static {
        for (int i = 0; i < 17; i++) {
            addItem("long test item "+ i);
        }
    }
    public static void addItem(String alertItem) {
        ITEMS.add(alertItem);
        ITEM_MAP.put(alertItem, alertItem);
    }
}
