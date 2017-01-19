package eu.mobilebear.carcompany.injection.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import eu.mobilebear.carcompany.injection.annotations.ActivityContext;
import eu.mobilebear.carcompany.injection.annotations.PerActivity;
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
  public ManufacturerPresenter provideManufacturerPresenter(@ActivityContext Context context,
      RestClient restClient) {
    return new ManufacturerPresenter(context, restClient);
  }

  @PerActivity
  @Provides
  public MainTypePresenter providMainTypePresenter(@ActivityContext Context context,
      RestClient restClient) {
    return new MainTypePresenter(context, restClient);
  }

}
