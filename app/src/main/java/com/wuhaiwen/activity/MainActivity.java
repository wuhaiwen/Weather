package com.wuhaiwen.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.util.ImageUtil;
import com.wuhaiwen.fragment.WeatherFragment;
import com.wuhaiwen.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements WeatherFragment.WeatherCallback {

    ViewPager viewPager;
    FragmentPagerAdapter fAdapter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_city_name)
    TextView textView_city_name;

    @Bind(R.id.FrameLayout_main)
    FrameLayout frameLayout;

//    @Bind(R.id.videoView)
//    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("天气预报");
        initFragment();
        initView();

//        Intent intent = getIntent();
//        String city = (String) intent.getSerializableExtra("city_name");
//        Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        //设置左边设置图标及其监听事件
        toolbar.setNavigationIcon(R.drawable.ic_settings_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingAcitivity.class);
                startActivity(intent);
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.more_action) {
            ChooseCity();
//            Toast.makeText(MainActivity.this, "gaga", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void ChooseCity() {
        //显示意图启动
        Intent intent = new Intent(MainActivity.this, City_Choose_Activity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //从回掉接口获得城市名
    @Override
    public void Send_CityName(String city_name, String cond) {
        toolbar.setTitle(city_name);
        //显示当前城市名
        textView_city_name.setText(city_name);
        //改变背景图片
        frameLayout.setBackgroundResource(ImageUtil.Change_Bg_ImageId(cond));
    }
}


