package eu.mobilebear.carcompany;

import android.app.Application;
import eu.mobilebear.carcompany.injection.Injector;
import timber.log.Timber;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
public class CarCompanyApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    initLogging();
    initDagger();
  }

  private void initDagger() {
    Injector.initializeAppComponent(this);
    Injector.getApplicationComponent().inject(this);
  }

  private void initLogging() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
//    Fabric.with(this, new Crashlytics(), new Answers());
  }
}
