package dev.yamin.cryptcurrencyacademy.content;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Yamin on 12-May-18.
 */
@Singleton
public class DataManager {

    @Inject
    PreferencesManager preferencesManager;

    @Inject
    public DataManager() {  }

    @Override
    public String toString() {
        return "DataManager{" +
                "preferencesManager=" + preferencesManager +
                '}';
    }
}
