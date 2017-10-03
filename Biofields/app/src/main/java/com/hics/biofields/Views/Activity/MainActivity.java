package com.hics.biofields.Views.Activity;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Presenters.Events.CloseFormRequisitionEvent;
import com.hics.biofields.Presenters.Events.ResultsRequisitionsOpenSearch;
import com.hics.biofields.Presenters.Events.ResultsRequisitionsSearch;
import com.hics.biofields.R;
import com.hics.biofields.Views.Adapters.RequisitionAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.awesometablayout.AwesomeTabBar;
import io.realm.Realm;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, android.widget.SearchView.OnQueryTextListener {

    @BindView(R.id.toolbar)Toolbar toolbar;
    android.widget.SearchView searchView;
    ViewPager pager;
    String needAuth = "-1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        ButterKnife.bind(this);
        searchView = (android.widget.SearchView) findViewById(R.id.searchview);
        toolbar.setTitle("Requisiciones");
        AwesomeTabBar tabBar=(AwesomeTabBar)findViewById(R.id.tabBar);
        pager=(ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new RequisitionAdapter(getSupportFragmentManager()));
        tabBar.setupWithViewPager(pager);
        pager.setCurrentItem(1);
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(this);
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("PAGERMAINACTIVITY",""+position);
        needAuth = position == 0 ? "1" : "2";
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("PAGERMAINACTIVITY","onPageSelected "+position);
        needAuth = position == 0 ? "1" : "2";
        if (position == 2){
            startActivity(new Intent(this,FormRequisitionActivity.class));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d("PAGERMAINACTIVITY","onPageScrollStateChanged "+state);
    }

    @Subscribe(sticky = true)
    public void onCloseForm(CloseFormRequisitionEvent event){
        EventBus.getDefault().removeStickyEvent(event);
        pager.setCurrentItem(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (needAuth.equals("1")) {
            EventBus.getDefault().postSticky(new ResultsRequisitionsSearch(RealmManager.findByWord(query, needAuth)));
        }else{
            EventBus.getDefault().postSticky(new ResultsRequisitionsOpenSearch(RealmManager.findByWord(query, needAuth)));
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (needAuth.equals("1")) {
            EventBus.getDefault().postSticky(new ResultsRequisitionsSearch(RealmManager.findByWord(newText, needAuth)));
        }else{
            EventBus.getDefault().postSticky(new ResultsRequisitionsOpenSearch(RealmManager.findByWord(newText, needAuth)));
        }
        return true;
    }
}
