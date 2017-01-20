package eu.mobilebear.carcompany.rest;

import eu.mobilebear.carcompany.rest.services.CarService;
import retrofit2.Retrofit;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
public class RestClient {

  public static final int PAGE_SIZE = 10;
  private static final String token = "coding-puzzle-client-449cc9d";

  private CarService carService;


  public RestClient(Retrofit retrofit) {
    this.carService = retrofit.create(CarService.class);
  }

  public CarService getCarService() {
    return carService;
  }

  public String getToken() {
    return token;
  }

}
