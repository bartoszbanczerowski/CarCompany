package eu.mobilebear.carcompany;

import android.app.Application;

import eu.mobilebear.carcompany.injection.Injector;

/**
 * @author bartoszbanczerowski@gmail.com
 * Created on 16.01.2017.
 */
public class CarCompanyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		initDagger();
	}

	private void initDagger(){
		Injector.initializeAppComponent(this);
		Injector.getApplicationComponent().inject(this);
	}
}
