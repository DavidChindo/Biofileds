package com.hics.biofields.Models.Managment;

import android.content.Context;

import com.hics.biofields.Models.Objects.User;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by david.barrera on 8/30/17.
 */

public class RealmManager {

    public static void saveUser(final User usr, Realm realm){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(usr);
            }
        });
    }

    public static Realm deleteRealm(){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();

        try {
            Realm.deleteRealm(realmConfiguration);
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex){
                throw ex;
                //No Realm file to remove.
            }
        }
    }

    public static <T extends RealmObject> RealmList<T> list(Realm realm, Class<T> aClass) {
        RealmResults<T> realmResults = realm.where(aClass).findAll();
        RealmList<T> realmList = new RealmList<>();
        for (T result : realmResults) {
            realmList.add(result);
        }
        return realmList;
    }

    public static <T extends RealmObject> void insert(Realm realm, final RealmList<T> items) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(items);
            }
        });
    }

    public static <T extends RealmObject> T findFirst(Realm realm, Class<T> tClass) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
            return realm.where(tClass).findFirst();
        } else {
            if (!realm.isClosed()) {
                return realm.where(tClass).findFirst();
            }
        }
        return null;
    }

    public static <T extends RealmObject> RealmList<T> findByProvider(Class<T> aClass,String fieldName,String value) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<T> realmResults = realm.where(aClass).contains(fieldName,value,Case.INSENSITIVE).findAllAsync();
        RealmList<T> realmList = new RealmList<>();
        for (T result : realmResults){
            realmList.add(result);
        }
        realm.close();
        return realmList;
    }

    public static <T extends RealmObject> RealmList<T> findByDescription(Class<T> aClass,String fieldName,String value,String fieldName2,String value2) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<T> realmResults = realm.where(aClass).equalTo(fieldName,value).findAll();
        RealmResults<T> realmResultsFilter =  realmResults.where().contains(fieldName2,value2,Case.INSENSITIVE).findAll();
        RealmList<T> realmList = new RealmList<>();
        for (T result : realmResultsFilter){
            realmList.add(result);
        }
        realm.close();
        return realmList;
    }

    public static <T extends RealmObject> RealmList<T> findById(Class<T> aClass,String fieldName,String value) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<T> realmResults = realm.where(aClass).equalTo(fieldName,value).findAll();
        RealmList<T> realmList = new RealmList<>();
        for (T result : realmResults){
            realmList.add(result);
        }
        realm.close();
        return realmList;
    }

    public static String token() {
        Realm realm = Realm.getDefaultInstance();
        String jwt = "";
        if (realm == null) {
            realm = Realm.getDefaultInstance();
            jwt = realm.where(User.class).findFirst().getJwt();
        } else {
            if (!realm.isClosed()) {
                jwt = realm.where(User.class).findFirst().getJwt();
            }
        }
        realm.close();
        return jwt;
    }

    public static String user() {
        Realm realm = Realm.getDefaultInstance();
        String user = "";
        if (realm == null) {
            realm = Realm.getDefaultInstance();
            user = realm.where(User.class).findFirst().getEmail();
        } else {
            if (!realm.isClosed()) {
                user = realm.where(User.class).findFirst().getEmail();
            }
        }
        realm.close();
        return user;
    }
}
