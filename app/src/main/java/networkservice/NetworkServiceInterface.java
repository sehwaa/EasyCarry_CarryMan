package networkservice;

import java.util.List;

import model.Request.RequestSignIn;
import model.Request.RequestSignUp;
import model.Response.ResponseDeilveryList;
import model.Response.ResponseMyDeliveryList;
import model.Response.ResponseSignIn;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by SEHWA on 2017-10-21.
 */

public interface NetworkServiceInterface {

    @POST("/carrier/sign/up")
    Call<ResponseBody> signUpCall(@Body RequestSignUp requestSignUp);

    @POST("/carrier/sign/in")
    Call<ResponseSignIn> signInCall(@Body RequestSignIn requestSignIn);

    @GET("/carrier/delivery")
    Call<List<ResponseDeilveryList>> deliveryCall(@Header("Cookie") String cookie,
                                                  @Query("start") String start,
                                                  @Query("last") int last,
                                                  @Query("size") int size);

    @GET("/carrier/delivery")
    Call<List<ResponseDeilveryList>> alldeliveryCall(@Header("Cookie") String cookie,
                                                     @Query("last") int last,
                                                     @Query("size") int size);

    @POST("/carrier/delivery/{lockerId}/start")
    Call<ResponseBody> addMyListCall(@Header("Cookie") String cookie,
                                     @Path("lockerId") String lockerid);

    @GET("/carrier/mydelivery")
    Call<List<ResponseMyDeliveryList>> mydeliverylistCall(@Header("Cookie") String cookie,
                                                    @Query("last") int last,
                                                    @Query("size") int size);

    @POST("/carrier/mydelivery/{lockerId}/cancel")
    Call<ResponseBody> mydeliverylistcancelCall(@Header("Cookie") String cookie,
                                                @Path("lockerId") String lockerId);

    @POST("/carrier/mydelivery/{lockerId}/complete")
    Call<ResponseBody> mydeliverycompleteCall(@Header("Cookie") String cookie,
                                              @Path("lockerId") String lockerId);

}
