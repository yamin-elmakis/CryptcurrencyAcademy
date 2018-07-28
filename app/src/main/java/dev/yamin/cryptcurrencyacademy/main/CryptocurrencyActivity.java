package dev.yamin.cryptcurrencyacademy.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.alerts.AlertsFragment;
import dev.yamin.cryptcurrencyacademy.alerts.NewAlertActivity_;
import dev.yamin.cryptcurrencyacademy.base.BaseActivity;
import dev.yamin.cryptcurrencyacademy.details.CoinDetailsActivity;
import dev.yamin.cryptcurrencyacademy.my.coins.CoinsFragment;
import lib.yamin.easylog.EasyLog;

public class CryptocurrencyActivity extends BaseActivity implements
        CoinsFragment.OnCoinSelectedListener,
        AlertsFragment.OnFragmentInteractionListener, View.OnClickListener {

    public static final int SELECT_COIN_REQUEST = 110;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.main_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);

        floatingActionButton = findViewById(R.id.add_alert);
        floatingActionButton.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CoinsFragment(), "Coins");
        adapter.addFragment(new AlertsFragment(), "Alert");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void OnCoinSelected(String coinPair, String lastPrice, String priceChangePercent) {
//        Intent intent = new Intent(this, NewAlertActivity.class);
//        startActivityForResult(intent, SELECT_COIN_REQUEST);
        EasyLog.e(coinPair);
        Intent detailsIntent = new Intent(this, CoinDetailsActivity.class);
        detailsIntent.putExtra(CoinDetailsActivity.ARG_COIN_PAIR, coinPair);
        detailsIntent.putExtra(CoinDetailsActivity.ARG_COIN_PERCENT, priceChangePercent);
        detailsIntent.putExtra(CoinDetailsActivity.ARG_COIN_PRICE, lastPrice);
        startActivity(detailsIntent);
    }

    @Override
    public void onClick(View view) {
        NewAlertActivity_.IntentBuilder_ intent = NewAlertActivity_.intent(this);
        intent.startForResult(RESULT_OK);
//        startActivityForResult(intent.get(),RESULT_OK);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 1){
                floatingActionButton.show();
            }
            else{
                floatingActionButton.hide();
            }
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EasyLog.e();

//        RequestBuilder.getInstance(this).GenerateKLinesRequest();


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(CryptocurrencyActivity.this, NewAlertActivity.class);
//                startActivityForResult(i, 1);
//            }
//        }, 1000);

//        NewAlertFragmentDialog editNameDialog = new NewAlertFragmentDialog();
//        editNameDialog.show(getSupportFragmentManager(), "fragment_edit_name");
//        RequestBuilder.getInstance(this).GenerateKLinesRequest("LTCUSDT","5m","5",this,this,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_COIN_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                EasyLog.e(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                EasyLog.e("RESULT_CANCELED");
                //Write your code if there's no result
            }
        }
    }
}
