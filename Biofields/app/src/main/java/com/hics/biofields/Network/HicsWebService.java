package com.hics.biofields.Network;

/**
 * Created by david.barrera on 8/30/17.
 */

import com.hics.biofields.Network.Requests.LoginRequest;
import com.hics.biofields.Network.Responses.LoginResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface HicsWebService {

    @POST("POST/login")
    Call<LoginResponse> Login(@Body LoginRequest loginRequest);

}
