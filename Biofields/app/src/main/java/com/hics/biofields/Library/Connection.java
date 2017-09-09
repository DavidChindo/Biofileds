package com.hics.biofields.Library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by david.barrera on 9/5/17.
 */

public class Connection {

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {

            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            {
                return  isOnlineV2(context);
            }
            return false;


        }catch (Exception e){
            ConnectivityManager CManager =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo NInfo = CManager.getActiveNetworkInfo();
            if (NInfo != null && NInfo.isConnected() && NInfo.isAvailable() && isOnline(context)) {
                return true;
            }
            return false;
        }
    }

    public static boolean isOnline(Context context) {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();//generate ANR
            if (exitValue == 0 )
            {
                Log.wtf("Ping","Ping existoso");
            }
            else
            {
                Log.wtf("Ping", "Ping no existoso");
            }

            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); Log.wtf("Ping", "Ping no existoso"); }
        catch (InterruptedException e) { e.printStackTrace(); Log.wtf("Ping", "Ping no existoso"); }

        return false;
    }


    public static boolean isOnlineV2(Context context) {


        RunnableFuture<Boolean> futureRun = new FutureTask<Boolean>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                try {
                    HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                    urlc.setRequestProperty("User-Agent", "Test");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    return (urlc.getResponseCode() == 200);
                } catch (IOException e) {
                    Log.e(Connection.class.getName(), "Error checking internet connection", e);
                    return false;
                }

            }
        });

        new Thread(futureRun).start();

        try {
            return futureRun.get(2, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(Connection.class.getSimpleName(),"Error "+e.getMessage());
            return false;
        }

    }


}

