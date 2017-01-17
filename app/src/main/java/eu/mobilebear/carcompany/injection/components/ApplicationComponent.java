package eu.mobilebear.carcompany.injection.components;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import eu.mobilebear.carcompany.CarCompanyApplication;
import eu.mobilebear.carcompany.injection.annotations.ApplicationContext;
import eu.mobilebear.carcompany.injection.annotations.CarPreferences;
import eu.mobilebear.carcompany.injection.annotations.GetCars;
import eu.mobilebear.carcompany.injection.modules.ApplicationModule;
import eu.mobilebear.carcompany.injection.modules.NetworkModule;
import eu.mobilebear.carcompany.injection.modules.RealmModule;
import eu.mobilebear.carcompany.rest.RestClient;
import io.realm.Realm;

/**
 * @author bartoszbanczerowski@gmail.com
 * Created on 16.01.2017.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, RealmModule.class})
public interface ApplicationComponent {

	void inject(CarCompanyApplication myCvApplication);

	@ApplicationContext
	Context getApplicationContext();

	@GetCars
	Realm getCarsRealm();

	@CarPreferences
	SharedPreferences getCarSharedPreferences();

	RestClient getRestClient();
}