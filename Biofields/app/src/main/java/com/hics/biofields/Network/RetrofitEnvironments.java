package com.hics.biofields.Network;

/**
 * Created by david.barrera on 8/30/17.
 */

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;
import io.realm.RealmObject;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitEnvironments {

    public static HicsWebService createEnvironment(String url) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(2, TimeUnit.MINUTES);
        okHttpClient.connectTimeout(2,TimeUnit.MINUTES);
        okHttpClient.writeTimeout(2,TimeUnit.MINUTES);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(HicsWebService.class);



    }

}
