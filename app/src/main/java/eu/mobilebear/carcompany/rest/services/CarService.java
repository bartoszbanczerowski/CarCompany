package eu.mobilebear.carcompany.rest.services;

import java.util.List;

import eu.mobilebear.carcompany.mvp.model.BuiltDate;
import eu.mobilebear.carcompany.mvp.model.MainType;
import eu.mobilebear.carcompany.mvp.model.Manufacturer;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author bartoszbanczerowski@gmail.com
 * Created on 16.01.2017.
 */
public interface CarService {

	@GET("v1/car-types/manufacturer")
	Observable<Manufacturer> getManufacturers(@Query("wa_key") String token,
											  @Query("page") int page,
											  @Query("pageSize") int pageSize);

	@GET("v1/car-types/main-types")
	Observable<List<MainType>> getMainTypes(@Query("wa_key") String token,
											@Query("page") int page,
											@Query("pageSize") int pageSize,
											@Query("manufacturer") String manufacturer);

	@GET("v1/car-types/built-dates")
	Observable<List<BuiltDate>> getBuiltDates(@Query("wa_key") String token,
											  @Query("manufacturer") String manufacturer,
											  @Query("main-type") String mainType);

}
