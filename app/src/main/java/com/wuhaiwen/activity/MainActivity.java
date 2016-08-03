package com.wuhaiwen.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.gps.GpsLocation;
import com.util.ImageUtil;
import com.wuhaiwen.fragment.WeatherFragment;
import com.wuhaiwen.weather.R;

import java.io.IOException;
import java.security.Provider;
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

    String city_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("天气预报");
        initFragment();
        initView();
        get_gps();

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
        Intent intent = getIntent();
        String city = (String) intent.getSerializableExtra("city_name");
        Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
        fragmentList.add(WeatherFragment.getInstance("长沙"));
        if (city != null)
            fragmentList.add(WeatherFragment.getInstance(city));
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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    long current_time = System.currentTimeMillis();

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - current_time < 2000) {
            finish();
            System.exit(0);
        } else {
            Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            current_time = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }

    //从回掉接口获得城市名
    @Override
    public void Send_CityName(String city_name, String cond) {
        toolbar.setTitle(city_name);
        city_name = city_name;
        //显示当前城市名
        textView_city_name.setText(city_name);
        //改变背景图片
        frameLayout.setBackgroundResource(ImageUtil.Change_Bg_ImageId(cond));
    }

    double longitude;
    double latitude;
    String locateString;


    public String getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d("location", location.getAltitude() + "gaga");
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        //location的过滤条件，精确度
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置要求LocationProvider是免费的
        criteria.setCostAllowed(false);
        //设置要求LocationProvider提供高度信息,这里只要定位就可以，所以全部设置为false
        criteria.setAltitudeRequired(true);
        //设置能提供方向信息
        criteria.setBearingRequired(false);
        //设置
        locationManager.requestLocationUpdates(
                locationManager.getBestProvider(criteria, true),
                1000,
                10,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d("location", String.valueOf(latitude) + "haha");
                            List<Address> addList = null;
                            Geocoder ge = new Geocoder(MainActivity.this);
                            try {
                                addList = ge.getFromLocation(latitude, longitude, 1);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            if (addList != null && addList.size() > 0) {
                                for (int i = 0; i < addList.size(); i++) {
                                    Address ad = addList.get(i);
                                    locateString += ad.getLocality() + ad.getSubLocality() + ad.getAddressLine(0);
                                }
                            }
                            Log.d("location", locateString + location.getAltitude());
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                }
        );
        return locateString;
    }

    LocationManager lom;
    Location loc;
    Double lat;
    Double lng;

    public void get_gps() {
        Log.d("cityLoaction", "wuhaiwen");
        lom = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("cityLoaction", "nimabia");
            return;
        }
        loc = lom.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (loc != null) {
            lat = loc.getLatitude();
            lng = loc.getLongitude();
            Log.d("cityLoaction", String.valueOf(lat + ";" + lng));
            Log.d("cityLoaction", "qunimabi");
        } else {
            Log.d("cityLoaction", "nimabi");
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = lom.getBestProvider(criteria, true);

        lom.requestLocationUpdates(provider, 1000 * 3600, 10, los);
    }

    LocationListener los = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.d("cityLoaction", "wuhaiwen1");
            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                List<Address> addList = null;
                Geocoder ge = new Geocoder(MainActivity.this);
                try {
                    addList = ge.getFromLocation(lat, lng, 1);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String latLongString = null;
                if (addList != null && addList.size() > 0) {
                    for (int i = 0; i < addList.size(); i++) {
                        Address ad = addList.get(i);
                        latLongString += ad.getCountryName() + ";" + ad.getLocality() + ";" + ad.getLocality() + ";" + ad.getFeatureName()
                                + ";" + ad.getAddressLine(0) + ";" + ad.getSubAdminArea() + ";" + ad.getSubLocality();
                    }
                }
                Log.d("cityLoaction", latLongString + location.getAltitude());
            } else {
            }
        }

        ;

        public void onProviderDisabled(String provider) {

        }

        ;

        public void onProviderEnabled(String provider) {
        }

        ;

        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
        }

        ;
    };


}


