package dev.yamin.cryptcurrencyacademy.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dev.yamin.cryptcurrencyacademy.base.App;

/**
 * Created by Yamin on 12-May-18.
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class})
interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }



}
