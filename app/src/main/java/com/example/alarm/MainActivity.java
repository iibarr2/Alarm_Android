package com.example.alarm;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionsPagerAdapter sectionsPagerAdapter;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //sectionsPagerAdapter.getItem(0);
        //tabLayout.getTabAt(0).select();



        tabLayout.getTabAt(0).setIcon(R.drawable.ic_alarm);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_timer);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_location);


        viewPager.setCurrentItem(0,true);


    }



    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AlarmFragment(), "AlarmFragment");
        adapter.addFragment(new TimerFragment(), "TimerFragment");
        adapter.addFragment(new LocationFragment(), "LocationFragment");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }
}