package com.hics.biofields.Views.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hics.biofields.R;
import com.hics.biofields.Views.Adapters.RequisitionAdapter;

import in.galaxyofandroid.awesometablayout.AwesomeTabBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AwesomeTabBar tabBar=(AwesomeTabBar)findViewById(R.id.tabBar);
        ViewPager pager=(ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new RequisitionAdapter(getSupportFragmentManager()));
        tabBar.setupWithViewPager(pager);
    }


}
