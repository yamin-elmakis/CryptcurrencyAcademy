package dev.yamin.cryptcurrencyacademy.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Yamin on 12-May-18.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface AppContext {  }
