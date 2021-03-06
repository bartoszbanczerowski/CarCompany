package eu.mobilebear.carcompany.injection.modules;

import dagger.Module;
import dagger.Provides;
import eu.mobilebear.carcompany.injection.annotations.PerActivity;
import eu.mobilebear.carcompany.mvp.presenters.BuiltDatePresenter;
import eu.mobilebear.carcompany.mvp.presenters.CarPresenter;
import eu.mobilebear.carcompany.mvp.presenters.MainTypePresenter;
import eu.mobilebear.carcompany.mvp.presenters.ManufacturerPresenter;
import eu.mobilebear.carcompany.rest.RestClient;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */
@Module
public class CarModule {

  @PerActivity
  @Provides
  ManufacturerPresenter provideManufacturerPresenter(RestClient restClient) {
    return new ManufacturerPresenter(restClient);
  }

  @PerActivity
  @Provides
  MainTypePresenter providMainTypePresenter(RestClient restClient) {
    return new MainTypePresenter(restClient);
  }

  @PerActivity
  @Provides
  BuiltDatePresenter provideBuiltDatePresenter(RestClient restClient) {
    return new BuiltDatePresenter(restClient);
  }

  @PerActivity
  @Provides
  CarPresenter provideCarPresenter(RestClient restClient) {
    return new CarPresenter(restClient);
  }

}
