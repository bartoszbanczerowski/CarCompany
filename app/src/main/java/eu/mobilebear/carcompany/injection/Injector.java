package eu.mobilebear.carcompany.injection;

import eu.mobilebear.carcompany.CarCompanyApplication;
import eu.mobilebear.carcompany.injection.components.ApplicationComponent;
import eu.mobilebear.carcompany.injection.components.DaggerApplicationComponent;
import eu.mobilebear.carcompany.injection.modules.ApplicationModule;
import eu.mobilebear.carcompany.injection.modules.NetworkModule;
import eu.mobilebear.carcompany.injection.modules.RealmModule;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017. Class responsible for injecting
 *         dependencies into activities, fragments and servcies.
 */
public class Injector {

  private static ApplicationComponent applicationComponent;

  public static void initializeAppComponent(CarCompanyApplication carCompanyApplication) {
    applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(carCompanyApplication))
        .networkModule(new NetworkModule())
        .realmModule(new RealmModule())
        .build();
  }

  public static ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }

}
