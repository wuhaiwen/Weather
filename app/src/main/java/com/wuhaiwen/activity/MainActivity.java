package com.wuhaiwen.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuhaiwen.fragment.WeatherFragment;
import com.wuhaiwen.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    FragmentPagerAdapter fAdapter;
    @Bind(R.id.relative_layout_main)
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        relativeLayout.setBackgroundResource(R.drawable.bg);
        initFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initFragment() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        final List<Fragment> fragmentList = new ArrayList<>();
        WeatherFragment weatherFragment = new WeatherFragment();
        fragmentList.add(weatherFragment);
        fAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setAdapter(fAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
