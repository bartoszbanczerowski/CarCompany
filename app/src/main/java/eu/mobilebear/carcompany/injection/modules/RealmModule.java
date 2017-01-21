package eu.mobilebear.carcompany.injection.modules;


import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import eu.mobilebear.carcompany.injection.annotations.GetCars;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import javax.inject.Singleton;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
@Module
public class RealmModule {

  private static final String CARS_REALM = "cars.realm";

  public RealmModule() {
  }

  @Provides
  @Singleton
  @GetCars
  RealmConfiguration provideCarsRealmConfiguration() {
    return new RealmConfiguration.Builder()
        .name(CARS_REALM)
        .schemaVersion(1)
        .deleteRealmIfMigrationNeeded()
        .build();
  }

  @Provides
  @GetCars
  @Singleton
  Realm provideCarsRealm(@NonNull @GetCars RealmConfiguration realmConfiguration) {
    return Realm.getInstance(realmConfiguration);
  }

}
