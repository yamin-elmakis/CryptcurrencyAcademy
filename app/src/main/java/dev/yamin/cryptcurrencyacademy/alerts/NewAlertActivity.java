package dev.yamin.cryptcurrencyacademy.alerts;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.base.BaseActivity;
import dev.yamin.cryptcurrencyacademy.content.AlertCoinsContent;
import dev.yamin.cryptcurrencyacademy.content.DataManager;
import dev.yamin.cryptcurrencyacademy.utils.SpacesItemDecoration;
import lib.yamin.easylog.EasyLog;

@EActivity(R.layout.activity_new_alert)
public class NewAlertActivity extends BaseActivity implements NewAlertAdapter.OnAlertCoinSelectedListener {

    @Inject
    DataManager dataManager;

    @ViewById(R.id.new_alert_rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterInject
    void onInjectDependencies() {
        AndroidInjection.inject(this);
        // dependency is now available
    }

    @AfterViews
    void afterViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NewAlertAdapter(AlertCoinsContent.ITEMS, this));

        int space = (int) (5 * Resources.getSystem().getDisplayMetrics().density);
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(space);
        itemDecoration.setFirstTop(space);
        recyclerView.addItemDecoration(itemDecoration);
        EasyLog.e(dataManager);
    }

    @Override
    public void OnAlertCoinSelected(String data) {
        EasyLog.e(data);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", data);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
