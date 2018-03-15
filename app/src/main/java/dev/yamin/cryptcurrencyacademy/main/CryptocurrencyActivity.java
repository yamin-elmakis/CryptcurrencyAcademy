package dev.yamin.cryptcurrencyacademy.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.alerts.AlertsFragment;
import dev.yamin.cryptcurrencyacademy.base.BaseActivity;
import dev.yamin.cryptcurrencyacademy.my.coins.CoinsFragment;
import dev.yamin.cryptcurrencyacademy.network.GsonJsonParser;
import dev.yamin.cryptcurrencyacademy.network.POJOS.KLines;
import dev.yamin.cryptcurrencyacademy.network.RequestBuilder;
import dev.yamin.cryptcurrencyacademy.utils.AppUtils;
import lib.yamin.easylog.EasyLog;

public class CryptocurrencyActivity extends BaseActivity implements CoinsFragment.OnFragmentInteractionListener,AlertsFragment.OnFragmentInteractionListener, Response.ErrorListener, Response.Listener, GsonJsonParser {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // test done
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        viewPager =  findViewById(R.id.main_viewpager);
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

        RequestBuilder.getInstance(this).GenerateKLinesRequest("LTCUSDT","5m","5",this,this,this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        EasyLog.d(error);
    }

    @Override
    public void onResponse(Object response) {
           EasyLog.d(response);
    }


    @Override
    public Object parseJsonToObj(String data) {
        return AppUtils.getObjectFromStr(data, KLines.class);
    }

    @Override
    public String parseObjToJson(Object obj) {
        return null;
    }

}
