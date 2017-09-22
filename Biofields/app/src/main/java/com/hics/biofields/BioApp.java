package com.hics.biofields;

import android.app.Application;
import android.content.ComponentCallbacks2;

import com.hics.biofields.Network.Definitions.Urls;
import com.hics.biofields.Network.HicsWebService;
import com.hics.biofields.Network.RetrofitEnvironments;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by david.barrera on 8/30/17.
 */

public class BioApp extends Application implements ComponentCallbacks2 {

    public static HicsWebService hicsService;

    @Override
    public void onCreate() {
        super.onCreate();
        hicsService= RetrofitEnvironments.createEnvironment(Urls.initStatics(this, Urls.STAGE_QA));
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("workflowmanager.realm").build();
        Realm.setDefaultConfiguration(config);

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();

        //Alerts.showToastMessage(this,R.string.dialog_error_memory_low);
    }

    public static HicsWebService getHicsService() {
        return hicsService;
    }

}

