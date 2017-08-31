package com.hics.biofields.Models.Managment;

import com.hics.biofields.Models.User;

import io.realm.Realm;
import io.realm.RealmObject;

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
}
