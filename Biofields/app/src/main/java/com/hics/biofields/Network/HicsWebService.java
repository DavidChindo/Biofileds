package com.hics.biofields.Network;

/**
 * Created by david.barrera on 8/30/17.
 */

import com.hics.biofields.Network.Requests.LoginCompanyRequest;
import com.hics.biofields.Network.Requests.LoginRequest;
import com.hics.biofields.Network.Requests.RecoveryPasswordRequest;
import com.hics.biofields.Network.Requests.RequisitionAuthRequest;
import com.hics.biofields.Network.Requests.RequisitionRequest;
import com.hics.biofields.Network.Responses.Catalogs.BudgetlistResponse;
import com.hics.biofields.Network.Responses.Catalogs.CompanyCatResponse;
import com.hics.biofields.Network.Responses.Catalogs.CostcenterResponse;
import com.hics.biofields.Network.Responses.Catalogs.ExpenseResponse;
import com.hics.biofields.Network.Responses.Catalogs.ItemResponse;
import com.hics.biofields.Network.Responses.Catalogs.SiteResponse;
import com.hics.biofields.Network.Responses.Catalogs.UoMResponse;
import com.hics.biofields.Network.Responses.Catalogs.VendorResponse;
import com.hics.biofields.Network.Responses.LoginResponse;
import com.hics.biofields.Network.Responses.RecoveryPasswordResponse;
import com.hics.biofields.Network.Responses.RequisitionAuthResponse;
import com.hics.biofields.Network.Responses.RequisitionDetailResponse;
import com.hics.biofields.Network.Responses.RequisitionItemResponse;
import com.hics.biofields.Network.Responses.RequisitionResponse;
import com.hics.biofields.Network.Responses.ResponseGeneric;

import java.util.ArrayList;

import io.realm.RealmList;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface HicsWebService {

    @POST("POST/login/v2/login")
    Call<LoginResponse> Login(@Body LoginRequest loginRequest);

    @POST("POST/login/v3/login/company")
    Call<LoginResponse> LoginCompany(@Header("Authorization")String authorization, @Body LoginCompanyRequest loginCompanyRequest);

    @GET("GET/requisitions/open/{req_id}")
    Call<ArrayList<RequisitionItemResponse>> requisitionsOpen(@Header("Authorization") String authorization,
                                                              @Path("req_id") int id);

    @GET("GET/requisitions/auth/{req_id}")
    Call<ArrayList<RequisitionItemResponse>> requisitionsAuth(@Header("Authorization") String authorization,
                                                              @Path("req_id") int id);

    @GET("GET/requisition/info/{req_id}")
    Call<ArrayList<RequisitionDetailResponse>> requisitionDetail(@Header("Authorization") String authorization,
                                                                 @Path("req_id") int id);


    //Call<RecoveryPasswordResponse> recoveryPassword(@Body RecoveryPasswordRequest recoveryPasswordRequest);
    @POST("POST/recoverypasswd")
    Call<RecoveryPasswordResponse> recoveryPassword(@Body RecoveryPasswordRequest recoveryPasswordRequest);
    /*@FormUrlEncoded
    Call<RecoveryPasswordResponse> recoveryPassword(@Field("email") String email);*/

    @POST("POST/logout")
    Call<ResponseGeneric> logout(@Header("Authorization") String authorization);

    @POST("POST/requisition/save")
    Call<RequisitionResponse> createRequisition(@Header("Authorization") String authorization, @Body RequisitionRequest requisitionRequest);

    @Multipart
    @POST("POST/requisition/uploadFile")
    Call<FilesResponse> uploadFile(@Header("Authorization") String authorization,
                                   @Part MultipartBody.Part file,
                                        @Part("req_number") int reqNumber);

    @POST("POST/requisition/auth")
    Call<RequisitionAuthResponse> sentRequisitionAuth(@Header("Authorization") String authorization,
                                                      @Body RequisitionAuthRequest requisitionAuthRequest);
    //region Catalog

    @GET("GET/list/verify/{catalog}/{date}")
    Call<RealmList<VendorResponse>> catalogVendor(@Header("Authorization") String authorization,
                                                  @Path("catalog") String catalog, @Path("date") String date);

    @GET("GET/list/verify/{catalog}/{date}")
    Call<RealmList<CompanyCatResponse>> catalogCompany(@Header("Authorization") String authorization,
                                                       @Path("catalog") String catalog, @Path("date") String date);

    @GET("GET/list/verify/{catalog}/{date}")
    Call<RealmList<CostcenterResponse>> catalogCostcenter(@Header("Authorization") String authorization,
                                                          @Path("catalog") String catalog, @Path("date") String date);

    @GET("GET/list/verify/{catalog}/{date}")
    Call<RealmList<BudgetlistResponse>> catalogBudgetlist(@Header("Authorization") String authorization,
                                                          @Path("catalog") String catalog, @Path("date") String date);

    @GET("GET/list/verify/{catalog}/{date}")
    Call<RealmList<SiteResponse>> catalogSite(@Header("Authorization") String authorization,
                                              @Path("catalog") String catalog, @Path("date") String date);

    @GET("GET/list/verify/{catalog}/{date}")
    Call<RealmList<ExpenseResponse>> catalogExpense(@Header("Authorization") String authorization,
                                                    @Path("catalog") String catalog, @Path("date") String date);

    @GET("GET/list/verify/{catalog}/{date}")
    Call<RealmList<ItemResponse>> catalogItem(@Header("Authorization") String authorization,
                                              @Path("catalog") String catalog, @Path("date") String date);

    @GET("GET/list/verify/{catalog}/{date}")
    Call<RealmList<UoMResponse>> catalogUom(@Header("Authorization") String authorization,
                                            @Path("catalog") String catalog, @Path("date") String date);
    //endregion

}