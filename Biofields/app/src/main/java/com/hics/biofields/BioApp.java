package com.hics.biofields;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.os.Environment;

import com.hics.biofields.Library.Statics;
import com.hics.biofields.Network.Definitions.Urls;
import com.hics.biofields.Network.HicsWebService;
import com.hics.biofields.Network.RetrofitEnvironments;

import java.io.File;

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
        hicsService= RetrofitEnvironments.createEnvironment(Urls.initStatics(this, Urls.STAGE_PRODUCTION));
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("workflowmanager.realm").build();
        Realm.setDefaultConfiguration(config);
        initDFolders();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();

        //Alerts.showToastMessage(this,R.string.dialog_error_memory_low);
    }

    public static HicsWebService getHicsService() {
        return hicsService;
    }

    private void initDFolders(){
        File storagePath = new File(Environment.getExternalStorageDirectory(), Statics.NAME_FOLDER);
        try {
            if (!storagePath.exists()){
                storagePath.mkdirs();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

