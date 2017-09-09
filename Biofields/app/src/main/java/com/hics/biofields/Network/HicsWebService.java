package com.hics.biofields.Network;

/**
 * Created by david.barrera on 8/30/17.
 */

import com.hics.biofields.Network.Requests.LoginCompanyRequest;
import com.hics.biofields.Network.Requests.LoginRequest;
import com.hics.biofields.Network.Requests.RecoveryPasswordRequest;
import com.hics.biofields.Network.Responses.Catalogs.BudgetlistResponse;
import com.hics.biofields.Network.Responses.Catalogs.CompanyCatResponse;
import com.hics.biofields.Network.Responses.Catalogs.CostcenterResponse;
import com.hics.biofields.Network.Responses.Catalogs.ExpenseResponse;
import com.hics.biofields.Network.Responses.Catalogs.ItemResponse;
import com.hics.biofields.Network.Responses.Catalogs.SiteResponse;
import com.hics.biofields.Network.Responses.Catalogs.UoMResponse;
import com.hics.biofields.Network.Responses.Catalogs.VendorResponse;
import com.hics.biofields.Network.Responses.CompanyResponse;
import com.hics.biofields.Network.Responses.LoginResponse;
import com.hics.biofields.Network.Responses.RecoveryPasswordResponse;
import com.hics.biofields.Network.Responses.RequisitionItemResponse;
import com.hics.biofields.Network.Responses.ResponseGeneric;

import java.util.ArrayList;

import io.realm.RealmList;
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
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HicsWebService {

    @POST("POST/login/v2/login")
    Call<LoginResponse> Login(@Body LoginRequest loginRequest);

    @POST("POST/login/v2/login/company")
    Call<LoginResponse> LoginCompany(@Header("Authorization")String authorization, @Body LoginCompanyRequest loginCompanyRequest);

    @GET("GET/requisitions/open/{req_id}")
    Call<ArrayList<RequisitionItemResponse>> requisitionsOpen(@Header("Authorization") String authorization,
                                                              @Path("req_id") int id);

    @GET("GET/requisitions/auth/{req_id}")
    Call<ArrayList<RequisitionItemResponse>> requisitionsAuth(@Header("Authorization") String authorization,
                                                              @Path("req_id") int id);
    @PUT("PUT/recoverypasswd")
    Call<RecoveryPasswordResponse> recoveryPassword(@Body RecoveryPasswordRequest recoveryPasswordRequest);

    @POST("POST/logout")
    Call<ResponseGeneric> logout(@Header("Authorization") String authorization);

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