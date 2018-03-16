package dev.yamin.cryptcurrencyacademy.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.alerts.AlertsFragment;
import dev.yamin.cryptcurrencyacademy.alerts.job.JobDispatcherManager;
import dev.yamin.cryptcurrencyacademy.base.BaseActivity;
import dev.yamin.cryptcurrencyacademy.my.coins.CoinsFragment;
import lib.yamin.easylog.EasyLog;

public class CryptocurrencyActivity extends BaseActivity implements
        CoinsFragment.OnFragmentInteractionListener,
        AlertsFragment.OnFragmentInteractionListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.main_viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);
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

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EasyLog.e();
        JobDispatcherManager.getInstance().dispatchJob();
//        NewAlertFragmentDialog editNameDialog = new NewAlertFragmentDialog();
//        editNameDialog.show(getSupportFragmentManager(), "fragment_edit_name");
//        RequestBuilder.getInstance(this).GenerateKLinesRequest("LTCUSDT","5m","5",this,this,this);
    }
}
