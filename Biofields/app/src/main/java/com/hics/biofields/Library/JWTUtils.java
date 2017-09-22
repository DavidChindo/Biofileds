package com.hics.biofields.Library;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by david.barrera on 9/14/17.
 */

public class JWTUtils {

    public static String decoded(String JWTEncoded) throws Exception {
        String data = "";
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));

            data = getJson(split[1]);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //Error
        }
        return data;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}