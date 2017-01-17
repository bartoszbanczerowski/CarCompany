package eu.mobilebear.carcompany.injection.modules;


import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.mobilebear.carcompany.injection.annotations.GetCars;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author bartoszbanczerowski@gmail.com
 * Created on 16.01.2017.
 */
@Module
public class RealmModule {

	private static final String CARS_REALM = "cars.realm";

	public RealmModule() {
	}

	@Provides
	@Singleton
	@GetCars
	public RealmConfiguration provideCarsRealmConfiguration() {
		return new RealmConfiguration.Builder()
				.name(CARS_REALM)
				.schemaVersion(1)
				.deleteRealmIfMigrationNeeded()
				.build();
	}

	@Provides
	@GetCars
	@Singleton
	public Realm provideCarsRealm(@NonNull @GetCars RealmConfiguration realmConfiguration) {
		return Realm.getInstance(realmConfiguration);
	}

}
