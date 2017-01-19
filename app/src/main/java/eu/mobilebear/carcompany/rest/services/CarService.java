package eu.mobilebear.carcompany.rest.services;

import eu.mobilebear.carcompany.mvp.model.APIResponse;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
public interface CarService {

  @GET("v1/car-types/manufacturer")
  Observable<Response<APIResponse>> getManufacturers(@Query("wa_key") String token,
      @Query("page") int page,
      @Query("pageSize") int pageSize);

  @GET("v1/car-types/main-types")
  Observable<Response<APIResponse>> getMainTypes(@Query("wa_key") String token,
      @Query("page") int page,
      @Query("pageSize") int pageSize,
      @Query("manufacturer") String manufacturer);

  @GET("v1/car-types/built-dates")
  Observable<Response<APIResponse>> getBuiltDates(@Query("wa_key") String token,
      @Query("manufacturer") String manufacturer,
      @Query("main-type") String mainType);

}
