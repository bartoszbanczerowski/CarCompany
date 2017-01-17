package eu.mobilebear.carcompany.injection.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.mobilebear.carcompany.CarCompanyApplication;
import eu.mobilebear.carcompany.injection.annotations.ApplicationContext;
import eu.mobilebear.carcompany.injection.annotations.CarPreferences;

/**
 * @author bartoszbanczerowski@gmail.com
 * Created on 16.01.2017.
 */
@Module
public class ApplicationModule {

	private static final String USER_PREFERENCES = "userPreferences";
	private CarCompanyApplication application;

	public ApplicationModule(CarCompanyApplication application) {
		this.application = application;
	}

	@Provides
	@Singleton
	Application provideApplication() {
		return application;
	}

	@Provides
	@Singleton
	@ApplicationContext
	Context provideApplicationContext() {
		return application;
	}


	@Provides
	@CarPreferences
	public SharedPreferences getCarPreferences(@ApplicationContext Context context) {
		return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
	}
}