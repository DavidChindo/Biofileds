package com.hics.biofields.Views.Activity;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hics.biofields.R;
import com.hics.biofields.Views.Adapters.RequisitionAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.awesometablayout.AwesomeTabBar;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("Requisiciones");
        AwesomeTabBar tabBar=(AwesomeTabBar)findViewById(R.id.tabBar);
        ViewPager pager=(ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new RequisitionAdapter(getSupportFragmentManager()));
        tabBar.setupWithViewPager(pager);
        pager.setCurrentItem(1);
        pager.setCurrentItem(0);
    }

}
